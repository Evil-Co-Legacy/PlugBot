package com.evilco.bot.PlugBot.command.exception;

/**
 * @package com.evilco.bot.PlugBot.command.exception
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public abstract class CommandException extends Exception {

	/**
	 * Constructs a new CommandException.
	 */
	public CommandException () {
		super ();
	}

	/**
	 * Constructs a new CommandException.
	 * @param message
	 */
	public CommandException (String message) {
		super (message);
	}

	/**
	 * Constructs a new CommandException.
	 * @param message
	 * @param cause
	 */
	public CommandException (String message, Throwable cause) {
		super (message, cause);
	}

	/**
	 * Constructs a new CommandException.
	 * @param cause
	 */
	public CommandException (Throwable cause) {
		super (cause);
	}
}
