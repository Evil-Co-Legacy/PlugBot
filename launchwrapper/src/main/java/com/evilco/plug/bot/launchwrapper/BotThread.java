package com.evilco.plug.bot.launchwrapper;

import com.evilco.plug.bot.core.Bot;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class BotThread extends Thread {

	/**
	 * Stores the internal bot instance.
	 */
	protected final Bot bot;

	/**
	 * Constructs a new bot thread.
	 * @param bot
	 */
	public BotThread (Bot bot) {
		this.bot = bot;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run () {
		this.bot.run ();
	}
}
