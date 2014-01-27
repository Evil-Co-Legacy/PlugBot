package com.evilco.plug.bot.core.event.system;

import com.evilco.plug.bot.core.Bot;
import org.springframework.context.ApplicationEvent;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public abstract class AuthenticationEvent extends ApplicationEvent {

	/**
	 * Constructs a new authentication event.
	 * @param bot
	 */
	public AuthenticationEvent (Bot bot) {
		super (bot);
	}
}
