package com.evilco.bot.PlugBot.plugins.LengthLimiter;

import com.evilco.bot.PlugBot.core.plugin.AbstractPlugin;
import com.evilco.bot.PlugBot.core.plugin.annotation.Plugin;
import com.evilco.bot.PlugBot.core.plugin.exception.PluginConfigurationException;

/**
 * @package com.evilco.bot.PlugBot.plugins.LengthLimiter
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Plugin (name = "LengthLimiter", description = "Limits the maximum duration of media in a channel.", version = "1.0.0-SNAPSHOT", author = "Johannes Donath <johannesd@evil-co.com>")
public class LengthLimiterPlugin extends AbstractPlugin {

	/**
	 * Stores the current plugin configuration.
	 */
	protected LengthLimiterConfiguration configuration = null;

	/**
	 * Returns the plugin configuration.
	 * @return
	 */
	public LengthLimiterConfiguration GetConfiguration () {
		return this.configuration;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void OnEnable () {
		super.OnEnable ();

		// read configuration
		try {
			this.configuration = this.LoadConfiguration (LengthLimiterConfiguration.class);
		} catch (PluginConfigurationException ex) {
			this.GetLog ().warn ("Cannot read plugin configuration file.");

			try {
				this.configuration = new LengthLimiterConfiguration ();
				this.SaveConfiguration (this.configuration);
			} catch (PluginConfigurationException e) {
				this.GetLog ().warn ("Could not read/create the plugin configuration file.", e);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void OnDisable () {
		super.OnDisable ();

		// write configuration file
		try {
			this.SaveConfiguration (this.configuration);
		} catch (PluginConfigurationException e) {
			this.GetLog ().warn ("Could not write the plugin configuration file.", e);
		}
	}
}
