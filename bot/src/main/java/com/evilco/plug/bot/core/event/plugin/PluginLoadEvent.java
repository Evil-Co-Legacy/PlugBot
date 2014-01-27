package com.evilco.plug.bot.core.event.plugin;

import com.evilco.plug.bot.core.plugin.IPlugin;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PluginLoadEvent extends PluginEvent {

	/**
	 * Constructs a new PluginLoadEvent.
	 * @param plugin
	 */
	public PluginLoadEvent (IPlugin plugin) {
		super (plugin);
	}
}
