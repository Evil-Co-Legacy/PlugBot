package com.evilco.bot.PlugBot.command;

import com.evilco.bot.PlugBot.command.exception.CommandException;
import com.evilco.bot.PlugBot.core.constant.PermissionLevel;

/**
 * @package com.evilco.bot.PlugBot.command
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface ICommand {

	/**
	 * Executes the command.
	 * @param alias
	 * @param context
	 */
	public void Execute (String alias, CommandContext context) throws CommandException;

	/**
	 * Returns all associated aliases.
	 * @return
	 */
	public String[] GetAliases ();

	/**
	 * Returns the description of this command.
	 * @return
	 */
	public String GetDescription ();

	/**
	 * Returns the permission level needed to execute this command.
	 * @return
	 */
	public PermissionLevel GetPermissionLevel ();

	/**
	 * Returns a description of the exact command usage.
	 * @return
	 */
	public String GetUsage ();
}
