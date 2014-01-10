package com.evilco.bot.PlugBot.core.plugin;

import com.evilco.bot.PlugBot.Bot;
import com.evilco.bot.PlugBot.core.plugin.annotation.Plugin;

import java.io.File;

/**
 * @package com.evilco.bot.PlugBot.core.plugin
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public abstract class AbstractPlugin implements IPlugin {

	/**
	 * Stores the current bot instance.
	 */
	private Bot bot = null;

	/**
	 * Stores the plugin file.
	 */
	private File file = null;

	/**
	 * Stores the plugin metadata.
	 */
	private Plugin metadata = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Bot GetBot () {
		return this.bot;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void Initialize (Bot bot, Plugin metadata, File pluginFile) {
		this.bot = bot;
		this.metadata = metadata;
		this.file = pluginFile;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void OnEnable () { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void OnDisable () { }
}
