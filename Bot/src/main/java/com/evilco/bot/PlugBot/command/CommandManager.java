package com.evilco.bot.PlugBot.command;


import com.evilco.bot.PlugBot.Bot;
import com.evilco.bot.PlugBot.command.exception.CommandException;
import com.evilco.bot.PlugBot.command.exception.InsufficientCommandPermissionsException;
import com.evilco.bot.PlugBot.command.exception.InvalidCommandArgumentException;
import com.evilco.bot.PlugBot.command.exception.UnknownCommandException;
import com.evilco.bot.PlugBot.core.plugin.IPlugin;
import com.evilco.bot.PlugBot.core.plugin.annotation.Command;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @package com.evilco.bot.PlugBot.command
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class CommandManager {

	/**
	 * The parent Bot instance.
	 */
	protected Bot bot = null;

	/**
	 * Stores a mapping between command aliases and commands.
	 */
	protected Map<String, ICommand> commandMap = null;

	/**
	 * Stores the mapping between commands and plugins.
	 */
	protected Map<String, IPlugin> pluginMap = null;

	/**
	 * Constructs a new CommandManager.
	 * @param bot
	 */
	public CommandManager (Bot bot) {
		this.bot = bot;

		// initialize members
		this.commandMap = new HashMap<String, ICommand> ();
		this.pluginMap = new HashMap<String, IPlugin> ();
	}

	/**
	 * Dispatches a command.
	 * @param sender
	 * @param message
	 * @throws CommandException
	 */
	public void Dispatch (ICommandSender sender, String message) {
		// split up parameters
		List<String> arguments = new ArrayList<String> (Arrays.asList (message.split (" ")));

		// get command alias
		String alias = arguments.remove (0);

		// check whether command has been registered
		if (!this.commandMap.containsKey (alias)) {
			sender.SendMessage ("That command is not known to me. Sorry.");
			return;
		}

		// construct list of flags
		List<String> flags = new ArrayList<String> ();

		if (arguments.size () > 0) {
			// check whether first parameter consists of flags
			if (arguments.get (0).matches ("/\\-(\\w)/")) {
				// get string
				String flagString = arguments.remove (0);

				// add characters
				flags = new ArrayList<String> (Arrays.asList (flagString.split ("")));

				// remove -
				flags.remove (0);
			}
		}

		// enter execution.
		try {
			// create command context
			CommandContext context = new CommandContext (sender, arguments, flags);

			// dispatch
			ICommand command = this.commandMap.get (alias);

			// check permissions
			if (command.GetPermissionLevel ().level > sender.GetPermissionLevel ().level) throw new InsufficientCommandPermissionsException ();

			// execute command
			command.Execute (alias, context);
		} catch (UnknownCommandException ex) {
			sender.SendMessage ("That command is not known to me. Sorry.");
		} catch (InvalidCommandArgumentException ex) {
			sender.SendMessage ("Usage: " + this.commandMap.get (alias).GetUsage ());
		} catch (InsufficientCommandPermissionsException ex) {
			sender.SendMessage ("You shall not pass ... this command to my event processor.");
		} catch (Exception ex) {
			sender.SendMessage ("An error occurred during command execution. This will be fixed soon! ... hopefully ...");
			this.bot.log.error ("Something went wrong during the command execution of " + alias + "!", ex);
		}
	}

	/**
	 * Registers a new command.
	 * @param alias
	 * @param command
	 * @param plugin
	 */
	public void RegisterCommand (String alias, ICommand command, IPlugin plugin) {
		this.commandMap.put (alias, command);
		if (plugin != null) this.pluginMap.put (alias, plugin);
	}

	/**
	 * Registers a new listener.
	 * @param listener
	 * @param plugin
	 */
	public void RegisterListener (ICommandListener listener, IPlugin plugin) {
		// search for annotated methods
		for (Method method : listener.getClass ().getMethods ()) {
			// skip util methods and such
			if (!method.isAnnotationPresent (Command.class)) continue;

			// get parameters
			Class<?>[] parameters = method.getParameterTypes ();

			// check against template
			if (parameters.length < 2 || parameters.length > 2) {
				this.bot.log.warn ("The method {} in the command listener {} does not accept the arguments String, CommandContext.", method.getName (), listener.getClass ().getName ());
				this.bot.log.warn ("Please notify the plugin author about this issue. The command handler will be skipped.");
				continue;
			}

			// check types
			if (!String.class.isAssignableFrom (parameters[0]) || !CommandContext.class.isAssignableFrom (parameters[1])) {
				this.bot.log.warn ("The method {} in the command listener {} does not accept the arguments String, CommandContext.", method.getName (), listener.getClass ().getName ());
				this.bot.log.warn ("Please notify the plugin author about this issue. The command handler will be skipped.");
				continue;
			}

			// everything fine! register ...
			ICommand command = new DynamicCommand (listener, method, method.getAnnotation (Command.class));

			// register all aliases
			for (String alias : method.getAnnotation (Command.class).alias ()) {
				this.RegisterCommand (alias, command, plugin);
			}
		}
	}
}
