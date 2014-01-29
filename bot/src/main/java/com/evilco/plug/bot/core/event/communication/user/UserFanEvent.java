package com.evilco.plug.bot.core.event.communication.user;

import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.communication.data.User;
import com.evilco.plug.bot.core.event.communication.ApiEvent;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class UserFanEvent extends ApiEvent {

	/**
	 * Stores the user who fan-ed the bot.
	 */
	protected User user = null;

	/**
	 * Constructs a new User Fan Event.
	 * @param source
	 * @param user
	 */
	public UserFanEvent (PageCommunicationAdapter source, User user) {
		super (source);

		this.user = user;
	}

	/**
	 * Returns the user who fan-ed the bot.
	 * @return
	 */
	public User getUser () {
		return this.user;
	}
}
