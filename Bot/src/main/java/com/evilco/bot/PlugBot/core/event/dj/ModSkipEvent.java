package com.evilco.bot.PlugBot.core.event.dj;

import com.evilco.bot.PlugBot.core.event.IEvent;

/**
 * @package com.evilco.bot.PlugBot.core.event.room
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ModSkipEvent implements IEvent {

	/**
	 * The name of the moderate who skipped the last track.
	 */
	public String username;

	/**
	 * Constructs a new ModSkipEvent.
	 * @param username
	 */
	public ModSkipEvent (String username) {
		this.username = username;
	}
}
