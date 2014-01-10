package com.evilco.bot.PlugBot.core.event.user;

import com.evilco.bot.PlugBot.core.data.PlugUser;
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
	public PlugUser user = null;

	/**
	 * Constructs a new UserSkipEvent.
	 * @param user
	 */
	public UserSkipEvent (PlugUser user) {
		this.user = user;
	}
}
