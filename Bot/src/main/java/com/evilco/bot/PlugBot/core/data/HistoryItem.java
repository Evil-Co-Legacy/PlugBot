package com.evilco.bot.PlugBot.core.data;

/**
 * @package com.evilco.bot.PlugBot.core.data
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class HistoryItem {

	/**
	 * The user which played the track.
	 * Note: This item will not be filled completely!
	 */
	public PlugUser user;

	/**
	 * The media.
	 */
	public PlugMedia media;

	/**
	 * The votes a media received.
	 */
	public PlugVotes room;
}
