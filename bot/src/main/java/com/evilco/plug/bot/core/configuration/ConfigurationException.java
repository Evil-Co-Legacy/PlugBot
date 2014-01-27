package com.evilco.plug.bot.core.configuration;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ConfigurationException extends Exception {

	public ConfigurationException () {
		super ();
	}

	public ConfigurationException (String message) {
		super (message);
	}

	public ConfigurationException (String message, Throwable cause) {
		super (message, cause);
	}

	public ConfigurationException (Throwable cause) {
		super (cause);
	}
}
