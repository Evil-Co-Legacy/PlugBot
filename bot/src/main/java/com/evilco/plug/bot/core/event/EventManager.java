package com.evilco.plug.bot.core.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Component
public class EventManager implements ApplicationEventPublisherAware {

	/**
	 * Stores the application event publisher.
	 */
	protected ApplicationEventPublisher publisher = null;

	/**
	 * Fires an event.
	 * @param event
	 */
	public void fireEvent (ApplicationEvent event) {
		this.publisher.publishEvent (event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setApplicationEventPublisher (ApplicationEventPublisher applicationEventPublisher) {
		this.publisher = applicationEventPublisher;
	}
}
