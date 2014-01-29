package com.evilco.plug.bot.core.command.annotation;

import com.evilco.plug.bot.core.communication.data.PermissionLevel;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public @interface Command {

	/**
	 * Defines the maximal amount of arguments for this command.
	 * @return
	 */
	public int argumentsMax () default 0;

	/**
	 * Defines the minimal amount of arguments for this command.
	 * @return
	 */
	public int argumentsMin () default 0;

	/**
	 * Defines a short description of the command.
	 * @return
	 */
	public String description ();

	/**
	 * Defines the minimum permission level for the command.
	 * @return
	 */
	public PermissionLevel permission () default PermissionLevel.NONE;

	/**
	 * Defines the usage of the command.
	 * @return
	 */
	public String usage ();

	/**
	 * Defines all command aliases.
	 * @return
	 */
	public String[] value ();
}
