package com.evilco.plug.bot.core.configuration;

import com.evilco.plug.bot.core.Bot;
import com.evilco.plug.bot.core.authentication.GoogleAuthenticationProvider;
import com.evilco.plug.bot.core.authentication.IAuthenticationProvider;
import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.driver.ChromeDriverDownloader;
import com.evilco.plug.bot.core.driver.WebDriverType;
import com.evilco.plug.bot.core.event.EventManager;
import com.evilco.plug.bot.core.plugin.PluginManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Configuration
@ComponentScan (basePackages = {"com.evilco.plug.bot.core"})
public class CoreBeanConfiguration {

	/**
	 * Sotres the internal logger.
	 */
	protected static final Logger logger = Logger.getLogger ("CoreBeanConfiguration");

	/**
	 * Constructs a new authentication provider.
	 * @return
	 */
	@Bean
	@Scope ("singleton")
	public IAuthenticationProvider authenticationProvider () {
		// get configuration string
		String authenticationType = this.configuration ().account.type;

		// check built-in types
		if (authenticationType.equalsIgnoreCase ("google")) return new GoogleAuthenticationProvider ();

		// check plug-in types
		try {
			// get class
			Class<?> providerTempClass = Class.forName (authenticationType);

			// cast
			Class<? extends IAuthenticationProvider> providerClass = providerTempClass.asSubclass (IAuthenticationProvider.class);

			// create a new instance
			return providerClass.newInstance ();
		} catch (Exception ex) {
			throw new RuntimeException ("Could not load the provider type \"" + authenticationType + "\".", ex);
		}
	}

	@Bean (name = "Bot")
	@Scope ("singleton")
	public Bot bot () {
		return new Bot ();
	}

	/**
	 * Constructs a new bot configuration.
	 * @return
	 */
	@Bean
	@Scope ("singleton")
	public BotConfiguration configuration () {
		return BotConfiguration.newInstance ();
	}

	/**
	 * Constructs a new driver.
	 * @return
	 */
	@Bean
	@Scope ("singleton")
	public WebDriver driver () {
		// get configuration bean
		BotConfiguration configuration = this.configuration ();

		// find webdriver type
		WebDriverType driverType = WebDriverType.valueOf (configuration.driver.name.toUpperCase ());

		// verify
		if (driverType == null) throw new RuntimeException ("Cannot find the driver implementation for " + configuration.driver.name + ". Aborting launch.");

		// specify common capabilities
		DesiredCapabilities capabilities = new DesiredCapabilities ();
		capabilities.setJavascriptEnabled (true);

		// log
		logger.info ("Loading the driver implementation \"" + driverType.toString () + "\" ...");

		// create driver variable
		WebDriver driver = null;

		// create instance
		switch (driverType) {
			case CHROME:
				// download chrome driver
				ChromeDriverDownloader chromeDriverDownloader = new ChromeDriverDownloader ();
				if (!chromeDriverDownloader.exists ()) chromeDriverDownloader.run ();

				// set chrome path
				System.setProperty ("webdriver.chrome.driver", chromeDriverDownloader.getDriverFile ().getAbsolutePath ());

				// create driver instace
				driver = new ChromeDriver (capabilities);
				break;
			case FIREFOX:
				driver = new FirefoxDriver (capabilities);
				break;
			case REMOTE:
				try {
					driver = new RemoteWebDriver (new URL (configuration.driver.url), capabilities);
				} catch (MalformedURLException ex) {
					throw new RuntimeException ("The supplied remote driver URL is malformed.", ex);
				}
				break;
		}

		logger.info ("");
		logger.info ("The web driver has been initialized successfully.");

		// update window size
		driver.manage ().window ().setSize (Bot.WINDOW_DIMENSIONS);

		// browse to plug.dj
		driver.get (Bot.PLUG_BASE_URL);

		// wait for the page to completely process
		try { Thread.sleep (500); } catch (Exception ignore) { }

		// return the driver instance
		return driver;
	}

	/**
	 * Constructs a new event manager.
	 * @return
	 */
	@Bean
	@Scope ("singleton")
	public EventManager eventManager () {
		return (new EventManager ());
	}

	/**
	 * Constructs a new page communication adapter.
	 * @return
	 */
	@Bean
	@Scope ("singleton")
	public PageCommunicationAdapter pageCommunicationAdapter () {
		return (new PageCommunicationAdapter ());
	}

	/**
	 * Constructs a new plugin manager.
	 * @return
	 */
	@Bean
	@Scope ("singleton")
	public PluginManager pluginManager () {
		return (new PluginManager ());
	}
}
