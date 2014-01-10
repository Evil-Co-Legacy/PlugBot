package com.evilco.bot.PlugBot.command.exception;

/**
 * @package com.evilco.bot.PlugBot.command.exception
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class InternalCommandException extends CommandException {

	/**
	 * Constructs a new InternalCommandException.
	 * @param message
	 */
	public InternalCommandException (String message) {
		super (message);
	}

	/**
	 * Constructs a new InternalCommandException.
	 * @param cause
	 */
	public InternalCommandException (Throwable cause) {
		super (cause);
	}
}
