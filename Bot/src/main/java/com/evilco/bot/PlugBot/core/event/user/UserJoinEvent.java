package com.evilco.bot.PlugBot.core.event.user;

import com.evilco.bot.PlugBot.core.data.PlugUser;
import com.evilco.bot.PlugBot.core.event.IEvent;

/**
 * @package com.evilco.bot.PlugBot.core.event.user
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class UserJoinEvent implements IEvent {

	/**
	 * The user who joined.
	 */
	public PlugUser user = null;

	/**
	 * Constructs a new UserJoinEvent.
	 * @param user
	 */
	public UserJoinEvent (PlugUser user) {
		this.user = user;
	}

	/**
	 * Checks whether the user is a fan.
	 * @return
	 */
	public boolean IsFan () {
		return false;
	}

	/**
	 * Checks whether the user is a friend.
	 * @return
	 */
	public boolean IsFriend () {
		return false;
	}
}
