package com.evilco.plug.bot.core.communication.data;

import com.google.gson.annotations.SerializedName;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum BanDuration {
	@SerializedName ("60")
	HOUR (60),
	@SerializedName ("1440")
	DAY (1440),
	@SerializedName ("-1")
	PERMANENT (-1);

	/**
	 * Defines the API representation.
	 */
	public final int representation;

	/**
	 * Constructs a new Ban Duration.
	 * @param representation
	 */
	private BanDuration (int representation) {
		this.representation = representation;
	}
}
