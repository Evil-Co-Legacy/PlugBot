package com.evilco.plug.bot.core.plugin;

import com.evilco.plug.bot.core.Bot;
import com.evilco.plug.bot.core.event.EventManager;
import com.evilco.plug.bot.core.event.plugin.PluginLoadEvent;
import com.evilco.plug.bot.core.event.plugin.PluginUnloadEvent;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Component
public class PluginManager {

	/**
	 * Defines the default plugin directory.
	 */
	public static final String PLUGIN_DIRECTORY = "plugins";

	/**
	 * Stores the internal logging instance.
	 */
	protected static final Logger logger = Logger.getLogger ("PluginManager");

	/**
	 * Caches the current bot instance.
	 */
	@Autowired
	@Qualifier ("Bot")
	protected Bot bot;

	/**
	 * Caches the current event manager instance.
	 */
	@Autowired
	protected EventManager eventManager;

	/**
	 * Stores the plugin map.
	 */
	protected Map<String, PluginClassLoader> pluginMap;

	/**
	 * Constructs a new empty class loader.
	 */
	public PluginManager () {
		logger.info ("Initializing the plugin manager instance ...");

		// create map
		this.pluginMap = new HashMap<String, PluginClassLoader> ();

		// load all plugins
		this.loadAll ();
	}

	/**
	 * Returns a plugin instance (if loaded).
	 * @param name
	 * @return
	 */
	public IPlugin getPlugin (String name) {
		return this.pluginMap.get (name).getPlugin ();
	}

	/**
	 * Returns the current plugin directory.
	 * @return
	 */
	public File getPluginDirectory () {
		File directory = new File (Bot.getApplicationDirectory (), PLUGIN_DIRECTORY);

		// create directory
		if (!directory.exists ()) directory.mkdirs ();

		// ok
		return directory;
	}

	/**
	 * Checks whether a plugin is loaded.
	 * @param name
	 * @return
	 */
	public boolean isLoaded (String name) {
		return this.pluginMap.containsKey (name);
	}

	/**
	 * Loads a plugin.
	 * @param pluginFile
	 * @throws PluginLoaderException
	 */
	public void load (File pluginFile) throws PluginLoaderException {
		// load application container
		PluginClassLoader loader = null;

		try {
			loader = new PluginClassLoader (pluginFile, this.getClass ().getClassLoader ());
		} catch (MalformedURLException ex) {
			throw new PluginLoaderException (ex);
		}

		// store application container
		this.pluginMap.put (loader.getMetadata ().name (), loader);

		// enable plugin
		try {
			loader.getPlugin ().onEnable ();
		} catch (Exception ex) {
			throw new PluginLoaderException (ex);
		}

		// fire event
		PluginLoadEvent event = new PluginLoadEvent (loader.getPlugin ());
		this.eventManager.fireEvent (event);

		// log
		logger.info ("Loaded plugin \"" + loader.getMetadata ().name () + "\" (version " + loader.getMetadata ().version () + ") created by " + (loader.getMetadata ().author ().isEmpty () ? "Unknown" : loader.getMetadata ().author ()) + " from file " + pluginFile.getName () + ".");
	}

	/**
	 * Loads all plugins.
	 */
	public void loadAll () {
		logger.info ("Loading all available plugins ...");

		// get iterator
		Iterator<File> it = FileUtils.iterateFiles (this.getPluginDirectory (), (new String[] {"jar"}), false);

		// iterate over files
		int pluginCount = 0;

		while (it.hasNext ()) {
			File file = it.next ();

			// load plugin
			try {
				this.load (file);
				pluginCount++;
			} catch (PluginLoaderException ex) {
				logger.log (Level.SEVERE, "Cannot load plugin: " + file.getName (), ex);
			}
		}

		// log
		logger.info ("Loaded " + pluginCount + " plugins.");
	}

	/**
	 * Reloads all plugins.
	 */
	public void reload () {
		logger.info ("Reloading ...");
		long time = System.currentTimeMillis ();

		// reload
		this.unloadAll ();
		this.loadAll ();

		// log
		logger.info ("Reload finished in " + (System.currentTimeMillis () - time) + " ms.");
	}

	/**
	 * Unloads a plugin.
	 * @param name
	 */
	public void unload (String name) {
		// get plugin
		IPlugin plugin = this.pluginMap.get (name).getPlugin ();

		// call onDisable
		try {
			plugin.onDisable ();
		} catch (Exception ex) {
			logger.log (Level.SEVERE, "An exception occurred during the disable sequence of the plugin \"" + name + "\".", ex);
		}

		// fire event
		PluginUnloadEvent event = new PluginUnloadEvent (plugin);
		this.eventManager.fireEvent (event);

		// delete internal plugin instace
		plugin = null;

		// log
		logger.info ("Unloading plugin \"" + this.pluginMap.get (name).getMetadata ().name () + "\" ...");

		// delete class loader instance
		this.pluginMap.remove (name);

		// suggest garbage collection
		System.gc ();
	}

	/**
	 * Unloads all plugins.
	 */
	public void unloadAll () {
		logger.info ("Unloading all plugins ...");

		// unload
		for (Map.Entry<String, PluginClassLoader> plugin : this.pluginMap.entrySet ()) {
			this.unload (plugin.getKey ());
			plugin.setValue (null);
		}

		// suggest garbage collection
		System.gc ();

		// info
		logger.info ("All plugins have been unloaded.");
	}
}
