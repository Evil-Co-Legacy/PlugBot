package com.evilco.plug.bot.core.event.communication.room;

import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.communication.data.HistoryItem;
import com.evilco.plug.bot.core.event.communication.ApiEvent;

import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class HistoryUpdateEvent extends ApiEvent {

	/**
	 * Stores the new history.
	 */
	protected List<HistoryItem> history;

	/**
	 * Constructs a new History Update Event.
	 * @param source
	 * @param history
	 */
	public HistoryUpdateEvent (PageCommunicationAdapter source, List<HistoryItem> history) {
		super (source);

		this.history = history;
	}

	/**
	 * Returns the history.
	 * @return
	 */
	public List<HistoryItem> getHistory () {
		return this.history;
	}
}
