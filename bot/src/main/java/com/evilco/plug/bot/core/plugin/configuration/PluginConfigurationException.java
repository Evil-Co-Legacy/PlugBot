package com.evilco.plug.bot.core.plugin.configuration;

import com.evilco.plug.bot.core.plugin.PluginException;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public abstract class PluginConfigurationException extends PluginException {

	public PluginConfigurationException () {
		super ();
	}

	public PluginConfigurationException (String message) {
		super (message);
	}

	public PluginConfigurationException (String message, Throwable cause) {
		super (message, cause);
	}

	public PluginConfigurationException (Throwable cause) {
		super (cause);
	}
}
