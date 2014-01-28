package com.evilco.plug.bot.core.event.communication;

import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import org.springframework.context.ApplicationEvent;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public abstract class ApiEvent extends ApplicationEvent {

	/**
	 * Constructs a new API event.
	 * @param source
	 */
	public ApiEvent (PageCommunicationAdapter source) {
		super (source);
	}

	/**
	 * Returns the event sender.
	 * @return
	 */
	public PageCommunicationAdapter getAdapter () {
		return ((PageCommunicationAdapter) this.source);
	}
}
