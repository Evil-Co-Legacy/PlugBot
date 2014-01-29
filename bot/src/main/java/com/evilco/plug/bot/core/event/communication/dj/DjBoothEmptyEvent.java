package com.evilco.plug.bot.core.event.communication.dj;

import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.event.communication.ApiEvent;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class DjBoothEmptyEvent extends ApiEvent {

	/**
	 * Constructs a new DJ Booth Empty Event.
	 * @param source
	 */
	public DjBoothEmptyEvent (PageCommunicationAdapter source) {
		super (source);
	}
}
