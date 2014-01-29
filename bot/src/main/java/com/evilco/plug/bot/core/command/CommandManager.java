package com.evilco.plug.bot.core.command;

import com.evilco.plug.bot.core.command.annotation.Command;
import com.evilco.plug.bot.core.plugin.IPlugin;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Component
public class CommandManager {

	/**
	 * Stores the internal logging instance.
	 */
	protected static final Logger logger = Logger.getLogger ("CommandManager");

	/**
	 * Stores a mapping between commands and their alias.
	 */
	protected Map<String, ICommand> commandMap = new HashMap<> ();

	/**
	 * Stores a mapping between command aliases and plugins.
	 */
	protected Map<IPlugin, String> pluginMap = new HashMap<> ();

	/**
	 * Dispatches a command.
	 * @param sender
	 * @param commandEx
	 * @return
	 * @throws CommandNotFoundException
	 */
	public boolean dispatch (ICommandSender sender, String[] commandEx) throws CommandNotFoundException {
		// construct array list
		ArrayList<String> arguments = new ArrayList<> (Arrays.asList (commandEx));

		// get alias
		String alias = arguments.remove (0);

		// search for correct alias
		if (!this.commandMap.containsKey (alias)) throw new CommandNotFoundException ();

		// dispatch command
		try {
			this.commandMap.get (alias).execute (sender, alias, ((String[]) arguments.toArray ()));
			return true;
		} catch (CommandUsageException ex) {
			sender.sendMessage ("Usage: " + this.commandMap.get (alias).getUsage ());
		} catch (CommandPermissionException ex) {
			sender.sendMessage ("You need more permissions to execute this command.");
		} catch (CommandException ex) {
			sender.sendMessage ("An internal error occured while executing the command.");
			logger.log (Level.SEVERE, "Could not execute command with alias \"" + alias + "\".", ex);
		} finally {
			return false;
		}
	}

	/**
	 * Registers a new command.
	 * @param command
	 * @param plugin
	 */
	public void registerCommand (ICommand command, IPlugin plugin) {
		for (String alias : command.getAlias ()) {
			// overwrite plugin
			if (plugin == null && this.pluginMap.containsValue (alias)) {
				for (Map.Entry<IPlugin, String> pluginRegistration : this.pluginMap.entrySet ()) {
					if (pluginRegistration.getValue ().equalsIgnoreCase (alias)) this.pluginMap.remove (pluginRegistration.getKey ());
				}
			} else
				this.pluginMap.put (plugin, alias);

			// register alias
			this.commandMap.put (alias, command);
		}
	}

	/**
	 * Registers a new handler class.
	 * @param handler
	 */
	public void registerHandlerClass (Object handler, IPlugin plugin) {
		for (Method method : handler.getClass ().getMethods ()) {
			// skip un-annotated methods
			if (!method.isAnnotationPresent (Command.class)) continue;

			// get information
			Command metadata = method.getAnnotation (Command.class);

			// construct a new dynamic command
			ICommand command = new DynamicCommand (handler, method, metadata.value (), metadata.argumentsMin (), metadata.argumentsMax (), metadata.description (), metadata.permission (), metadata.usage ());

			// register command
			this.registerCommand (command, plugin);
		}
	}
}
