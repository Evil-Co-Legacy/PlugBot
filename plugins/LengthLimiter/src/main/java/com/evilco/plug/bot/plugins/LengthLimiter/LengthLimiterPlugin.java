package com.evilco.plug.bot.plugins.LengthLimiter;

import com.evilco.plug.bot.core.command.ICommandSender;
import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.communication.data.UserCommandSender;
import com.evilco.plug.bot.core.event.communication.dj.DjAdvanceEvent;
import com.evilco.plug.bot.core.plugin.AbstractPlugin;
import com.evilco.plug.bot.core.plugin.PluginException;
import com.evilco.plug.bot.core.plugin.annotation.Plugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Plugin (name = "LengthLimiter", version = "1.0.0-SNAPSHOT", description = "Limits the maximal length of tracks.", author = "Johannes \"Akkarin\" Donath")
@Component
public class LengthLimiterPlugin extends AbstractPlugin implements ApplicationListener<DjAdvanceEvent> {

	/**
	 * Caches the page communication adapter.
	 */
	@Autowired
	protected PageCommunicationAdapter pageCommunicationAdapter;

	/**
	 * Caches the plugin configuration.
	 */
	protected PluginConfiguration pluginConfiguration;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDisable () throws PluginException {
		super.onDisable ();

		// save configuration
		this.pluginConfiguration.save (this.getConfigurationFile ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onEnable () throws PluginException {
		super.onEnable ();

		// load configuration
		this.pluginConfiguration = PluginConfiguration.newInstance (this.getConfigurationFile ());

		// save configuration
		this.pluginConfiguration.save (this.getConfigurationFile ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onApplicationEvent (DjAdvanceEvent event) {
		// wrap user
		ICommandSender sender = new UserCommandSender (this.pageCommunicationAdapter, event.getUser ());

		// check warning
		if (event.getMedia ().duration > this.pluginConfiguration.warningLength) sender.sendMessage (String.format (this.pluginConfiguration.warningMessage, (this.pluginConfiguration.warningLength / 60)));

		// check skip
		if (event.getMedia ().duration > this.pluginConfiguration.skipLength) this.pageCommunicationAdapter.moderateForceSkip ();
	}
}
