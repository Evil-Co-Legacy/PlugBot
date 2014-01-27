package com.evilco.plug.bot.core.event.system;

import com.evilco.plug.bot.core.Bot;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class AuthenticationSuccessEvent extends AuthenticationEvent {

	/**
	 * Constructs a new AuthenticationSuccessEvent.
	 * @param bot
	 */
	public AuthenticationSuccessEvent (Bot bot) {
		super (bot);
	}
}
