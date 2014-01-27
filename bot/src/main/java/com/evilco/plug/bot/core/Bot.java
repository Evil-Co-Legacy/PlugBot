package com.evilco.plug.bot.core;

import com.evilco.plug.bot.core.configuration.BotConfiguration;
import com.evilco.plug.bot.core.configuration.CoreBeanConfiguration;
import com.evilco.plug.bot.core.driver.ChromeDriverDownloader;
import com.evilco.plug.bot.core.driver.WebDriverType;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class Bot implements Runnable {

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
		if (!directory.exists ()) directory.mkdirs ();

		// return
		return directory;
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
	 * Initializes the web driver instance.
	 */
	protected void initializeWebDriver () {
		// find webdriver type
		WebDriverType driverType = WebDriverType.valueOf (this.configuration.driver.name.toUpperCase ());

		// verify
		if (driverType == null) throw new RuntimeException ("Cannot find the driver implementation for " + this.configuration.driver.name + ". Aborting launch.");

		// specify common capabilities
		DesiredCapabilities capabilities = new DesiredCapabilities ();
		capabilities.setJavascriptEnabled (true);

		// create instance
		switch (driverType) {
			case CHROME:
				// download chrome driver
				ChromeDriverDownloader chromeDriverDownloader = new ChromeDriverDownloader ();
				if (!chromeDriverDownloader.exists ()) chromeDriverDownloader.run ();

				// set chrome path
				System.setProperty ("webdriver.chrome.driver", chromeDriverDownloader.getDriverFile ().getAbsolutePath ());

				// create driver instace
				this.driver = new ChromeDriver (capabilities);
				break;
			case FIREFOX:
				this.driver = new FirefoxDriver (capabilities);
				break;
			case REMOTE:
				try {
					this.driver = new RemoteWebDriver (new URL (this.configuration.driver.url), capabilities);
				} catch (MalformedURLException ex) {
					throw new RuntimeException ("The supplied remote driver URL is malformed.", ex);
				}
				break;
		}

		// update window size
		this.driver.manage ().window ().setSize (WINDOW_DIMENSIONS);

		// browse to plug.dj
		this.driver.get (PLUG_BASE_URL);
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

		// create web driver
		this.initializeWebDriver ();
	}

	/**
	 * Tries to shut down all bot components.
	 */
	public void shutdown () {
		// TODO
	}
}
