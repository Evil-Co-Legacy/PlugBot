package com.evilco.plug.bot.core.communication.data;

import com.google.gson.annotations.SerializedName;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum PermissionLevel {
	@SerializedName ("0")
	NONE (0),
	@SerializedName ("1")
	RESIDENT_DJ (1),
	@SerializedName ("2")
	BOUNCER (2),
	@SerializedName ("3")
	MANAGER (3),
	@SerializedName ("4")
	CO_HOST (4),
	@SerializedName ("5")
	HOST (5),

	@SerializedName ("8")
	AMBASSADOR (8),
	@SerializedName ("10")
	ADMIN (10);

	/**
	 * Stores the integer representation of a permission level.
	 */
	public final int representation;

	/**
	 * Constructs a new Permission Level.
	 * @param representation
	 */
	private PermissionLevel (int representation) {
		this.representation = representation;
	}
}
