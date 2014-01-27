package com.evilco.plug.bot.core.event.plugin;

import com.evilco.plug.bot.core.plugin.IPlugin;
import org.springframework.context.ApplicationEvent;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public abstract class PluginEvent extends ApplicationEvent {

	/**
	 * Constructs a new plugin event.
	 * @param plugin
	 */
	public PluginEvent (IPlugin plugin) {
		super (plugin);
	}

	/**
	 * Returns the source plugin instance.
	 * @return
	 */
	public IPlugin getPlugin () {
		return ((IPlugin) this.source);
	}
}
