package com.evilco.bot.PlugBot.plugins.HistoryCheck.event;

import com.evilco.bot.PlugBot.command.sender.PlugCommandSender;
import com.evilco.bot.PlugBot.core.data.HistoryItem;
import com.evilco.bot.PlugBot.core.event.AbstractListener;
import com.evilco.bot.PlugBot.core.event.dj.DJAdvanceEvent;
import com.evilco.bot.PlugBot.core.plugin.IPlugin;
import com.evilco.bot.PlugBot.core.plugin.annotation.EventHandler;
import com.evilco.bot.PlugBot.plugins.HistoryCheck.HistoryCheckPlugin;

import java.util.List;

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
	public HistoryCheckPlugin GetPlugin () {
		return ((HistoryCheckPlugin) this.plugin);
	}

	/**
	 * Handles DJAdvance events.
	 * @param event
	 */
	@EventHandler()
	public void OnAdvance (DJAdvanceEvent event) {
		this.GetPlugin ().GetLog ().debug ("Received DJAdvanceEvent.");

		// verify that somebody is even playing
		if (event.dj == null || event.media == null) return;

		// read history
		List<HistoryItem> history = this.GetPlugin ().GetBot ().GetInterface ().GetHistory ();

		// remove first item (current track)
		history.remove (0);

		// check other tracks
		int songIndex = 0;

		for (HistoryItem item : history) {
			songIndex++;

			// verify
			if (!item.media.id.equalsIgnoreCase (event.media.id)) continue; // TODO: Check title to make this check more accurate.

			// notify user and auto-skip
			PlugCommandSender sender = new PlugCommandSender (event.dj, this.GetPlugin ().GetBot ().GetInterface ());
			sender.SendMessage ("This track has been played recently (" + songIndex + " out of " + history.size () + ").");

			// skip
			this.GetPlugin ().GetBot ().GetInterface ().ModerateForceSkip ();

			// log
			this.GetPlugin ().GetLog ().info ("Skipped {} by {} (played by {}): Track is in history ({} out of {}).", event.media.title, event.media.author, event.dj.username, songIndex, history.size ());

			// stop execution (one skip is enough)
			return;
		}

		// debug log
		this.GetPlugin ().GetLog ().debug ("Checked {} by {} (played by {}) against history.", event.media.title, event.media.author, event.dj.username);
	}
}
