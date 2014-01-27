package com.evilco.plug.bot.launchwrapper;

import com.evilco.plug.bot.core.Bot;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.shell.Bootstrap;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class LaunchwrapperApplication {

	/**
	 * Stores the internal logging instance.
	 */
	protected static final Logger logger = Logger.getLogger("LaunchwrapperApplication");

	/**
	 * Stores the current bot instance.
	 */
	protected Bot bot = null;

	/**
	 * Stores the wrapper instance.
	 */
	protected static LaunchwrapperApplication instance = null;

	/**
	 * Stores the internal bot thread.
	 */
	protected Thread thread = null;

	/**
	 * Constructs a new launchwrapper.
	 * @param arguments
	 */
	private LaunchwrapperApplication (String[] arguments) {
		// initiate first instance
		this.initiateBot ();
		this.thread.start ();

		// start console
		try {
			Bootstrap.main (arguments);
		} catch (IOException ex) {
			this.shutdown ();
		}
	}

	/**
	 * Entry Point.
	 * @param arguments
	 */
	public static void main (String[] arguments) {
		// create new wrapper instance
		instance = new LaunchwrapperApplication (arguments);
	}

	/**
	 * Initiates a new bot instance.
	 */
	protected void initiateBot () {
		// check current instance
		if (this.bot != null || this.thread != null) return;

		// create context
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext ();

		// add core configuration
		context.register (Bot.class);
		context.refresh ();

		// create new bot instance
		this.bot = ((Bot) context.getBean ("Bot"));

		// create new thread
		this.thread = new BotThread (this.bot);

		// register the error handler
		this.thread.setUncaughtExceptionHandler ((new LaunchwrapperErrorHandler ()));
	}

	/**
	 * Reports a crash.
	 * @param e
	 */
	protected void reportCrash (Throwable e) {
		// log
		logger.severe ("================================== CRASH REPORT ========================================");
		logger.severe ("PlugBot has crashed. This might be a bug, a service outage at plug.dj or a configuration");
		logger.severe ("issue.");
		logger.severe ("");
		logger.severe ("The bot will now try to automatically restart and reconnect based on your current");
		logger.severe ("configuration. Please submit this crash report if the problem keeps coming up from now");
		logger.severe ("on.");
		logger.severe ("===================================== REPORT ===========================================");
		logger.severe ("Exception: " + e.getClass ().getName ());
		logger.severe ("Message: " + e.getMessage ());

		// log cause
		if (e.getCause () != null) {
			logger.severe ("Caused by: " + e.getCause ().getClass ().getName ());
			logger.severe ("Cause: " + e.getCause ().getMessage ());
		}

		// log stacktrace
		logger.severe ("");
		logger.severe (ExceptionUtils.getFullStackTrace (e));

		// end report
		logger.severe ("========================================================================================");
	}

	/**
	 * Shuts down the application.
	 */
	public void shutdown () {
		// give the API a chance to free resources
		this.bot.shutdown ();

		// reset instances
		this.thread = null;
		this.bot = null;

		// suggest a garbage collection
		System.gc ();
	}

	/**
	 * Handles problems with bots.
	 */
	public class LaunchwrapperErrorHandler implements Thread.UncaughtExceptionHandler {

		/**
		 * Protected constructor.
		 */
		private LaunchwrapperErrorHandler () { }

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void uncaughtException (Thread t, Throwable e) {
			// try to shutdown the bot
			shutdown ();

			// report
			reportCrash (e);

			// restart bot
			initiateBot ();
			thread.start ();
		}
	}
}
