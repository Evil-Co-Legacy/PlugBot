package com.evilco.plug.bot.core.command;

import com.evilco.plug.bot.core.communication.data.PermissionLevel;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface ICommand {

	public void execute (ICommandSender sender, String alias, String[] arguments) throws CommandException;

	/**
	 * Returns a list of command aliases.
	 * @return
	 */
	public String[] getAlias ();

	/**
	 * Returns a minimal amount of arguments the command requires.
	 * @return
	 */
	public int getArgumentsMinimal ();

	/**
	 * Returns a maximal amount of arguments the command requires (-1 = unlimited).
	 * @return
	 */
	public int getArgumentsMaximal ();

	/**
	 * Returns a small description of the command.
	 * @return
	 */
	public String getDescription ();

	/**
	 * Returns the minimal required permission level for this command.
	 * @return
	 */
	public PermissionLevel getPermission ();

	/**
	 * Returns the correct command usage.
	 * @return
	 */
	public String getUsage ();
}
