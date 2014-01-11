package com.evilco.bot.PlugBot.core.plugin.exception;

/**
 * @package com.evilco.bot.PlugBot.core.plugin.exception
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PluginConfigurationException extends Exception {

	/**
	 * Constructs a new PluginConfigurationException.
	 */
	public PluginConfigurationException () {
		super ();
	}

	/**
	 * Constructs a new PluginConfigurationException.
	 * @param message
	 */
	public PluginConfigurationException (String message) {
		super (message);
	}

	/**
	 * Constructs a new PluginConfigurationException.
	 * @param message
	 * @param cause
	 */
	public PluginConfigurationException (String message, Throwable cause) {
		super (message, cause);
	}

	/**
	 * Constructs a new PluginConfigurationException.
	 * @param cause
	 */
	public PluginConfigurationException (Throwable cause) {
		super (cause);
	}
}
