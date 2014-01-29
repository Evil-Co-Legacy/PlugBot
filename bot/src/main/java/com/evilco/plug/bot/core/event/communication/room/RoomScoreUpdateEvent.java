package com.evilco.plug.bot.core.event.communication.room;

import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.communication.data.RoomScore;
import com.evilco.plug.bot.core.event.communication.ApiEvent;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class RoomScoreUpdateEvent extends ApiEvent {

	/**
	 * Stores the new room score.
	 */
	protected RoomScore score = null;

	/**
	 * Constructs a new Room Score Update Event.
	 * @param source
	 * @param score
	 */
	public RoomScoreUpdateEvent (PageCommunicationAdapter source, RoomScore score) {
		super (source);

		this.score = score;
	}

	/**
	 * Returns the new room score.
	 * @return
	 */
	public RoomScore getScore () {
		return this.score;
	}
}
