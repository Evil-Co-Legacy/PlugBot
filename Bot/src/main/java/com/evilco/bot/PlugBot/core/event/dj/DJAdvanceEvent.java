package com.evilco.bot.PlugBot.core.event.dj;

import com.evilco.bot.PlugBot.core.data.PlugMedia;
import com.evilco.bot.PlugBot.core.data.PlugUser;
import com.evilco.bot.PlugBot.core.event.IEvent;

/**
 * @package com.evilco.bot.PlugBot.core.event.dj
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DJAdvanceEvent implements IEvent {

	/**
	 * Stores the new DJ.
	 */
	public PlugUser dj;

	/**
	 * The currently playing track.
	 */
	public PlugMedia media;
}
