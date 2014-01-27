package com.evilco.plug.bot.core.plugin;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PluginException extends Exception {

	public PluginException () {
		super ();
	}

	public PluginException (String message) {
		super (message);
	}

	public PluginException (String message, Throwable cause) {
		super (message, cause);
	}

	public PluginException (Throwable cause) {
		super (cause);
	}
}
