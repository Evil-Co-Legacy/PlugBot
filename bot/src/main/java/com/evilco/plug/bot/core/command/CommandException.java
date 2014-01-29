package com.evilco.plug.bot.core.command;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public abstract class CommandException extends Exception {

	public CommandException () {
		super ();
	}

	public CommandException (String message) {
		super (message);
	}

	public CommandException (String message, Throwable cause) {
		super (message, cause);
	}

	public CommandException (Throwable cause) {
		super (cause);
	}
}
