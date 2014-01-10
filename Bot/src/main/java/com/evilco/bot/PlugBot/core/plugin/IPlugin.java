package com.evilco.bot.PlugBot.core.plugin;

import com.evilco.bot.PlugBot.Bot;

import java.io.File;

/**
 * @package com.evilco.bot.PlugBot.core.plugin
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IPlugin {

	/**
	 * Returns the currently associated bot instance.
	 * @return
	 */
	public Bot GetBot ();


	/**
	 * Initializes the plugin.
	 * @param loader
	 * @param name
	 * @param description
	 * @param version
	 * @param author
	 * @param pluginFile
	 */
	public void Initialize (Bot bot, String name, String description, String version, String author, File pluginFile);

	/**
	 * Event handler which occurs when a plugin is enabled.
	 */
	public void OnEnable ();

	/**
	 * Event handler which occurs when a plugin is disabled.
	 */
	public void OnDisable ();
}
