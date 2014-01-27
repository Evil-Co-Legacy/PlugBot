package com.evilco.plug.bot.core.configuration;

import com.evilco.plug.bot.core.Bot;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Component
@XmlRootElement (name = "configuration", namespace = BotConfiguration.NAMESPACE)
public class BotConfiguration {

	/**
	 * Defines the configuration file.
	 */
	public static final String FILE = "PlugBot.xml";

	/**
	 * Defines the configuration namespace.
	 */
	public static final String NAMESPACE = "http://www.evil-co.org/2014/PlugBot/configuration";

	/**
	 * Stores the internal logger.
	 */
	protected static final Logger logger = Logger.getLogger ("BotConfiguration");

	/**
	 * Defines the account configuration.
	 */
	@XmlElement (name = "account", namespace = NAMESPACE)
	public AccountConfiguration account = new AccountConfiguration ();

	/**
	 * Defines the driver configuration.
	 */
	public DriverConfiguration driver = new DriverConfiguration ();

	/**
	 * Protected Constructor.
	 */
	private BotConfiguration () { }

	/**
	 * Returns a reference to the configuration file.
	 * @return
	 */
	public static File getConfigurationFile () {
		return (new File (Bot.getApplicationDirectory (), FILE));
	}

	/**
	 * Loads a configuration file.
	 * @return
	 * @throws ConfigurationLoadException
	 */
	public static BotConfiguration load () throws ConfigurationLoadException {
		try {
			// create context
			JAXBContext context = JAXBContext.newInstance (BotConfiguration.class);

			// create unmarshaller
			Unmarshaller unmarshaller = context.createUnmarshaller ();

			// create a new object
			return ((BotConfiguration) unmarshaller.unmarshal (getConfigurationFile ()));
		} catch (JAXBException ex) {
			throw new ConfigurationLoadException (ex);
		}
	}

	/**
	 * Creates a new instance of the bot configuration.
	 * @return
	 */
	public static BotConfiguration newInstance () {
		// try to load a configuration file
		try {
			return load ();
		} catch (ConfigurationLoadException ex) {
			logger.warning ("Could not load the configuration file. Creating an empty configuration.");
		}

		// create an empty configuration file
		BotConfiguration configuration = new BotConfiguration ();

		// try to save configuration
		try {
			configuration.save ();
		} catch (ConfigurationSaveException ex) {
			logger.log (Level.SEVERE, "Could not store the configuration file.", ex);
		}

		// return new configuration
		return configuration;
	}

	/**
	 * Saves the configuration back to disk.
	 * @throws ConfigurationSaveException
	 */
	public void save () throws ConfigurationSaveException {
		try {
			// create context
			JAXBContext context = JAXBContext.newInstance (BotConfiguration.class);

			// create marshaller
			Marshaller marshaller = context.createMarshaller ();

			// set properties
			marshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// marshal
			marshaller.marshal (this, getConfigurationFile ());
		} catch (JAXBException ex) {
			throw new ConfigurationSaveException (ex);
		}
	}

	/**
	 * Stores account related information.
	 */
	public static class AccountConfiguration {

		/**
		 * Defines the password.
		 */
		public String password = "12345678";

		/**
		 * Defines the account type.
		 */
		public String type = "Google";

		/**
		 * Defines the username.
		 */
		public String username = "user@example.org";
	}

	/**
	 * Stores driver related information.
	 */
	public static class DriverConfiguration {

		/**
		 * Defines the driver name.
		 */
		public String name = "Chrome";

		/**
		 * Defines the driver URL (applies to the remote driver).
		 */
		public String url = "http://127.0.0.1:14444/wd/hub";
	}
}
