package com.evilco.bot.PlugBot.plugins.LengthLimiter.event;

import com.evilco.bot.PlugBot.command.sender.PlugCommandSender;
import com.evilco.bot.PlugBot.core.event.AbstractListener;
import com.evilco.bot.PlugBot.core.event.dj.DJAdvanceEvent;
import com.evilco.bot.PlugBot.core.plugin.IPlugin;
import com.evilco.bot.PlugBot.core.plugin.annotation.EventHandler;
import com.evilco.bot.PlugBot.plugins.LengthLimiter.LengthLimiterPlugin;

/**
 * @package com.evilco.bot.PlugBot.plugins.LengthLimiter.event
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
	public LengthLimiterPlugin GetPlugin () {
		return ((LengthLimiterPlugin) this.plugin);
	}

	/**
	 * Handles DJAdvance events.
	 * @param event
	 */
	@EventHandler ()
	public void OnAdvance (DJAdvanceEvent event) {
		this.plugin.GetLog ().debug ("Received DJ advance event!");

		// check event data
		if (event.media == null || event.dj == null) return;

		// check length
		if (event.media.duration <= (this.GetPlugin ().GetConfiguration ().warnLength * 60)) return;

		// wrap user
		PlugCommandSender sender = new PlugCommandSender (event.dj, this.plugin.GetBot ().GetInterface ());

		// check duration
		if (event.media.duration >= (this.GetPlugin ().GetConfiguration ().skipLength * 60)) {
			this.plugin.GetBot ().GetInterface ().ModerateForceSkip ();
			this.GetPlugin ().GetLog ().info ("Skipped track {} by {} due to it's violation of the length limitations: Track is {} seconds long. Limitation is {} seconds", event.media.title, event.media.author, event.media.duration, (this.GetPlugin ().GetConfiguration ().skipLength));
		}

		// notify
		sender.SendMessage ("Please limit your tracks to " + this.GetPlugin ().GetConfiguration ().warnLength + " minutes.");
	}
}
