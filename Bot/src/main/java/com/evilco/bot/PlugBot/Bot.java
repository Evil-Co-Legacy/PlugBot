package com.evilco.bot.PlugBot;

import com.evilco.bot.PlugBot.authentication.GoogleAuthenticator;
import com.evilco.bot.PlugBot.authentication.IAuthenticator;
import com.evilco.bot.PlugBot.bridge.PlugInterface;
import com.evilco.bot.PlugBot.command.CommandManager;
import com.evilco.bot.PlugBot.core.Platform;
import com.evilco.bot.PlugBot.core.configuration.Configuration;
import com.evilco.bot.PlugBot.core.configuration.ConfigurationException;
import com.evilco.bot.PlugBot.core.event.EventManager;
import com.evilco.bot.PlugBot.core.event.EventType;
import com.evilco.bot.PlugBot.core.plugin.PluginManager;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @package com.evilco.bot.PlugBot
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class Bot {

	/**
	 * Stores the name of the default configuration file.
	 */
	public static final String CONFIGURATION_FILE = "bot.xml";

	/**
	 * Defines the URL to use to access plug.dj.
	 */
	public static final String PLUG_URL = "http://plug.dj/";

	/**
	 * Defines the path to use to access the lobby.
	 */
	public static final String PLUG_LOBBY_PATH = "communities/";

	/**
	 * Stores the path to internal resources.
	 */
	public static final String RESOURCE_PATH = "/resources/";

	/**
	 * Stores the current command manager.
	 */
	protected CommandManager commandManager = null;

	/**
	 * Stores the application configuration.
	 */
	protected Configuration configuration = null;

	/**
	 * Stores the currently used browser driver.
	 */
	protected WebDriver currentDriver = null;

	/**
	 * Stores the currently used EventManager instance.
	 */
	protected EventManager eventManager = null;

	/**
	 * Stores the current Bot instance.
	 */
	protected static Bot instance = null;

	/**
	 * Indicates whether the bot is still alive.
	 */
	protected boolean isAlive = false;

	/**
	 * Stores the plugin manager instance.
	 */
	protected PluginManager pluginManager = null;

	/**
	 * Stores the current plug.dj interface (used to unify plug.dj APIs in Java).
	 */
	protected PlugInterface plugInterface = null;

	/**
	 * The log interface.
	 */
	public Logger log;

	/**
	 * Constructs a new instance of Bot.
	 * @param args
	 */
	protected Bot (String[] args) {
		// extract log configuration
		File logConfiguration = new File ("log4j2.xml");
		if (!logConfiguration.exists ()) this.ExtractFile ("log4j2.xml", "log4j2.xml");

		// set configuration file
		System.setProperty ("log4j.configurationFile", "log4j2.xml");

		// create logger
		this.log = LogManager.getLogger ("PlugBot");

		// print start info
		this.log.info ("PlugBot v"); // TODO: Add version
		this.log.info ("Copyright (C) 2014 Evil-Co <http://www.evil-co.org>");
		this.log.info ("---------------------------------------------------");

		// check current platform
		if (GetPlatform () == Platform.UNKNOWN) {
			this.log.error ("Your platform seems to be not supported at this time: " + System.getProperty("os.name"));
			this.log.error ("Please use one of the following platforms: *NIX, Mac OS X, Windows");
			this.log.error ("Also please remember to submit a bug report with this message to get your platform added to the supported systems.");
			throw new RuntimeException ("Unsupported Platform");
		}

		// TODO: Parse CMD arguments here

		// read configuration
		try {
			this.configuration = Configuration.GetInstance (CONFIGURATION_FILE);
		} catch (ConfigurationException ex) {
			throw new RuntimeException ("Could read/write bot configuration file!", ex);
		}

		// check whether a restart is needed
		if (this.configuration == null) {
			this.log.info ("No configuration could be found.");
			this.log.info ("An empty configuration file has been created.");
			this.log.info ("Please modify it and re-start the bot.");
			throw new RuntimeException ("An empty configuration file is being used.");
		}

		// create event manager
		this.eventManager = new EventManager (this);

		// start plugin manager
		this.pluginManager = new PluginManager (this);
	}

	/**
	 * Extract a file from the JAR.
	 * @param sourceFileName
	 * @param targetFileName
	 * @throws IOException
	 */
	protected void ExtractFile (String sourceFileName, String targetFileName) {
		// define variables
		InputStream in = null;
		OutputStream out = null;

		try {
			// get resource file
			in = Bot.class.getResourceAsStream (RESOURCE_PATH + sourceFileName);

			// get output file
			out = new FileOutputStream ((new File (targetFileName)));

			// verify streams
			if (in == null) throw new RuntimeException ("Could not find \"" + sourceFileName + "\" in your JAR. Please re-install the application.");

			// copy file
			IOUtils.copy (in, out);

			// close both
		} catch (IOException ex) {
			ex.printStackTrace ();
		} finally {
			// close streams
			try { in.close (); } catch (Exception ignore) { }
			try { out.close (); } catch (Exception ignore) { }
		}
	}

	/**
	 * Returns the currently used command manager.
	 * @return
	 */
	public CommandManager GetCommandManager () {
		return this.commandManager;
	}

	/**
	 * Returns the currently used driver.
	 * @return
	 */
	public WebDriver GetDriver () {
		return this.currentDriver;
	}

	/**
	 * Returns the current event manager instance.
	 * @return
	 */
	public EventManager GetEventManager () {
		return this.eventManager;
	}

	/**
	 * Returns the current plug interface instance.
	 * @return
	 */
	public PlugInterface GetInterface () {
		return this.plugInterface;
	}

	/**
	 * Returns the current plugin manager instance.
	 * @return
	 */
	public PluginManager GetPluginManager () {
		return this.pluginManager;
	}

	/**
	 * Returns the platform we're currently on.
	 * @return
	 */
	public static Platform GetPlatform () {
		String osName = System.getProperty("os.name").toLowerCase();

		// detect system
		if (osName.contains("win"))
			return Platform.WINDOWS;
		if (osName.contains("mac"))
			return Platform.MAC_OS_X;
		if (osName.contains("solaris") || osName.contains("sunos"))
			return Platform.SOLARIS;
		if (osName.contains("linux"))
			return Platform.LINUX;
		if (osName.contains("unix"))
			return Platform.LINUX;

		return Platform.UNKNOWN;
	}

	/**
	 * Starts the bot.
	 */
	public void Start () {
		// load plugins
		this.pluginManager.LoadPlugins ();

		// create proper driver
		if (this.configuration.system.driver.equalsIgnoreCase ("firefox")) {
			// construct capability
			DesiredCapabilities capabilities = DesiredCapabilities.firefox ();
			capabilities.setJavascriptEnabled (true);

			// load driver
			this.currentDriver = new FirefoxDriver (capabilities);
		} else if (this.configuration.system.driver.equalsIgnoreCase ("chrome")) {
			// load binary
			switch (GetPlatform ()) {
				case WINDOWS:
					System.setProperty ("webdriver.chrome.driver", "chromedriver.exe");
					break;
				case MAC_OS_X:
				case LINUX:
				case SOLARIS:
					System.setProperty ("webdriver.chrome.driver", "chromedriver");
					break;
			}

			// construct capability
			DesiredCapabilities capabilities = DesiredCapabilities.chrome ();
			capabilities.setJavascriptEnabled (true);

			// create new driver
			this.currentDriver = new ChromeDriver (capabilities);
		}

		// setup driver properties
		this.currentDriver.manage ().window ().setSize (new Dimension (1280, 720));

		// create command manager
		this.commandManager = new CommandManager (this);

		// create interface
		this.plugInterface = new PlugInterface (this.currentDriver);

		// request plug.dj
		this.currentDriver.get (PLUG_URL);

		// request authentication
		IAuthenticator authenticator = null;

		if (this.configuration.account.type.equalsIgnoreCase ("google"))
			authenticator = new GoogleAuthenticator (this.configuration.account);
		else {
			this.log.error ("The supplied account type is currently not supported: " + this.configuration.account.type);
			this.log.error ("Currently supported methods are: google");
			this.log.error ("Please re-configure the bot to use the correct account type.");
			throw new RuntimeException ("Unknown Account Type");
		}

		// log
		this.log.info ("Starting authentication with provider " + authenticator.getClass ().getCanonicalName () + ".");

		// start authentication
		WebElement element = this.currentDriver.findElement(By.id (authenticator.GetProviderName ()));
		element.click ();

		// log
		this.log.info ("Reached provider authentication page.");

		// authenticate
		authenticator.Authenticate (this.currentDriver);

		// wait for driver to finish
		try {
			int tries = 0;

			while (!this.currentDriver.getCurrentUrl ().equalsIgnoreCase (PLUG_URL + PLUG_LOBBY_PATH) && tries < 40) {
				Thread.sleep (500);
				tries++;
			}

			if (tries >= 40) {
				this.log.error ("plug.dj needed more than 20 seconds to answer correctly. Aborting execution.");
				throw new RuntimeException ("plug.dj authentication did not finish in 20 seconds. The service might be down or an authentication problem occurred.");
			}
		} catch (InterruptedException ex) {
			this.log.error ("Got interrupted during plug.dj authentication sequence. Application state is unknown.", ex);
		}

		// log
		this.log.info ("Authentication successfull.");
		this.log.info ("Joining requested channel: " + this.configuration.system.channel);

		// join channel
		this.currentDriver.get (PLUG_URL + this.configuration.system.channel);

		// wait for plug.dj
		try {
			Thread.sleep (10000);
		} catch (InterruptedException ex) {
			this.log.error ("Got interrupted during plug.dj authentication sequence. Application state is unknown.", ex);
		}

		// inject main handler interface
		this.eventManager.InjectHandler ();

		// inject JavaScript interfaces
		this.eventManager.Inject (EventType.ALL);

		// initialize plugins
		this.pluginManager.Initialize ();
	}

	/**
	 * Listens to events.
	 */
	public void Listen () {
		// set state to "alive"
		this.isAlive = true;

		// enter poll loop
		while (this.isAlive) {
			// poll events
			this.eventManager.PollEvents ();

			// TODO: Call plugins

			// sleep for a few ms
			try {
				Thread.sleep (500);
			} catch (InterruptedException ex) {
				this.log.error ("Got interrupted during poll sequence. Application state is unknown.", ex);
			}
		}

		// shut down
		this.Shutdown ();
	}

	/**
	 * Requests a complete bot shutdown.
	 */
	public void RequestShutdown () {
		this.isAlive = false;
	}

	/**
	 * Shuts down the bot.
	 */
	protected void Shutdown () {
		// unload plugins
		this.pluginManager.UnloadPlugins ();

		// shut down browser
		this.currentDriver.quit ();

		// free resources
		this.currentDriver = null;
		this.pluginManager = null;
		this.eventManager = null;
	}

	/**
	 * Main Entry Point
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// create new bot instance
			instance = new Bot (args);

			// go into normal processing mode
			instance.Start ();

			// enter poll mode
			instance.Listen ();
		} catch (Exception ex) {
			// write to log (if available)
			try {
				instance.log.error ("The bot has crashed. A restart has been queued to restore the correct application state.");
			} catch (Exception e) { }

			// create dump fileName
			StringBuilder dumpFile = new StringBuilder ("crash-");

			TimeZone timeZone = TimeZone.getTimeZone("UTC");
			DateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			dateFormat.setTimeZone (timeZone);
			dumpFile.append (dateFormat.format (new Date ()));

			// write crash dumb
			try {
				FileWriter fileWriter = new FileWriter (dumpFile.toString (), true);
				PrintWriter printWriter = new PrintWriter (fileWriter);

				// write exception
				ex.printStackTrace (printWriter);
			} catch (IOException e) { }

			// write log (if available)
			try {
				instance.log.error ("Restarting ...");
			} catch (Exception e) { }

			// delete instance
			try {
				instance.Shutdown ();
			} catch (Exception e) { }

			// completely delete it
			instance = null;

			// queue garbage collection
			System.gc ();

			// try to restart application (until Java kills us)
			main (args);
		}
	}
}
