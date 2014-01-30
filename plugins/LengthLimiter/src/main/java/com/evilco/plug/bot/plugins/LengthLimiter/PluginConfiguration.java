package com.evilco.plug.bot.plugins.LengthLimiter;

import com.evilco.plug.bot.core.plugin.configuration.PluginConfigurationException;
import com.evilco.plug.bot.core.plugin.configuration.PluginConfigurationLoadException;
import com.evilco.plug.bot.core.plugin.configuration.PluginConfigurationSaveException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.File;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@XmlRootElement (name = "configuration", namespace = PluginConfiguration.NAMESPACE)
@XmlType (propOrder = {"warningLength", "skipLength"})
public class PluginConfiguration {

	/**
	 * Defines the XML namespace.
	 */
	public static final String NAMESPACE = "http://www.evil-co.org/2014/PlugBot/configuration";

	/**
	 * Defines the length as of the bot will skip tracks (in seconds).
	 */
	@XmlElement (name = "skipLength", namespace = NAMESPACE)
	public int skipLength = 540;

	/**
	 * Defines the length as of the bot will issue warnings (in seconds).
	 */
	@XmlElement (name = "warningLength", namespace = NAMESPACE)
	public int warningLength = 480;

	/**
	 * Protected constructor.
	 */
	private PluginConfiguration () { }

	/**
	 * Loads a configuration file.
	 * @param configurationFile
	 * @return
	 * @throws PluginConfigurationLoadException
	 */
	public static PluginConfiguration load (File configurationFile) throws PluginConfigurationLoadException {
		try {
			JAXBContext context = JAXBContext.newInstance (PluginConfiguration.class);

			// create unmarshaller
			Unmarshaller unmarshaller = context.createUnmarshaller ();

			// un-marshal from configuration file
			return ((PluginConfiguration) unmarshaller.unmarshal (configurationFile));
		} catch (JAXBException ex) {
			throw new PluginConfigurationLoadException (ex);
		}
	}

	/**
	 * Constructs a new plugin configuration instance.
	 * @param configurationFile
	 * @return
	 * @throws PluginConfigurationException
	 */
	public static PluginConfiguration newInstance (File configurationFile) throws PluginConfigurationException {
		try {
			return load (configurationFile);
		} catch (PluginConfigurationLoadException ex) {
			// create new instance
			PluginConfiguration configuration = new PluginConfiguration ();

			// save empty configuration
			configuration.save (configurationFile);

			// ok!
			return configuration;
		}
	}

	/**
	 * Saves the configuration back to a file.
	 * @throws PluginConfigurationSaveException
	 */
	public void save (File configurationFile) throws PluginConfigurationSaveException {
		try {
			JAXBContext context = JAXBContext.newInstance (PluginConfiguration.class);

			// create marshaller
			Marshaller marshaller = context.createMarshaller ();

			// set properties
			marshaller.setProperty (Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty (Marshaller.JAXB_FORMATTED_OUTPUT, true);

			// marshal into file
			marshaller.marshal (this, configurationFile);
		} catch (JAXBException ex) {
			throw new PluginConfigurationSaveException (ex);
		}
	}
}
