package com.evilco.plug.bot.core.communication.data;

import com.google.gson.annotations.SerializedName;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum PermissionLevel {
	@SerializedName ("0")
	NONE,
	@SerializedName ("1")
	RESIDENT_DJ,
	@SerializedName ("2")
	BOUNCER,
	@SerializedName ("3")
	MANAGER,
	@SerializedName ("4")
	CO_HOST,
	@SerializedName ("5")
	HOST,

	@SerializedName ("8")
	AMBASSADOR,
	@SerializedName ("10")
	ADMIN
}
