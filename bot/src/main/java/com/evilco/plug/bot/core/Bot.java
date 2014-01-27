package com.evilco.plug.bot.core;

import com.evilco.plug.bot.core.configuration.BotConfiguration;
import com.evilco.plug.bot.core.configuration.CoreBeanConfiguration;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.logging.Logger;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@ComponentScan
@Import (CoreBeanConfiguration.class)
@Configuration
@Component
public class Bot implements Runnable {

	/**
	 * Stores the internal logger.
	 */
	public static final Logger logger = Logger.getLogger ("PlugBot");

	/**
	 * Stores the bot configuration.
	 */
	@Autowired
	protected BotConfiguration configuration;

	/**
	 * Stores the driver to use for communication.
	 */
	protected WebDriver driver = null;

	/**
	 * Constructs a new bot.
	 */
	public Bot () { }

	/**
	 * Returns the application directory.
	 * @return
	 */
	public static File getApplicationDirectory () {
		// get file reference
		File directory = new File (System.getProperty ("user.dir"));

		// create directory (if it does not exist yet)
		directory.mkdirs ();

		// return
		return directory;
	}

	/**
	 * Constructs a new bot instance.
	 * @todo Find a better solution for this problem ...
	 * @return
	 */
	@Bean (name = "Bot")
	@Scope ("prototype")
	public static Bot newInstance () {
		return (new Bot ());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void run () {
		// wait for launcher
		try { Thread.sleep (2200); } catch (Exception ex) { }

		// log startup note
		logger.info ("Starting a new PlugBot instance ...");

		// test
		logger.info ("Using " + this.configuration.account.type + " auth...");
	}

	/**
	 * Tries to shut down all bot components.
	 */
	public void shutdown () {
		// TODO
	}
}
