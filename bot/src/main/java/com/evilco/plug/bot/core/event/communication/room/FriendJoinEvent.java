package com.evilco.plug.bot.core.event.communication.room;

import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.communication.data.User;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class FriendJoinEvent extends FanJoinEvent {

	/**
	 * Constructs a new Fan Join Event.
	 * @param source
	 * @param user
	 */
	public FriendJoinEvent (PageCommunicationAdapter source, User user) {
		super (source, user);
	}
}
