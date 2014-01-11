package com.evilco.bot.PlugBot.plugins.UserGreet;

import com.evilco.bot.PlugBot.core.plugin.AbstractPlugin;
import com.evilco.bot.PlugBot.core.plugin.annotation.Plugin;
import com.evilco.bot.PlugBot.core.plugin.exception.PluginConfigurationException;

/**
 * @package com.evilco.bot.PlugBot.plugins.UserGreet
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Plugin(name = "UserGreet", description = "Greets joining users.", version = "1.0.0-SNAPSHOT", author = "Johannes Donath <johannesd@evil-co.com>")
public class UserGreetPlugin extends AbstractPlugin {

	/**
	 * Stores the current plugin configuration.
	 */
	protected UserGreetConfiguration configuration = null;

	/**
	 * Returns the plugin configuration.
	 * @return
	 */
	public UserGreetConfiguration GetConfiguration () {
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
			this.configuration = this.LoadConfiguration (UserGreetConfiguration.class);
		} catch (PluginConfigurationException ex) {
			this.GetLog ().warn ("Cannot read plugin configuration file.", ex);

			try {
				this.configuration = new UserGreetConfiguration ();
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
