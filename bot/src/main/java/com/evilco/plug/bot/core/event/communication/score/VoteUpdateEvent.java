package com.evilco.plug.bot.core.event.communication.score;

import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.communication.data.User;
import com.evilco.plug.bot.core.communication.data.UserVote;
import com.evilco.plug.bot.core.event.communication.ApiEvent;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class VoteUpdateEvent extends ApiEvent {

	/**
	 * Stores the user who voted.
	 */
	protected User user = null;

	/**
	 * Stores the vote.
	 */
	protected UserVote vote = UserVote.UNKNOWN;

	/**
	 * Constructs a new Vote Update Event.
	 * @param source
	 * @param user
	 * @param vote
	 */
	public VoteUpdateEvent (PageCommunicationAdapter source, User user, int vote) {
		super (source);

		this.user = user;
		this.vote = UserVote.getVote (vote);
	}

	/**
	 * Returns the user who voted.
	 * @return
	 */
	public User getUser () {
		return this.user;
	}

	/**
	 * Returns the vote.
	 * @return
	 */
	public UserVote getVote () {
		return this.vote;
	}
}
