package com.evilco.plug.bot.core.communication.data;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class RoomScore {

	/**
	 * Stores the amount of users who curated the track (grabs).
	 */
	public int curates;

	/**
	 * Stores the negative votes (mehs).
	 */
	public int negative;

	/**
	 * Stores the positive votes (woots).
	 */
	public int positive;

	/**
	 * Stores the overall score.
	 * @todo Verify
	 */
	public int score;
}
