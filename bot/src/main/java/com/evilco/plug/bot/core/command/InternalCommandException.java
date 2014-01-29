package com.evilco.plug.bot.core.command;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class InternalCommandException extends CommandException {

	public InternalCommandException () {
		super ();
	}

	public InternalCommandException (String message) {
		super (message);
	}

	public InternalCommandException (String message, Throwable cause) {
		super (message, cause);
	}

	public InternalCommandException (Throwable cause) {
		super (cause);
	}
}
