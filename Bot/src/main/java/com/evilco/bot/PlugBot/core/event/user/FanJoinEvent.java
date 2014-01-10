package com.evilco.bot.PlugBot.core.event.user;

import com.evilco.bot.PlugBot.core.data.PlugUser;
import com.evilco.bot.PlugBot.core.event.IEvent;

/**
 * @package com.evilco.bot.PlugBot.core.event.room
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class FanJoinEvent extends UserJoinEvent implements IEvent {

	/**
	 * Stores the user which just joined.
	 */
	public PlugUser user = null;

	/**
	 * Constructs a new FanJoinEvent.
	 * @param user
	 */
	public FanJoinEvent (PlugUser user) {
		super (user);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean IsFan () {
		return true;
	}
}
