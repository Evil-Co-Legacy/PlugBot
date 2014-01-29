package com.evilco.plug.bot.core.event.communication.dj;

import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.communication.data.User;
import com.evilco.plug.bot.core.event.communication.ApiEvent;

import java.util.List;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class WaitListUpdateEvent extends ApiEvent {

	/**
	 * Stores the new wait list.
	 */
	protected List<User> waitList = null;

	/**
	 * Constructs a new Wait List Update Event.
	 * @param source
	 * @param waitList
	 */
	public WaitListUpdateEvent (PageCommunicationAdapter source, List<User> waitList) {
		super (source);

		this.waitList = waitList;
	}

	/**
	 * Returns the new wait list.
	 * @return
	 */
	public List<User> getWaitList () {
		return this.waitList;
	}
}
