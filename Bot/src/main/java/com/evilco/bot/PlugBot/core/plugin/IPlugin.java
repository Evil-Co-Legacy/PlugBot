package com.evilco.bot.PlugBot.core.plugin;

import com.evilco.bot.PlugBot.Bot;
import com.evilco.bot.PlugBot.core.plugin.annotation.Plugin;
import org.apache.logging.log4j.Logger;

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
	 * Returns the suggested configuration file for this plugin.
	 * @return
	 */
	public File GetConfigurationFile ();

	/**
	 * Returns the plugin log provider.
	 * @return
	 */
	public Logger GetLog ();

	/**
	 * Returns the plugin metadata.
	 * @return
	 */
	public Plugin GetMetadata ();

	/**
	 * Returns the plugin file (JAR).
	 * @return
	 */
	public File GetFile ();

	/**
	 * Initializes the plugin.
	 * @param bot
	 * @param metadata
	 * @param pluginFile
	 * @param configurationFile A suggested configuration file for the plugin.
	 */
	public void Initialize (Bot bot, Plugin metadata, File pluginFile, File configurationFile);

	/**
	 * Event handler which occurs when a plugin is enabled.
	 */
	public void OnEnable ();

	/**
	 * Event handler which occurs when a plugin is disabled.
	 */
	public void OnDisable ();
}
