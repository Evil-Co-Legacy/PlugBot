package com.evilco.bot.PlugBot.core.constant;

/**
 * @package com.evilco.bot.PlugBot.core.constant
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum UserStatus {
	AVAILABLE (0),
	AFK (1),
	WORKING (2),
	GAMING (3);

	/**
	 * Stores the numeric representation of the status.
	 */
	public final int numeric;

	/**
	 * Constructs a new UserStatus.
	 * @param numeric
	 */
	private UserStatus (int numeric) {
		this.numeric = numeric;
	}

	/**
	 * Converts a status from it's numeric presentation.
	 * @param status
	 * @return
	 */
	public static UserStatus FromInt (int numeric) {
		for (UserStatus status : UserStatus.values ()) {
			if (status.numeric == numeric) return status; // TODO: This might be a lot better if we'd use maps ...
		}

		return null;
	}
}
