package com.evilco.bot.PlugBot.core.plugin;

import com.evilco.bot.PlugBot.Bot;
import com.evilco.bot.PlugBot.core.plugin.annotation.Plugin;
import com.evilco.bot.PlugBot.core.plugin.exception.PluginConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * @package com.evilco.bot.PlugBot.core.plugin
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public abstract class AbstractPlugin implements IPlugin {

	/**
	 * Stores the current bot instance.
	 */
	private Bot bot = null;

	/**
	 * Stores the suggest configuration file.
	 */
	private File configurationFile = null;

	/**
	 * Stores the log instance.
	 */
	private Logger log = null;

	/**
	 * Stores the plugin file.
	 */
	private File file = null;

	/**
	 * Stores the plugin metadata.
	 */
	private Plugin metadata = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Bot GetBot () {
		return this.bot;
	}

	/**
	 * {@inheritDoc}
	 */
	public File GetConfigurationFile () {
		return this.configurationFile;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Logger GetLog () {
		return this.log;
	}

	/**
	 * {@inheritDoc}
	 */
	public Plugin GetMetadata () {
		return this.metadata;
	}

	/**
	 * {@inheritDoc}
	 */
	public File GetFile () {
		return this.file;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void Initialize (Bot bot, Plugin metadata, File pluginFile, File configurationFile) {
		this.bot = bot;
		this.metadata = metadata;
		this.file = pluginFile;
		this.configurationFile = configurationFile;

		// get logger
		this.log = LogManager.getLogger (metadata.name ());
	}

	/**
	 * Loads a configuration file.
	 * @param type
	 * @param <T>
	 * @return
	 * @throws PluginConfigurationException
	 */
	public <T> T LoadConfiguration (Class<T> type) throws PluginConfigurationException {
		try {
			// create context
			JAXBContext context = JAXBContext.newInstance (type);

			// create unmarshaller
			Unmarshaller unmarshaller = context.createUnmarshaller ();

			// unmarshal object from file
			return ((T) unmarshaller.unmarshal (this.configurationFile));
		} catch (JAXBException ex) {
			throw new PluginConfigurationException (ex);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void OnEnable () { }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void OnDisable () { }

	/**
	 * Saves a configuration file.
	 * @param configuration
	 * @throws PluginConfigurationException
	 */
	public void SaveConfiguration (Object configuration) throws PluginConfigurationException {
		try {
			// create context
			JAXBContext context = JAXBContext.newInstance (configuration.getClass ());

			// create marshaller
			Marshaller marshaller = context.createMarshaller ();

			// set properties
			marshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");

			// marshal object to file
			marshaller.marshal (configuration, this.configurationFile);
		} catch (JAXBException ex) {
			throw new PluginConfigurationException (ex);
		}
	}
}
