package com.evilco.bot.PlugBot.core.event.room;

import com.evilco.bot.PlugBot.core.data.PlugUser;
import com.evilco.bot.PlugBot.core.event.IEvent;

/**
 * @package com.evilco.bot.PlugBot.core.event.room
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class VoteUpdateEvent implements IEvent {

	/**
	 * The vote (1 = woot, -1 = meh)
	 */
	public int vote;

	/**
	 * The user.
	 */
	public PlugUser user;
}
