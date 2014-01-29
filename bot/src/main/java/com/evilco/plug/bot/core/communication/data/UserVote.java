package com.evilco.plug.bot.core.communication.data;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum UserVote {
	POSITIVE,
	NEGATIVE,
	UNKNOWN;

	/**
	 * Returns the correct vote.
	 * @param vote
	 * @return
	 */
	public static UserVote getVote (int vote) {
		if (vote > 0) return POSITIVE;
		if (vote < 0) return NEGATIVE;
		return UNKNOWN;
	}
}
