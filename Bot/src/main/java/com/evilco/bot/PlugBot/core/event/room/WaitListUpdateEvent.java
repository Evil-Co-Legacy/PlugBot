package com.evilco.bot.PlugBot.core.event.room;

import com.evilco.bot.PlugBot.core.data.PlugUser;
import com.evilco.bot.PlugBot.core.event.IEvent;

import java.util.List;

/**
 * @package com.evilco.bot.PlugBot.core.event.room
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class WaitListUpdateEvent implements IEvent {

	/**
	 * The new waitlist.
	 */
	public List<PlugUser> waitList;

	/**
	 * Constructs a new WaitListUpdateEvent.
	 * @param waitList
	 */
	public WaitListUpdateEvent (List<PlugUser> waitList) {
		this.waitList = waitList;
	}
}
