package com.evilco.plug.bot.core.event.communication.dj;

import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class ModeratorSkipEvent extends UserSkipEvent {

	/**
	 * Stores the username of the mod which skipped.
	 */
	protected String username = null;

	/**
	 * Constructs a new Moderator Skip Event.
	 * @param source
	 * @param username
	 */
	public ModeratorSkipEvent (PageCommunicationAdapter source, String username) {
		super (source, null);

		this.username = username;
	}

	/**
	 * Returns the username of the moderator which skipped.
	 * @return
	 */
	public String getUsername () {
		return this.username;
	}
}