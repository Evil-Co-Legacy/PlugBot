package com.evilco.bot.PlugBot.command.exception;

/**
 * @package com.evilco.bot.PlugBot.command.exception
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class InsufficientCommandPermissionsException extends CommandException {

	/**
	 * Constructs a new InsufficientCommandPermissionsException.
	 */
	public InsufficientCommandPermissionsException () {
		super ();
	}

	/**
	 * Constructs a new InsufficientCommandPermissionsException.
	 * @param message
	 */
	public InsufficientCommandPermissionsException (String message) {
		super (message);
	}

	/**
	 * Constructs a new InsufficientCommandPermissionsException.
	 * @param message
	 * @param cause
	 */
	public InsufficientCommandPermissionsException (String message, Throwable cause) {
		super (message, cause);
	}

	/**
	 * Constructs a new InsufficientCommandPermissionsException.
	 * @param cause
	 */
	public InsufficientCommandPermissionsException (Throwable cause) {
		super (cause);
	}
}
