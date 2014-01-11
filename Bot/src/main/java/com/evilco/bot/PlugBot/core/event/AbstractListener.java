package com.evilco.bot.PlugBot.core.event;

import com.evilco.bot.PlugBot.core.plugin.IPlugin;

/**
 * @package com.evilco.bot.PlugBot.core.event
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class AbstractListener implements IListener {

	/**
	 * Stores the parent plugin.
	 */
	protected IPlugin plugin = null;

	/**
	 * Constructs a new event listener.
	 * @param plugin
	 */
	public AbstractListener (IPlugin plugin) {
		this.plugin = plugin;
	}
}
