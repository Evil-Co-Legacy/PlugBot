package com.evilco.bot.PlugBot.core.event.user;

import com.evilco.bot.PlugBot.core.data.PlugUser;
import com.evilco.bot.PlugBot.core.event.IEvent;

/**
 * @package com.evilco.bot.PlugBot.core.event.user
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class UserLeaveEvent implements IEvent {

	/**
	 * The user.
	 */
	public PlugUser user = null;

	/**
	 * Constructs a new UserLeaveEvent.
	 * @param user
	 */
	public UserLeaveEvent (PlugUser user) {
		this.user = user;
	}
}
