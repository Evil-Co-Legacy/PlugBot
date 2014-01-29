package com.evilco.plug.bot.core.event.communication.dj;

import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.communication.data.User;
import com.evilco.plug.bot.core.event.communication.ApiEvent;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class UserSkipEvent extends ApiEvent {

	/**
	 * Stores the user who skipped.
	 */
	protected User user = null;

	/**
	 * Constructs a new User Skip Event.
	 * @param source
	 * @param user
	 */
	public UserSkipEvent (PageCommunicationAdapter source, User user) {
		super (source);

		this.user = user;
	}

	/**
	 * Returns the user who skipped.
	 * @return
	 */
	public User getUser () {
		return this.user;
	}
}
