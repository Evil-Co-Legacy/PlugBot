package com.evilco.bot.PlugBot.core.event.user;

import com.evilco.bot.PlugBot.core.data.PlugUser;
import com.evilco.bot.PlugBot.core.event.IEvent;

/**
 * @package com.evilco.bot.PlugBot.core.event.user
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class UserFanEvent implements IEvent {

	/**
	 * The user who faned the bot.
	 */
	public PlugUser user;

	/**
	 * Constructs a new UserFanEvent.
	 * @param user
	 */
	public UserFanEvent (PlugUser user) {
		this.user = user;
	}
}
