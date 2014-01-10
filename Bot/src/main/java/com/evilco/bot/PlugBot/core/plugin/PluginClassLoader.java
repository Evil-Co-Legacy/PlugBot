package com.evilco.bot.PlugBot.core.plugin;

import com.evilco.bot.PlugBot.Bot;
import com.evilco.bot.PlugBot.core.event.IListener;
import com.evilco.bot.PlugBot.core.plugin.annotation.Plugin;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

/**
 * @package com.evilco.bot.PlugBot.core.plugin
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PluginClassLoader extends URLClassLoader {

	/**
	 * The file which contains the plugin.
	 */
	protected File file;

	/**
	 * Stores the contained plugin instance.
	 */
	protected IPlugin plugin;

	/**
	 * Stores the plugin metadata.
	 */
	protected Plugin pluginMetadata;

	/**
	 * Constructs a new plugin class loader.
	 * @param file
	 * @param parent
	 * @throws MalformedURLException
	 */
	public PluginClassLoader (final File file, final ClassLoader parent, Bot bot) throws MalformedURLException, InvalidPluginException {
		super ((new URL[] { file.toURI ().toURL () }), parent);

		// store file
		this.file = file;

		// create reflections instance
		Reflections reflections = new Reflections (ClasspathHelper.forClassLoader (this));

		// get annotations
		Set<Class<?>> plugins = reflections.getTypesAnnotatedWith (Plugin.class);

		// verify set
		if (plugins.isEmpty ()) throw new InvalidPluginException ("The plugin file does not contain any plugin definitions.");
		if (plugins.size () > 1) throw new InvalidPluginException ("The plugin file contains more than one plugin definition.");

		// get plugin class
		for (Class<?> clazz : plugins) {
			// cast
			Class<? extends IPlugin> pluginClass;

			try {
				pluginClass = clazz.asSubclass (IPlugin.class);
			} catch (ClassCastException ex) {
				throw new InvalidPluginException ("Plugins need to inherit from IPlugin.");
			}

			// create a new instance
			try {
				this.plugin = pluginClass.newInstance ();
				this.pluginMetadata = this.plugin.getClass ().getAnnotation (Plugin.class);
			} catch (IllegalAccessException ex) {
				throw new InvalidPluginException ("Plugin constructors need to be public.");
			} catch (InstantiationException ex) {
				throw new InvalidPluginException ("Everything is in abnormal parameters ...");
			}

			// fuck this loop (I'm just lazy, sorry)
			// there might be support for multiple plugins in one file at some point though
			break;
		}

		// search for event listeners and register them
		Set<Class<? extends IListener>> listeners = reflections.getSubTypesOf (IListener.class);

		// check classes
		for (Class<? extends IListener> clazz : listeners) {
			// create new instance
			try {
				// find correct constructor
				Constructor<? extends IListener> constructor = clazz.getConstructor (IPlugin.class);

				// call constructor
				IListener listener = constructor.newInstance (this.plugin);

				// register listener
				bot.GetEventManager ().RegisterListener (listener, this.plugin);
			} catch (NoSuchMethodException ex) {
				bot.log.warn ("The listener " + clazz.getName () + " could not be initialized automatically. No constructor accepts one parameter of type IPlugin.");
			} catch (IllegalAccessException ex) {
				bot.log.warn ("The listener " + clazz.getName () + " could not be initialized automatically. The constructor is protected from external calls.");
			} catch (InstantiationException ex) {
				bot.log.warn ("The listener " + clazz.getName () + " could not be initialized automatically. An instantiation of the class failed.");
			} catch (InvocationTargetException ex) {
				bot.log.warn ("The listener " + clazz.getName () + " could not be initialized automatically. An instantiation of the class failed.");
			}
		}
	}

	/**
	 * Returns the plugin file.
	 * @return
	 */
	public File GetFile () {
		return this.file;
	}

	/**
	 * Returns the loaded plugin.
	 * @return
	 */
	public IPlugin GetPlugin () {
		return this.plugin;
	}

	/**
	 * Returns the plugin metadata.
	 * @return
	 */
	public Plugin GetPluginMetadata () {
		return this.pluginMetadata;
	}

	/**
	 * Initializes the plugin.
	 */
	public void Initialize (Bot bot) {
		// initialize plugin
		this.plugin.Initialize (bot, this.pluginMetadata, this.file);
	}
}
