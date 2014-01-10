package com.evilco.bot.PlugBot.command;

import java.util.List;

/**
 * @package com.evilco.bot.PlugBot.command
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class CommandContext {

	/**
	 * The command sender.
	 */
	protected ICommandSender sender = null;

	/**
	 * A list of supplied arguments.
	 */
	protected List<String> arguments = null;

	/**
	 * A list of set flags (via /command -flag)
	 */
	protected List<String> flags = null;

	/**
	 * Constructs a new command context.
	 * @param sender
	 * @param arguments
	 * @param flags
	 */
	public CommandContext (ICommandSender sender, List<String> arguments, List<String> flags) {
		this.sender = sender;
		this.arguments = arguments;
		this.flags = flags;
	}
}
