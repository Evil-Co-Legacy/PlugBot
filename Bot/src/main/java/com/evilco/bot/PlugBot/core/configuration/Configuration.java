package com.evilco.bot.PlugBot.core.configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.File;

/**
 * @package com.evilco.bot.PlugBot.core
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@XmlRootElement (name = "configuration")
public class Configuration {

	/**
	 * Stores account information.
	 */
	@XmlElement (name = "account")
	public ConfigurationAccount account;

	/**
	 * Stores database configuration properties.
	 */
	@XmlElement (name = "database")
	public ConfigurationDatabase database;

	/**
	 * Stores system configuration properties.
	 */
	@XmlElement (name = "system")
	public ConfigurationSystem system;

	/**
	 * Creates a new instance of Configuration.
	 */
	protected Configuration () {
		this.account = new ConfigurationAccount ();
		this.database = new ConfigurationDatabase ();
		this.system = new ConfigurationSystem ();
	}

	/**
	 * Saves a configuration file back to disk.
	 * @param fileName
	 */
	public void Save (String fileName) throws ConfigurationException {
		// get file instance
		File file = new File (fileName);

		// store
		this.Save (file);
	}

	/**
	 * Saves a configuration file back to disk.
	 * @param file
	 * @throws ConfigurationException
	 */
	public void Save (File file) throws ConfigurationException {
		try {
			// create context
			JAXBContext context = JAXBContext.newInstance (Configuration.class);

			// create marshaller
			Marshaller marshaller = context.createMarshaller ();

			// set marshaller properties
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			marshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");

			// marshall instance into xml
			marshaller.marshal (this, file);
		} catch (JAXBException ex) {
			throw new ConfigurationException ("Cannot create configuration file.", ex);
		}
	}

	/**
	 * Reads a configuration file (or creates an empty one).
	 * @param fileName
	 * @return
	 * @throws ConfigurationException
	 */
	public static Configuration GetInstance (String fileName) throws ConfigurationException {
		// get file
		File file = new File (fileName);

		// read configuration
		return GetInstance (file);
	}

	/**
	 * Reads a configuration file (or creates an empty one).
	 * @param file
	 * @return
	 * @throws ConfigurationException
	 */
	public static Configuration GetInstance (File file) throws ConfigurationException {
		// check whether our file exists
		if (!file.exists ()) {
			try {
				// create default configuration
				Configuration configuration = new Configuration ();

				// save back to file
				configuration.Save (file);

				// return null to signal the main bot to restart
				return null;
			} catch (ConfigurationException ex) {
				throw new ConfigurationException ("Cannot create default configuration file.", ex);
			}
		}

		// check whether configuration is readable
		if (!file.canRead ()) throw new ConfigurationException ("Cannot read configuration file \"" + file.toPath ().toAbsolutePath () + "\": Permission Denied");

		try {
			// create context
			JAXBContext context = JAXBContext.newInstance (Configuration.class);

			// create unmarshaller
			Unmarshaller unmarshaller = context.createUnmarshaller ();

			// unmarshal
			Configuration configuration = ((Configuration) unmarshaller.unmarshal (file));

			// Cave Johnson: We're done here
			return configuration;
		} catch (JAXBException ex) {
			throw new ConfigurationException ("Cannot load existing configuration file.", ex);
		}
	}

	/**
	 * Storage class for account details.
	 */
	@XmlType (name = "account")
	public static class ConfigurationAccount {

		/**
		 * Defines which type of account to use.
		 */
		@XmlElement (name = "type")
		public String type = "google";

		/**
		 * Defines which username to input.
		 */
		@XmlElement (name = "username")
		public String username = "example";

		/**
		 * Defines the passwords to use.
		 */
		@XmlElement (name = "password")
		public String password = "1234";

		/**
		 * Constructs a new ConfigurationAccount.
		 */
		protected ConfigurationAccount () { }
	}

	/**
	 * Storage class for database details.
	 */
	@XmlType (name = "database")
	public static class ConfigurationDatabase {

		/**
		 * Enables or disable the database API.
		 */
		@XmlElement (name = "enableDatabase")
		public boolean enableDatabase = false;

		/**
		 * Defines the JDBC url to use.
		 */
		@XmlElement (name = "url")
		public String url = "jdbc:mysql://localhost/plugbot";

		/**
		 * Defines the database username.
		 */
		@XmlElement (name = "username")
		public String username = "plugbot";

		/**
		 * Defines the database password.
		 */
		@XmlElement (name = "password")
		public String password = "potato";
	}

	/**
	 * Storage class for system details.
	 */
	@XmlType (name = "system")
	public static class ConfigurationSystem {

		/**
		 * Defines the channel to join.
		 */
		@XmlElement (name = "channel")
		public String channel = "evil-co";

		/**
		 * Defines the browser driver to use.
		 */
		@XmlElement (name = "driver")
		public String driver = "Firefox";
	}
}
