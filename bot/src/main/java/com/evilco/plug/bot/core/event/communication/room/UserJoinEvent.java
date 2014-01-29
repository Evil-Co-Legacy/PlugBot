package com.evilco.plug.bot.core.event.communication.room;

import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.communication.data.User;
import com.evilco.plug.bot.core.event.communication.ApiEvent;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class UserJoinEvent extends ApiEvent {

	/**
	 * Stores the user who joined.
	 */
	protected User user;

	/**
	 *
	 * @param source
	 * @param user
	 */
	public UserJoinEvent (PageCommunicationAdapter source, User user) {
		super (source);

		this.user = user;
	}

	/**
	 * Returns the user who joined.
	 * @return
	 */
	public User getUser () {
		return this.user;
	}
}