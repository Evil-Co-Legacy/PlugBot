package com.evilco.bot.PlugBot.core.event.user;

import com.evilco.bot.PlugBot.core.event.IEvent;

/**
 * @package com.evilco.bot.PlugBot.core.event.user
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class UserSkipEvent implements IEvent {

	/**
	 * The user who skipped.
	 */
	public String username = null;

	/**
	 * Constructs a new UserSkipEvent.
	 * @param user
	 */
	public UserSkipEvent (String username) {
		this.username = username;
	}
}
