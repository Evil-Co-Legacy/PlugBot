package com.evilco.plug.bot.core.event.communication.dj;

import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.communication.data.Media;
import com.evilco.plug.bot.core.communication.data.User;
import com.evilco.plug.bot.core.event.communication.ApiEvent;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DjAdvanceEvent extends ApiEvent {

	/**
	 * Stores the
	 */
	protected Media media = null;

	/**
	 * Stores the new DJ.
	 */
	protected User user = null;

	/**
	 * Constructs a new DJ Advance Event.
	 * @param source
	 */
	public DjAdvanceEvent (PageCommunicationAdapter source, User user, Media media) {
		super (source);

		this.user = user;
		this.media = media;
	}

	/**
	 * Returns the current media object.
	 * @return
	 */
	public Media getMedia () {
		return this.media;
	}

	/**
	 * Returns the new DJ.
	 * @return
	 */
	public User getUser () {
		return this.user;
	}
}
