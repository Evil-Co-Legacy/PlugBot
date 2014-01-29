package com.evilco.plug.bot.core.event.communication.room.message;

import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.communication.data.Message;
import com.evilco.plug.bot.core.event.communication.ApiEvent;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class MessageEvent extends ApiEvent {

	/**
	 * Stores the message.
	 */
	protected Message message = null;

	/**
	 * Constructs a new message event.
	 * @param source
	 * @param message
	 */
	public MessageEvent (PageCommunicationAdapter source, Message message) {
		super (source);

		this.message = message;
	}

	/**
	 * Returns the message.
	 * @return
	 */
	public Message getMessage () {
		return this.message;
	}
}
