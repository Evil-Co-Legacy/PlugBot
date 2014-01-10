package com.evilco.bot.PlugBot.core.event.dj;

import com.evilco.bot.PlugBot.core.data.PlugUser;
import com.evilco.bot.PlugBot.core.event.IEvent;

/**
 * @package com.evilco.bot.PlugBot.core.event.dj
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DJUpdateEvent implements IEvent {

	/**
	 * Stores the new DJ.
	 */
	public PlugUser user;

	/**
	 * Constructs a new DJUpdateEvent.
	 * @param user
	 */
	public DJUpdateEvent (PlugUser user) {
		this.user = user;
	}
}
