package com.evilco.bot.PlugBot.command.exception;

/**
 * @package com.evilco.bot.PlugBot.command.exception
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class UnknownCommandException extends CommandException {

	/**
	 * Constructs a new UnknownCommandException.
	 */
	public UnknownCommandException () {
		super ();
	}

	/**
	 * Constructs a new UnknownCommandException.
	 * @param cause
	 */
	public UnknownCommandException (Throwable cause) {
		super (cause);
	}
}
