package com.evilco.plug.bot.core.event;

import com.evilco.plug.bot.core.plugin.PluginManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Component
public class EventManager implements ApplicationEventPublisherAware {

	/**
	 * Stores the internal logging instance.
	 */
	protected static final Logger logger = Logger.getLogger ("EventManager");

	/**
	 * Caches the plugin manager instance.
	 */
	@Autowired
	protected PluginManager pluginManager;

	/**
	 * Stores the application event publisher.
	 */
	protected ApplicationEventPublisher publisher = null;

	/**
	 * Fires an event.
	 * @param event
	 */
	public void fireEvent (ApplicationEvent event) {
		logger.finest ("Dispatching event of type " + event.getClass ().getCanonicalName () + ".");

		// fire
		this.publisher.publishEvent (event);

		// publish event to plugin system
		this.pluginManager.onApplicationEvent (event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setApplicationEventPublisher (ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}
}
