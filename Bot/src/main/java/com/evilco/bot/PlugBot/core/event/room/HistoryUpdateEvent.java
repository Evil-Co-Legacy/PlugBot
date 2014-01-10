package com.evilco.bot.PlugBot.core.event.room;

import com.evilco.bot.PlugBot.core.data.HistoryItem;
import com.evilco.bot.PlugBot.core.event.IEvent;

import java.util.List;

/**
 * @package com.evilco.bot.PlugBot.core.event.room
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class HistoryUpdateEvent implements IEvent {

	/**
	 * The new history.
	 */
	public List<HistoryItem> items;

	/**
	 * Constructs a new HistoryUpdateEvent.
	 * @param items
	 */
	public HistoryUpdateEvent (List<HistoryItem> items) {
		this.items = items;
	}
}
