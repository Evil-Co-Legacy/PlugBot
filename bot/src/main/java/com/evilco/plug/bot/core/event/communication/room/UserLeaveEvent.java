package com.evilco.plug.bot.core.event.communication.room;

import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.communication.data.User;
import com.evilco.plug.bot.core.event.communication.ApiEvent;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class UserLeaveEvent extends ApiEvent {

	/**
	 * Stores the user who left.
	 */
	protected User user = null;

	/**
	 * Constructs a new User Leave Event.
	 * @param source
	 * @param user
	 */
	public UserLeaveEvent (PageCommunicationAdapter source, User user) {
		super (source);

		this.user = user;
	}

	/**
	 * Returns the user who left.
	 * @return
	 */
	public User getUser () {
		return this.user;
	}
}
