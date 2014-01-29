package com.evilco.plug.bot.core.command;

import com.evilco.plug.bot.core.communication.data.PermissionLevel;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface ICommandSender {

	/**
	 * Checks whether the sender has a specific permission level.
	 * @param level
	 * @return
	 */
	public boolean hasPermission (PermissionLevel level);

	/**
	 * Sends a message to the sender.
	 * @param message
	 */
	public void sendMessage (String message);
}
