package com.evilco.plug.bot.core.event.communication.score;

import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.communication.data.User;
import com.evilco.plug.bot.core.event.communication.ApiEvent;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class CurateUpdateEvent extends ApiEvent {

	/**
	 * Stores the user which curated.
	 */
	protected User user;

	/**
	 *
	 * @param source
	 */
	public CurateUpdateEvent (PageCommunicationAdapter source, User user) {
		super (source);
		this.user = user;
	}
}
