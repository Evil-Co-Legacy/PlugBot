package com.evilco.bot.PlugBot.core.configuration;

/**
 * @package com.evilco.bot.PlugBot.core
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ConfigurationException extends Exception {

	/**
	 * Constructs a new ConfigurationException.
	 * @param ex
	 */
	public ConfigurationException (Exception ex) {
		super (ex);
	}

	/**
	 * Constructs a new ConfigurationException.
	 * @param message
	 */
	public ConfigurationException (String message) {
		super (message);
	}

	/**
	 * Constructs a new ConfigurationException.
	 * @param message
	 * @param ex
	 */
	public ConfigurationException (String message, Exception ex) {
		super (message, ex);
	}
}
