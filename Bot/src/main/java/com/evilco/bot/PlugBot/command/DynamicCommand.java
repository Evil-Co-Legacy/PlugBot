package com.evilco.bot.PlugBot.command;

import com.evilco.bot.PlugBot.command.exception.CommandException;
import com.evilco.bot.PlugBot.command.exception.InternalCommandException;
import com.evilco.bot.PlugBot.core.constant.PermissionLevel;
import com.evilco.bot.PlugBot.core.plugin.annotation.Command;

import java.lang.reflect.Method;

/**
 * @package com.evilco.bot.PlugBot.command
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DynamicCommand implements ICommand {

	/**
	 * Stores the command metadata.
	 */
	protected Command command;

	/**
	 * Stores the listener object.
	 */
	protected ICommandListener listener;

	/**
	 * Stores the command method.
	 */
	protected Method method;

	/**
	 * Constructs a new dynamic command.
	 * @param listener
	 * @param method
	 * @param command
	 */
	public DynamicCommand (ICommandListener listener, Method method, Command command) {
		this.listener = listener;
		this.method = method;
		this.command = command;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void Execute (String alias, CommandContext context) throws CommandException {
		try {
			this.method.invoke (this.listener, alias, context);
		} catch (Exception ex) {
			if (ex instanceof CommandException) throw ((CommandException) ex);
			throw new InternalCommandException (ex);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] GetAliases () {
		return this.command.alias ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String GetDescription () {
		return this.command.description ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PermissionLevel GetPermissionLevel () {
		return this.command.permission ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String GetUsage () {
		return this.command.usage ();
	}
}
