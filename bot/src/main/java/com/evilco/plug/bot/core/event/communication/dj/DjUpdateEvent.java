package com.evilco.plug.bot.core.event.communication.dj;

import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.communication.data.User;
import com.evilco.plug.bot.core.event.communication.ApiEvent;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DjUpdateEvent extends ApiEvent {

	/**
	 * Stores the current DJ.
	 */
	protected User user;

	/**
	 * Constructs a new DJ Update Event.
	 * @param source
	 * @param user
	 */
	public DjUpdateEvent (PageCommunicationAdapter source, User user) {
		super (source);

		this.user = user;
	}

	/**
	 * Returns the current DJ.
	 * @return
	 */
	public User getUser () {
		return this.user;
	}
}
