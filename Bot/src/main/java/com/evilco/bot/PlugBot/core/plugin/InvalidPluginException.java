package com.evilco.bot.PlugBot.core.plugin;

/**
 * @package com.evilco.bot.PlugBot.core.plugin
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class InvalidPluginException extends Exception {

	/**
	 * Constructs a new InvalidPluginException.
	 * @param message
	 */
	public InvalidPluginException (String message) {
		super (message);
	}

	/**
	 * Constructs a new InvalidPluginException.
	 * @param ex
	 */
	public InvalidPluginException (Exception ex) {
		super (ex);
	}

	/**
	 * Constructs a new InvalidPluginException.
	 * @param message
	 * @param ex
	 */
	public InvalidPluginException (String message, Exception ex) {
		super (message, ex);
	}
}
