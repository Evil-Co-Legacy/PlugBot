package com.evilco.plug.bot.core.event.system;

import com.evilco.plug.bot.core.Bot;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class AuthenticationInitializeEvent extends AuthenticationEvent {

	/**
	 * Constructs a new authentication initialize event.
	 * @param bot
	 */
	public AuthenticationInitializeEvent (Bot bot) {
		super (bot);
	}
}
