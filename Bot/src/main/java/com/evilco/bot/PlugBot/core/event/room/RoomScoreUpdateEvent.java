package com.evilco.bot.PlugBot.core.event.room;

import com.evilco.bot.PlugBot.core.data.PlugVotes;
import com.evilco.bot.PlugBot.core.event.IEvent;

/**
 * @package com.evilco.bot.PlugBot.core.event.room
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class RoomScoreUpdateEvent implements IEvent {

	/**
	 * Stores the current score.
	 */
	public PlugVotes votes;

	/**
	 * Constructs a new RoomScoreUpdateEvent.
	 * @param votes
	 */
	public RoomScoreUpdateEvent (PlugVotes votes) {
		this.votes = votes;
	}
}
