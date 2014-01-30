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
	// protected User user = null;

	/**
	 * Stores the username of the user which skipped.
	 */
	protected String username = null;

	/**
	 * Constructs a new User Skip Event.
	 * @param source
	 * @param username
	 */
	// public UserSkipEvent (PageCommunicationAdapter source, User user) {
	public UserSkipEvent (PageCommunicationAdapter source, String username) {
		super (source);

		// this.user = user;
		this.username = username;
	}

	/**
	 * Returns the user who skipped.
	 * @return
	 */
	/* public User getUser () {
		return this.user;
	} */

	/**
	 * Returns the name of the user which skipped.
	 * @return
	 */
	public String getUsername () {
		return this.username;
	}
}
