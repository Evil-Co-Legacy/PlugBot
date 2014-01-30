package com.evilco.plug.bot.core.plugin.configuration;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PluginConfigurationLoadException extends PluginConfigurationException {

	public PluginConfigurationLoadException () {
		super ();
	}

	public PluginConfigurationLoadException (String message) {
		super (message);
	}

	public PluginConfigurationLoadException (String message, Throwable cause) {
		super (message, cause);
	}

	public PluginConfigurationLoadException (Throwable cause) {
		super (cause);
	}
}
