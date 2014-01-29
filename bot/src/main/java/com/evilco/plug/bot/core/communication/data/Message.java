package com.evilco.plug.bot.core.communication.data;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class Message {

	/**
	 * Stores the username of the sender.
	 */
	public String from;

	/**
	 * Stores the userID of the sender.
	 */
	public String fromID;

	/**
	 * The language of the user (two character language code).
	 */
	public String language;

	/**
	 * Stores the message.
	 */
	public String message;

	/**
	 * Stores the message type.
	 */
	public MessageType type;
}
