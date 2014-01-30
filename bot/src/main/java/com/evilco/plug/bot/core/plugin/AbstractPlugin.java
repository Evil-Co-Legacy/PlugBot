package com.evilco.plug.bot.core.plugin;

import com.evilco.plug.bot.core.plugin.annotation.Plugin;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class AbstractPlugin implements IPlugin {

	/**
	 * Stores the plugin manager.
	 */
	@Autowired
	private PluginManager pluginManager;

	/**
	 * Returns a suggested configuration file.
	 * @return
	 */
	public File getConfigurationFile () {
		// verify instance
		if (!this.getClass ().isAnnotationPresent (Plugin.class)) return null;

		// return file
		return (new File (this.getDataDirectory (), "configuration.xml"));
	}

	/**
	 * Returns the data directory.
	 * @return
	 */
	public File getDataDirectory () {
		// verify instance
		if (!this.getClass ().isAnnotationPresent (Plugin.class)) return null;

		// get directory
		File directory = new File (this.pluginManager.getPluginDirectory (), this.getClass ().getAnnotation (Plugin.class).name ());

		// create directory
		if (!directory.exists ()) directory.mkdirs ();

		// done
		return directory;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onEnable () { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDisable () { }
}
