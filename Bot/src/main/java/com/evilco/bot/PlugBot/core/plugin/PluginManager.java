package com.evilco.bot.PlugBot.core.plugin;

import com.evilco.bot.PlugBot.Bot;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @package com.evilco.bot.PlugBot.core.plugin
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PluginManager {

	/**
	 * Defines the plugin configuration directory.
	 */
	public static final String CONFIG_DIRECTORY = "config";

	/**
	 * Defines which directory is used to store plugins.
	 */
	public static final String PLUGIN_DIRECTORY = "plugins";

	/**
	 * Stores the parent bot isntance.
	 */
	protected Bot bot;

	/**
	 * Stores all currently loaded plugins.
	 */
	protected Map<String, PluginClassLoader> pluginMap = null;

	/**
	 * Constructs a new PluginManager.
	 * @param bot
	 */
	public PluginManager (Bot bot) {
		this.bot = bot;

		// construct plugin map
		this.pluginMap = new HashMap<String, PluginClassLoader> ();
	}

	/**
	 * Returns the current configuration directory.
	 * @return
	 */
	public File GetConfigurationDirectory () {
		File directory = new File (CONFIG_DIRECTORY);

		// check whether directory has been created already
		if (!directory.exists ()) directory.mkdirs ();

		// return
		return directory;
	}

	/**
	 * Returns the file object for a plugin configuration.
	 * @param name
	 * @return
	 */
	protected File GetConfigurationFile (String name) {
		return (new File (this.GetConfigurationDirectory (), name + ".xml"));
	}

	/**
	 * Returns a loaded plugin.
	 * @param name
	 * @return
	 */
	public IPlugin GetPlugin (String name) {
		if (!this.pluginMap.containsKey (name)) return null;
		return this.pluginMap.get (name).GetPlugin ();
	}

	/**
	 * Returns the plugin directory which stores all plugin files.
	 * @return
	 */
	public File GetPluginDirectory () {
		File directory = new File (PLUGIN_DIRECTORY);

		// create if needed
		if (!directory.exists ()) directory.mkdirs ();

		// return directory
		return directory;
	}

	/**
	 * Initializes all plugins.
	 */
	public void Initialize () {
		// initialize all plugins
		for (Map.Entry<String, PluginClassLoader> pluginEntry : this.pluginMap.entrySet ()) {
			pluginEntry.getValue ().Initialize (this.bot, this.GetConfigurationFile (pluginEntry.getKey ()));
			pluginEntry.getValue ().GetPlugin ().OnEnable ();

			// log
			this.bot.log.info ("Enabled plugin {}.", pluginEntry.getKey ());
		}
	}


	/**
	 * Loads a plugin.
	 * @param pluginFile
	 * @return
	 */
	public boolean LoadPlugin (File pluginFile) {
		// construct new class loader
		try {
			// get loader
			PluginClassLoader loader = new PluginClassLoader (pluginFile, this.getClass ().getClassLoader (), this.bot);

			// store loader
			this.pluginMap.put (loader.GetPluginMetadata ().name (), loader);

			// log
			this.bot.log.info ("Loaded plugin {} (version {}) by {}.", loader.GetPluginMetadata ().name (), loader.GetPluginMetadata ().version (), loader.GetPluginMetadata ().author ());

			// plugin loaded successfully!
			return true;
		} catch (MalformedURLException ex) {
			this.bot.log.error ("Cannot load plugin from file {}: Malformed file URL: {}", pluginFile.getAbsolutePath (), ex.getMessage ());
			return false;
		} catch (InvalidPluginException ex) {
			this.bot.log.error ("Cannot load plugin from file {}: The plugin seems to be invalid: {}", pluginFile.getAbsolutePath (), ex.getMessage ());
			return false;
		}
	}

	/**
	 * Loads all plugins.
	 */
	public void LoadPlugins () {
		// get iterator for plugins directory
		Iterator<File> pluginFiles = FileUtils.iterateFiles (this.GetPluginDirectory (), new String[]{"jar", "zip"}, false);

		// iterate over all plugin files
		while (pluginFiles.hasNext ()) {
			this.LoadPlugin (pluginFiles.next ());
		}
	}

	/**
	 * Reloads all plugins.
	 */
	public void ReloadPlugins () {
		// log
		this.bot.log.info ("Reloading all plugins.");

		// unload plugins
		this.UnloadPlugins ();

		// load plugins
		this.LoadPlugins ();

		// log
		this.bot.log.info ("Finished reload.");
	}

	/**
	 * Unloads a plugin.
	 * @param name
	 * @return
	 */
	public boolean UnloadPlugin (String name) {
		// check
		if (!this.pluginMap.containsKey (name)) return false;

		// get plugin
		PluginClassLoader loader = this.pluginMap.get (name);

		// call unload
		loader.GetPlugin ().OnDisable ();

		// unload
		this.pluginMap.remove (name);

		// delete local reference
		loader = null;

		// manually issue GC
		System.gc (); // XXX: We're forcing GC here to make sure that everything gets deleted as soon as possible (in case of reloads)

		// log
		this.bot.log.info ("Unloaded plugin {}.", name);

		// just to keep everything the same way ...
		return true;
	}

	/**
	 * Unloads a plugin.
	 * @param pluginClassLoader
	 * @return
	 */
	public boolean UnloadPlugin (PluginClassLoader pluginClassLoader) {
		// check
		if (!this.pluginMap.containsValue (pluginClassLoader)) return false;

		// unload plugin
		for (Map.Entry<String, PluginClassLoader> pluginEntry : this.pluginMap.entrySet ()) {
			if (!pluginEntry.getValue ().equals (pluginClassLoader)) continue;

			// unload plugin
			this.UnloadPlugin (pluginEntry.getKey ());

			// done!
			return true;
		}

		// nothing found
		return false;
	}

	/**
	 * Unloads a plugin.
	 * @param plugin
	 * @return
	 */
	public boolean UnloadPlugin (IPlugin plugin) {
		// find corect loader
		for (Map.Entry<String, PluginClassLoader> pluginEntry : this.pluginMap.entrySet ()) {
			if (!pluginEntry.getValue ().GetPlugin ().equals (plugin)) continue;

			// unload plugin
			this.UnloadPlugin (pluginEntry.getKey ());

			// done
			return true;
		}

		// nothing found
		return false;
	}

	/**
	 * Unloads all plugins.
	 */
	public void UnloadPlugins () {
		for (Map.Entry<String, PluginClassLoader> pluginEntry : this.pluginMap.entrySet ()) {
			this.UnloadPlugin (pluginEntry.getKey ());
		}
	}
}
