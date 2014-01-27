package com.evilco.plug.bot.core.plugin;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PluginLoaderException extends PluginException {

	public PluginLoaderException () {
		super ();
	}

	public PluginLoaderException (String message) {
		super (message);
	}

	public PluginLoaderException (String message, Throwable cause) {
		super (message, cause);
	}

	public PluginLoaderException (Throwable cause) {
		super (cause);
	}
}
