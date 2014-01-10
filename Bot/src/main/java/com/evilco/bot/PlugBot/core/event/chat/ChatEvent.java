package com.evilco.bot.PlugBot.core.event.chat;

import com.evilco.bot.PlugBot.core.event.IEvent;

/**
 * @package com.evilco.bot.PlugBot.core.event.chat
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ChatEvent implements IEvent {

	/**
	 * The message type.
	 */
	public String type;

	/**
	 * The sender's name.
	 */
	public String from;

	/**
	 * The sender's user ID.
	 */
	public String fromID;

	/**
	 * The chat message.
	 */
	public String message;

	/**
	 * The message language (unused).
	 */
	public String language;
}
