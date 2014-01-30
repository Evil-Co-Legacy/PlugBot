package com.evilco.plug.bot.core.plugin;

import com.evilco.plug.bot.core.plugin.annotation.Plugin;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.io.File;
import java.util.logging.Logger;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class AbstractPlugin implements IPlugin, ApplicationContextAware {

	/**
	 * Caches the internal application context.
	 */
	private ApplicationContext context = null;

	private Logger logger = null;

	/**
	 * Constructs a new plugin.
	 */
	public AbstractPlugin () {
		this.logger = (this.getClass ().isAnnotationPresent (Plugin.class) ? Logger.getLogger (this.getClass ().getAnnotation (Plugin.class).name ()) : Logger.getAnonymousLogger ());
	}

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
		File directory = new File (((PluginManager) this.context.getBean ("pluginManager")).getPluginDirectory (), this.getClass ().getAnnotation (Plugin.class).name ());

		// create directory
		if (!directory.exists ()) directory.mkdirs ();

		// done
		return directory;
	}

	/**
	 * Returns the current logger instance.
	 * @return
	 */
	public Logger getLogger () {
		return this.logger;
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setApplicationContext (ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
}
