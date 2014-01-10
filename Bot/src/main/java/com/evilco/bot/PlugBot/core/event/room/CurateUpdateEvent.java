package com.evilco.bot.PlugBot.core.event.room;

import com.evilco.bot.PlugBot.core.data.PlugUser;
import com.evilco.bot.PlugBot.core.event.IEvent;

/**
 * @package com.evilco.bot.PlugBot.core.event.dj
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class CurateUpdateEvent implements IEvent {

	/**
	 * Stores the user who grabbed a song.
	 */
	public PlugUser user;
}
