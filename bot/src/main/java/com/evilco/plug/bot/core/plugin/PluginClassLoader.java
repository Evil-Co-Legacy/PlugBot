package com.evilco.plug.bot.core.plugin;

import com.evilco.plug.bot.core.plugin.annotation.Plugin;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PluginClassLoader extends URLClassLoader {

	/**
	 * Stores the internal plugin context.
	 */
	protected AnnotationConfigApplicationContext context;

	/**
	 * Stores the plugin file.
	 */
	protected final File file;

	/**
	 * Stores the plugin metadata.
	 */
	protected final Plugin metadata;

	/**
	 * Stores the internal plugin instance.
	 */
	protected final IPlugin plugin;

	/**
	 * Constructs a new plugin class loader.
	 * @param file
	 * @param parent
	 * @throws MalformedURLException
	 * @throws PluginLoaderException
	 */
	public PluginClassLoader (final File file, final ClassLoader parent, ApplicationContext applicationContext) throws MalformedURLException, PluginLoaderException {
		super ((new URL[] {file.toURI ().toURL ()}), parent);

		// store properties
		this.file = file;

		// search for all plugin classes
		Reflections reflections = this.getReflections ();

		// find all plugin instances
		Set<Class<?>> plugins = reflections.getTypesAnnotatedWith (Plugin.class);

		// check size
		if (plugins.size () == 0) throw new PluginLoaderException ("No plugin was found inside the plugin file.");
		if (plugins.size () > 1) throw new PluginLoaderException ("The plugin contains more than one plugin definition.");

		// find plugin
		Class<?> tmpClass = plugins.iterator ().next ();

		// get plugin class
		Class<? extends IPlugin> pluginClass = null;

		try {
			pluginClass = tmpClass.asSubclass (IPlugin.class);
		} catch (ClassCastException ex) {
			throw new PluginLoaderException (ex);
		}

		// construct context
		this.context = new AnnotationConfigApplicationContext ();
		this.context.setParent (applicationContext);
		this.context.setClassLoader (this);

		this.context.register (pluginClass);

		// refresh
		this.context.refresh ();

		// get an instance
		this.plugin = this.context.getBean (pluginClass);
		this.metadata = pluginClass.getAnnotation (Plugin.class);
	}

	/**
	 * Returns the plugin context.
	 * @return
	 */
	public ApplicationContext getContext () {
		return this.context;
	}

	/**
	 * Returns the plugin metadata.
	 * @return
	 */
	public Plugin getMetadata () {
		return this.metadata;
	}

	/**
	 * Returns the plugin instance.
	 * @return
	 */
	public IPlugin getPlugin () {
		return this.plugin;
	}

	/**
	 * Returns a reflections instance for this class loader.
	 * @return
	 */
	public Reflections getReflections () {
		return (new Reflections (new ConfigurationBuilder ().setUrls (ClasspathHelper.forClassLoader (this)).addClassLoader (this)));
	}
}
