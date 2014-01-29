package com.evilco.plug.bot.core.command;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface ICommandSender {

	/**
	 * Sends a message to the sender.
	 * @param message
	 */
	public void sendMessage (String message);
}
