package com.evilco.plug.bot.core.communication.data;

import com.google.gson.annotations.SerializedName;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum MessageType {
	@SerializedName ("message")
	MESSAGE,
	@SerializedName ("emote")
	EMOTE,
	@SerializedName ("mention")
	MENTION,
	@SerializedName ("moderation")
	MODERATION,
	@SerializedName ("system")
	SYSTEM;
}
