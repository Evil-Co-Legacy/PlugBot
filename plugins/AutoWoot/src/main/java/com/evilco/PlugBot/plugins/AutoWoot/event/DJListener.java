package com.evilco.PlugBot.plugins.AutoWoot.event;

import com.evilco.PlugBot.plugins.AutoWoot.AutoWootPlugin;
import com.evilco.bot.PlugBot.core.event.AbstractListener;
import com.evilco.bot.PlugBot.core.event.dj.DJAdvanceEvent;
import com.evilco.bot.PlugBot.core.plugin.IPlugin;
import com.evilco.bot.PlugBot.core.plugin.annotation.EventHandler;

/**
 * @package com.evilco.bot.PlugBot.plugins.HistoryCheck.event
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DJListener extends AbstractListener {

	/**
	 * Constructs a new DJ listener.
	 * @param plugin
	 */
	public DJListener (IPlugin plugin) {
		super (plugin);
	}

	/**
	 * Returns the parent plugin instance.
	 * @return
	 */
	public AutoWootPlugin GetPlugin () {
		return ((AutoWootPlugin) this.plugin);
	}

	/**
	 * Handles DJAdvance events.
	 * @param event
	 */
	@EventHandler()
	public void OnAdvance (DJAdvanceEvent event) {
		this.GetPlugin ().GetLog ().debug ("Received DJAdvanceEvent.");

		// check whether the room is empty
		if (event.dj == null || event.media == null) return;

		// vote
		this.GetPlugin ().GetBot ().GetInterface ().VotePositive ();

		// debug log
		this.GetPlugin ().GetLog ().debug ("Wooted on {} by {} (played by {}).", event.media.title, event.media.author, event.dj.username);
	}
}
