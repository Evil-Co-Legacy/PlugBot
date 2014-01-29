package com.evilco.plug.bot.core.event.communication.dj;

import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.communication.data.User;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class VoteSkipEvent extends UserSkipEvent {

	/**
	 * Constructs a new Vote Skip Event.
	 * @param source
	 */
	public VoteSkipEvent (PageCommunicationAdapter source) {
		super (source, null);
	}
}
