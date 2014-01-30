package com.evilco.plug.bot.core.plugin.configuration;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PluginConfigurationSaveException extends PluginConfigurationException {

	public PluginConfigurationSaveException () {
		super ();
	}

	public PluginConfigurationSaveException (String message) {
		super (message);
	}

	public PluginConfigurationSaveException (String message, Throwable cause) {
		super (message, cause);
	}

	public PluginConfigurationSaveException (Throwable cause) {
		super (cause);
	}
}
