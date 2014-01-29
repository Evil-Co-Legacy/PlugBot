package com.evilco.plug.bot.core.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
class DynamicCommand implements ICommand {

	/**
	 * Stores the alias list.
	 */
	protected String[] alias;

	/**
	 * Stores the maximal amount of arguments which has to be supplied to this command.
	 */
	protected int argumentsMaximal;

	/**
	 * Stores the minimal amount of arguments which has to be supplied to this command.
	 */
	protected int argumentsMinimal;

	/**
	 * Stores a short command description.
	 */
	protected String description;

	/**
	 * Stores the command handler.
	 */
	protected final Object handler;

	/**
	 * Stores the command method.
	 */
	protected final Method method;

	/**
	 * Stores the command usage.
	 */
	protected String usage;

	/**
	 * Constructs a new dynamic command.
	 * @param handler
	 * @param method
	 * @param alias
	 * @param argumentsMinimal
	 * @param argumentsMaximal
	 * @param description
	 * @param usage
	 */
	public DynamicCommand (Object handler, Method method, String[] alias, int argumentsMinimal, int argumentsMaximal, String description, String usage) {
		this.handler = handler;
		this.method = method;
		this.alias = alias;
		this.argumentsMinimal = argumentsMinimal;
		this.argumentsMaximal = argumentsMaximal;
		this.description = description;
		this.usage = usage;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void execute (ICommandSender sender, String alias, String[] arguments) throws CommandException {
		// verify arguments
		if (arguments.length < this.argumentsMinimal) throw new InvalidCommandArgumentException ("Not enough arguments supplied.");
		if (this.argumentsMaximal <= 0 && arguments.length > this.argumentsMaximal) throw new InvalidCommandArgumentException ("More arguments supplied than allowed.");

		// execute handler
		try {
			// try to make method accessible
			this.method.setAccessible (true);

			// invoke method
			this.method.invoke (this.handler, sender, alias, arguments);
		} catch (IllegalAccessException ex) {
			throw new InternalCommandException ("Cannot access handler method.", ex);
		} catch (InvocationTargetException ex) {
			throw new InternalCommandException (ex);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getAlias () {
		return this.alias;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getArgumentsMinimal () {
		return this.argumentsMinimal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getArgumentsMaximal () {
		return this.argumentsMaximal;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getDescription () {
		return this.description;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getUsage () {
		return this.usage;
	}
}
