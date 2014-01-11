package com.evilco.bot.PlugBot.plugins.UserGreet.event;

import com.evilco.bot.PlugBot.core.constant.PermissionLevel;
import com.evilco.bot.PlugBot.core.event.AbstractListener;
import com.evilco.bot.PlugBot.core.event.user.UserJoinEvent;
import com.evilco.bot.PlugBot.core.plugin.IPlugin;
import com.evilco.bot.PlugBot.core.plugin.annotation.EventHandler;
import com.evilco.bot.PlugBot.plugins.UserGreet.UserGreetPlugin;

import java.util.Random;

/**
 * @package com.evilco.bot.PlugBot.plugins.UserGreet.event
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class UserListener extends AbstractListener {

	/**
	 * Constructs a new UserListener.
	 * @param plugin
	 */
	public UserListener (IPlugin plugin) {
		super (plugin);
	}

	/**
	 * Returns the parent plugin instance.
	 * @return
	 */
	public UserGreetPlugin GetPlugin () {
		return ((UserGreetPlugin) this.plugin);
	}

	/**
	 * Handles user join events.
	 * @param event
	 */
	@EventHandler
	public void OnUserJoin (UserJoinEvent event) {
		// debug
		this.GetPlugin ().GetLog ().debug ("Received UserJoin event.");

		// check user
		if (event.user == null) return;
		if (event.user.id == this.GetPlugin ().GetBot ().GetInterface ().GetBotID ()) return;
		if (!this.GetPlugin ().GetConfiguration ().greetRegularUsers && event.user.permission < PermissionLevel.RESIDENT_DJ.level) return;

		// choose greeting
		Random random = new Random ();
		String greeting = this.GetPlugin ().GetConfiguration ().greetLines.get (random.nextInt (this.GetPlugin ().GetConfiguration ().greetLines.size ()));

		// send greeting
		this.GetPlugin ().GetBot ().GetInterface ().SendChat (String.format (greeting, "@" + event.user.username));
	}
}
