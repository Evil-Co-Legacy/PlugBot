package com.evilco.plug.bot.core.command;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class CommandPermissionException extends CommandException {

	public CommandPermissionException () {
		super ();
	}

	public CommandPermissionException (String message) {
		super (message);
	}

	public CommandPermissionException (String message, Throwable cause) {
		super (message, cause);
	}

	public CommandPermissionException (Throwable cause) {
		super (cause);
	}
}
