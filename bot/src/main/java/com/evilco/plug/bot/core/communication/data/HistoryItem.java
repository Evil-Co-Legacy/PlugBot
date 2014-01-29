package com.evilco.plug.bot.core.communication.data;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class HistoryItem {

	/**
	 * Stores the played media.
	 */
	public Media media;

	/**
	 * Stores the rating of the community.
	 */
	public RoomScore room;

	/**
	 * Stores the user who played this media.
	 */
	public User user;
}
