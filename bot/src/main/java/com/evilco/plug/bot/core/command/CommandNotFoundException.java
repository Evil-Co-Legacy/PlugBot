package com.evilco.plug.bot.core.command;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class CommandNotFoundException extends CommandException {

	public CommandNotFoundException () {
		super ();
	}

	public CommandNotFoundException (String message) {
		super (message);
	}

	public CommandNotFoundException (String message, Throwable cause) {
		super (message, cause);
	}

	public CommandNotFoundException (Throwable cause) {
		super (cause);
	}
}
