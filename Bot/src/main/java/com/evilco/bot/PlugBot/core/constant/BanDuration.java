package com.evilco.bot.PlugBot.core.constant;

/**
 * @package com.evilco.bot.PlugBot.core.constant
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum BanDuration {
	HOUR (60),
	DAY (1440),
	PERMANENT (-1);

	/**
	 * Stores the duration in minutes.
	 */
	public final int duration;

	/**
	 * Constructs a new BanDuration.
	 * @param duration
	 */
	private BanDuration (int duration) {
		this.duration = duration;
	}

	/**
	 * Converts a ban duration from it's numeric presentation.
	 * @param duration
	 * @return
	 */
	public static BanDuration FromInt (int duration) {
		for (BanDuration durationObject : BanDuration.values ()) {
			if (durationObject.duration == duration) return durationObject; // TODO: This might be a lot better if we'd use maps ...
		}

		return null;
	}
}
