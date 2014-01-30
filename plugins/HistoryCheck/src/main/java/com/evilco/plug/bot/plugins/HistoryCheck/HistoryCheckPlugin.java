package com.evilco.plug.bot.plugins.HistoryCheck;

import com.evilco.plug.bot.core.command.ICommandSender;
import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.communication.data.HistoryItem;
import com.evilco.plug.bot.core.communication.data.UserCommandSender;
import com.evilco.plug.bot.core.event.communication.dj.DjAdvanceEvent;
import com.evilco.plug.bot.core.plugin.AbstractPlugin;
import com.evilco.plug.bot.core.plugin.annotation.Plugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Plugin (name = "HistoryCheck", version = "1.0.0-SNAPSHOT", description = "Automatically checks track against the room history.", author = "Johannes \"Akkarin\" Donath")
@Component
public class HistoryCheckPlugin extends AbstractPlugin implements ApplicationListener<DjAdvanceEvent> {

	/**
	 * Caches the page communication adapter instance.
	 */
	@Autowired
	protected PageCommunicationAdapter pageCommunicationAdapter;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onApplicationEvent (DjAdvanceEvent event) {
		// get room history
		List<HistoryItem> history = this.pageCommunicationAdapter.getHistory ();

		// remove first item (current track).
		history.remove (0);

		// search history for current track
		int index = 1;

		for (HistoryItem item : history) {
			if (item.media.id.equalsIgnoreCase (event.getMedia ().id)) {
				this.pageCommunicationAdapter.moderateForceSkip ();

				// wrap user
				ICommandSender sender = new UserCommandSender (this.pageCommunicationAdapter, event.getUser ());

				// send warning
				sender.sendMessage (String.format ("Track was in history (%d out of %s).", index, history.size ()));
			}

			// count up
			index++;
		}
	}
}
