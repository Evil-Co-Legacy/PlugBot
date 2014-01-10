package com.evilco.bot.PlugBot.command;

import com.evilco.bot.PlugBot.core.constant.PermissionLevel;

/**
 * @package com.evilco.bot.PlugBot.command
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface ICommandSender {

	/**
	 * Returns the maximum permission level assigned to this sender.
	 * @return
	 */
	public PermissionLevel GetPermissionLevel ();

	/**
	 * Sends a message to the command sender.
	 * @param message
	 */
	public void SendMessage (String message);
}
