package com.evilco.bot.PlugBot.core.data;

/**
 * @package com.evilco.bot.PlugBot.core.data
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class QueuedMedia {

	/**
	 * Stores the queued media.
	 */
	public PlugMedia media;

	/**
	 * Indicates whether the item is already in the room history.
	 */
	public boolean inHistory;
}
