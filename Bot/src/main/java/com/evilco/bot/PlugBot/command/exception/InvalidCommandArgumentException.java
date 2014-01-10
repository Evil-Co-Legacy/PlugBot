package com.evilco.bot.PlugBot.command.exception;

/**
 * @package com.evilco.bot.PlugBot.command.exception
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class InvalidCommandArgumentException extends CommandException {

	/**
	 * Constructs a new InvalidCommandArgumentException.
	 */
	public InvalidCommandArgumentException () {
		super ();
	}

	/**
	 * Constructs a new InvalidCommandArgumentException.
	 * @param cause
	 */
	public InvalidCommandArgumentException (Throwable cause) {
		super (cause);
	}
}
