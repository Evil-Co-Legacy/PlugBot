package com.evilco.plug.bot.core.command;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class CommandUsageException extends CommandException {

	public CommandUsageException () {
		super ();
	}

	public CommandUsageException (String message) {
		super (message);
	}

	public CommandUsageException (String message, Throwable cause) {
		super (message, cause);
	}

	public CommandUsageException (Throwable cause) {
		super (cause);
	}
}
