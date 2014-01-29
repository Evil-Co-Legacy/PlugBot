package com.evilco.plug.bot.core.event.communication.room;

import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.communication.data.User;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class FanJoinEvent extends UserJoinEvent {

	/**
	 * Constructs a new Fan Join Event.
	 * @param source
	 * @param user
	 */
	public FanJoinEvent (PageCommunicationAdapter source, User user) {
		super (source, user);
	}
}
