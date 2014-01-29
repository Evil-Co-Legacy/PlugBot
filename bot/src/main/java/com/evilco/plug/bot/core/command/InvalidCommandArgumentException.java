package com.evilco.plug.bot.core.command;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class InvalidCommandArgumentException extends CommandUsageException {

	public InvalidCommandArgumentException () {
		super ();
	}

	public InvalidCommandArgumentException (String message) {
		super (message);
	}

	public InvalidCommandArgumentException (String message, Throwable cause) {
		super (message, cause);
	}

	public InvalidCommandArgumentException (Throwable cause) {
		super (cause);
	}
}
