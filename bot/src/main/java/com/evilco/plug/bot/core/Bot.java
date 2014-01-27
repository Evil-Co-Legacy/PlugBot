package com.evilco.plug.bot.core;

import com.evilco.plug.bot.core.authentication.IAuthenticationProvider;
import com.evilco.plug.bot.core.configuration.BotConfiguration;
import com.evilco.plug.bot.core.configuration.CoreBeanConfiguration;
import com.evilco.plug.bot.core.driver.ChromeDriverDownloader;
import com.evilco.plug.bot.core.driver.WebDriverType;
import com.evilco.plug.bot.core.event.EventManager;
import com.evilco.plug.bot.core.event.system.AuthenticationInitializeEvent;
import com.evilco.plug.bot.core.event.system.AuthenticationSuccessEvent;
import com.evilco.plug.bot.core.plugin.PluginManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Component
@Configuration
public class Bot implements Runnable, ApplicationContextAware {

	/**
	 * Defines the authentication timeout (60 seconds).
	 */
	public static final long AUTHENTICATION_TIMEOUT = 60000;

	/**
	 * Defines the plug.dj base URL (homepage).
	 */
	public static final String PLUG_BASE_URL = "http://plug.dj";

	/**
	 * Defines the browser window size.
	 */
	public static final Dimension WINDOW_DIMENSIONS = new Dimension (1280, 720);

	/**
	 * Stores the internal logger.
	 */
	public static final Logger logger = Logger.getLogger ("PlugBot");

	/**
	 * Stores the current application context.
	 */
	protected ApplicationContext applicationContext = null;

	/**
	 * Stores the bot configuration.
	 */
	@Autowired
	protected BotConfiguration configuration;

	/**
	 * Stores the driver to use for communication.
	 */
	@Autowired
	protected WebDriver driver;

	/**
	 * Stores the event manager instance.
	 */
	@Autowired
	protected EventManager eventManager;

	/**
	 * Constructs a new bot.
	 */
	public Bot () { }

	/**
	 * Authenticates the bot.
	 */
	protected void authenticate () {
		// log
		logger.info ("Starting authentication ...");

		// fire event
		AuthenticationInitializeEvent event = new AuthenticationInitializeEvent (this);
		this.eventManager.fireEvent (event);

		// get current time
		long authenticationStart = System.currentTimeMillis ();

		// get correct authentication provider
		IAuthenticationProvider provider = ((IAuthenticationProvider) this.applicationContext.getBean ("authenticationProvider"));

		// announce provider
		logger.info ("Using authentication provider \"" + provider.getProviderName () + "\".");

		// log in
		provider.authenticate (this.configuration.account.username, this.configuration.account.password);

		// log
		logger.info ("Finished authentication. Waiting for plug.dj ...");

		// wait for plug.dj
		while (!this.driver.getCurrentUrl ().contains ("/communities") && ((System.currentTimeMillis () - authenticationStart) < AUTHENTICATION_TIMEOUT));

		// handle errors
		if (!this.driver.getCurrentUrl ().contains ("/communities")) throw new RuntimeException ("Authentication with plug.dj timed out.");

		// log
		logger.info ("Successfully authenticated with plug.dj!");

		// fire event
		AuthenticationSuccessEvent successEvent = new AuthenticationSuccessEvent (this);
		this.eventManager.fireEvent (event);
	}

	/**
	 * Returns the application directory.
	 * @return
	 */
	public static File getApplicationDirectory () {
		// get file reference
		File directory = new File (System.getProperty ("user.dir"));

		// create directory (if it does not exist yet)
		if (!directory.exists ()) directory.mkdirs ();

		// return
		return directory;
	}

	/**
	 * Returns the current driver instance.
	 * @return
	 */
	public WebDriver getDriver () {
		return this.driver;
	}

	/**
	 * Returns the default library directory.
	 * @return
	 */
	public static File getLibraryDirectory () {
		// get file reference
		File directory = new File (getApplicationDirectory (), "lib");

		// create directory (if it does not exist yet)
		if (!directory.exists ()) directory.mkdirs ();

		// return
		return directory;
	}

	/**
	 * Initializes the plugin manager.
	 */
	protected void initializePluginManager () {
		((PluginManager) this.applicationContext.getBean ("pluginManager")).loadAll ();
	}

	/**
	 * Initializes the web driver instance.
	 */
	protected void initializeWebDriver () {
		this.applicationContext.getBean ("driver");
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

		// initialize bot
		this.initializeWebDriver ();
		this.initializePluginManager ();

		// authenticate
		this.authenticate ();

		// start interface

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setApplicationContext (ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * Tries to shut down all bot components.
	 */
	public void shutdown () {
		// shutdown driver
		try {
			this.driver.close ();
		} catch (Exception ex) { }

		// get registry
		DefaultListableBeanFactory registry = ((DefaultListableBeanFactory) this.applicationContext.getAutowireCapableBeanFactory());

		// delete singleton instances
		registry.destroySingleton ("authenticationProvider");
		registry.destroySingleton ("pluginManager");
		registry.destroySingleton ("eventManager");
		registry.destroySingleton ("driver");
		registry.destroySingleton ("Bot");
	}
}
