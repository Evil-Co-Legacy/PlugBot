package com.evilco.plug.bot.launchwrapper;

import com.evilco.plug.bot.core.Bot;
import com.evilco.plug.bot.core.IWrapperInterface;
import com.evilco.plug.bot.core.configuration.CoreBeanConfiguration;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.shell.Bootstrap;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Component
@ComponentScan (basePackages = {"com.evilco.plug.bot.launchwrapper"})
public class LaunchwrapperApplication implements ApplicationContextAware {

	/**
	 * Stores the internal logging instance.
	 */
	protected static final Logger logger = Logger.getLogger("LaunchwrapperApplication");

	/**
	 * Stores the current bot instance.
	 */
	protected Bot bot = null;

	/**
	 * Stores the current bot context.
	 */
	protected AnnotationConfigApplicationContext botContext = null;

	/**
	 * Stores the application context.
	 */
	protected AnnotationConfigApplicationContext context = null;

	/**
	 * Stores the wrapper instance.
	 */
	protected static LaunchwrapperApplication instance = null;

	/**
	 * Stores the internal bot thread.
	 */
	protected Thread thread = null;

	/**
	 * Entry Point.
	 * @param arguments
	 */
	public static void main (String[] arguments) {
		// create root context
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext ();

		// register classes
		context.register (LaunchwrapperApplication.class);
		context.refresh ();

		// start up by requesting the bean
		context.getBean (LaunchwrapperApplication.class).initiateBot ();

		try {
			Bootstrap.main (arguments);
		} catch (IOException ex) { }
	}

	/**
	 * Initiates a new bot instance.
	 */
	protected void initiateBot () {
		// check current instance
		if (this.bot != null || this.thread != null) return;

		// create context
		this.botContext = new AnnotationConfigApplicationContext ();

		// add core configuration
		this.botContext.setParent (this.context);
		this.botContext.register (CoreBeanConfiguration.class);

		// refresh context
		this.botContext.refresh ();

		// create new bot instance
		this.bot = ((Bot) this.botContext.getBean ("Bot"));

		// create new thread
		this.thread = new BotThread (this.bot);

		// register the error handler
		this.thread.setUncaughtExceptionHandler ((new LaunchwrapperErrorHandler ()));

		// start thread
		this.thread.start ();
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
	 * {@inheritDoc}
	 */
	@Override
	public void setApplicationContext (ApplicationContext applicationContext) throws BeansException {
		this.context = ((AnnotationConfigApplicationContext) applicationContext);
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
