package com.evilco.plug.bot.core.communication.data;

import com.google.gson.annotations.SerializedName;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum UserStatus {
	@SerializedName ("0")
	AVAILABLE (0),
	@SerializedName ("1")
	AFK (1),
	@SerializedName ("2")
	WORKING (2),
	@SerializedName ("3")
	GAMING (3);

	/**
	 * Defines the API representation.
	 */
	public final int representation;

	/**
	 * Constructs a new User Status.
	 * @param representation
	 */
	private UserStatus (int representation) {
		this.representation = representation;
	}
}
