package com.evilco.plug.bot.core.communication;

import com.evilco.plug.bot.core.event.EventManager;
import com.google.gson.Gson;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Component
@EnableScheduling
public class PageCommunicationAdapter {

	/**
	 * Caches the web driver.
	 */
	@Autowired
	protected WebDriver driver;

	/**
	 * Caches the event manager.
	 */
	@Autowired
	protected EventManager eventManager;

	/**
	 * Caches the JavaScript executor.
	 */
	protected JavascriptExecutor executor = null;

	/**
	 * Stores the internal logger.
	 */
	protected static final Logger logger = Logger.getLogger ("PageCommunicationAdapter");

	/**
	 * Injects all event handlers.
	 */
	public void inject () {
		// get javascript executor
		this.executor = ((JavascriptExecutor) this.driver);

		// inject handler script
		this.executor.executeScript ("window.PlugBotQueue = [];");
		this.executor.executeScript ("window.PlugBotHandler = function (data) { window.PlugBotQueue[window.PlugBotQueue.length] = data; };");

		// inject all API calls
		for (EventType type : EventType.values ()) {
			this.executor.executeScript ("API.on (API." + type.toString () + ", function (data) { window.PlugBotHandler ('" + type.toString () + ":' + JSON.stringify (data)); });");
		}
	}

	/**
	 * Polls all cached events.
	 */
	@Scheduled (fixedRate = 1000)
	public void pollEvents () {
		// get event queue
		List<String> eventQueue = ((List<String>) this.executor.executeScript ("var currentQueue = window.PlugBotQueue; window.PlugBotQueue = [ ]; return currentQueue;"));

		// parse event queue
		for (String eventString : eventQueue) {
			// find event detail
			int separatorPosition = eventString.indexOf (":");

			// verify position
			if (separatorPosition < 0) {
				logger.warning ("Ignoring event string due to malformed event string: " + eventString);
				continue;
			}

			// get type
			String typeString = eventString.substring (0, separatorPosition);
			String eventData = eventString.substring ((separatorPosition + 1));

			// find the event type
			EventType type = EventType.valueOf (typeString);

			// create an empty application event
			ApplicationEvent event = null;

			// create gson instance
			Gson gson = new Gson ();

			// decode all events
			switch (type) {
				case CHAT:
					break;
				case CHAT_COMMAND:
					logger.warning ("Chat commands are not supported in PlugBot.");
					break;
				case CURATE_UPDATE:
					break;
				case DJ_ADVANCE:
					break;
				case DJ_UPDATE:
					break;
				case FAN_JOIN:
					break;
				case FRIEND_JOIN:
					break;
				case HISTORY_UPDATE:
					break;
				case MOD_SKIP:
					break;
				case ROOM_SCORE_UPDATE:
					break;
				case USER_FAN:
					break;
				case USER_JOIN:
					break;
				case USER_LEAVE:
					break;
				case USER_SKIP:
					break;
				case VOTE_SKIP:
					break;
				case VOTE_UPDATE:
					break;
				case WAIT_LIST_UPDATE:
					break;
			}

			// verify event
			if (event == null) {
				logger.warning ("Skipped event of type " + type.toString () + ": No event instance has been constructed.");
				continue;
			}

			// dispatch
			this.eventManager.fireEvent (event);
		}
	}
}
