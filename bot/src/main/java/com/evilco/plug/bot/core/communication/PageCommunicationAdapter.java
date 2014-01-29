package com.evilco.plug.bot.core.communication;

import com.evilco.plug.bot.core.communication.data.*;
import com.evilco.plug.bot.core.event.EventManager;
import com.evilco.plug.bot.core.event.communication.dj.*;
import com.evilco.plug.bot.core.event.communication.room.*;
import com.evilco.plug.bot.core.event.communication.room.message.*;
import com.evilco.plug.bot.core.event.communication.score.CurateUpdateEvent;
import com.evilco.plug.bot.core.event.communication.score.VoteUpdateEvent;
import com.evilco.plug.bot.core.event.communication.user.UserFanEvent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Component
@Configuration
@EnableScheduling
@DependsOn ("driver")
public class PageCommunicationAdapter {

	/**
	 * Defines the minimal amount of interactions a user needs to get blocked automatically.
	 */
	public static final int BLACKLIST_INTERACTION_AMOUNT = 5;

	/**
	 * Defines the time which needs to pass for an interaction to be removed.
	 */
	public static final int BLACKLIST_TIMEOUT = 20000;

	/**
	 * Defines the text a dialog window has to have to cause an application crash.
	 */
	public static final String CONNECTION_ERROR_TEXT = "Connection Error";

	/**
	 * Stores the internal logger.
	 */
	protected static final Logger logger = Logger.getLogger ("PageCommunicationAdapter");

	/**
	 * Stores the command blacklist.
	 */
	protected Map<String, Long> blacklist = new HashMap<String, Long> ();

	/**
	 * Stores the command interaction blacklist.
	 */
	protected Map<String, Integer> blacklistInteractionMap = new HashMap<String, Integer> ();

	/**
	 * Caches the web driver.
	 */
	@Autowired
	protected WebDriver driver;

	/**
	 * Indicates whether the adapter has been enabled.
	 */
	protected boolean enabled = false;

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
	 * Stores the bot user.
	 */
	protected User user = null;

	/**
	 * Caches the current user list.
	 */
	protected List<User> userList = new ArrayList<> ();

	/**
	 * Returns the Javascript executor.
	 * @return
	 */
	public JavascriptExecutor getExecutor () {
		if (this.executor == null) this.executor = ((JavascriptExecutor) this.driver);
		return this.executor;
	}

	/**
	 * Returns the current bot user.
	 * @return
	 */
	public User getUser () {
		return this.user;
	}

	/**
	 * Returns the user list.
	 * @return
	 */
	@Bean (name = "userList")
	public List<User> getUserList () {
		return this.userList;
	}

	/**
	 * Injects all event handlers.
	 */
	public void inject () {
		// check state
		if (this.enabled) throw new RuntimeException ("The API has already been injected.");

		// inject handler script
		this.executor.executeScript ("window.PlugBotQueue = [];");
		this.executor.executeScript ("window.PlugBotHandler = function (data) { window.PlugBotQueue[window.PlugBotQueue.length] = data; };");

		// inject all API calls
		for (EventType type : EventType.values ()) {
			this.executor.executeScript ("API.on (API." + type.toString () + ", function (data) { window.PlugBotHandler ('" + type.toString () + ":' + JSON.stringify (data)); });");
		}

		// disable sound output
		this.executor.executeScript ("API.setVolume (0);");

		// read user
		this.pollUser ();

		// enable
		this.enabled = true;
	}

	/**
	 * Checks whether the API is ready.
	 * @return
	 */
	public boolean isApiReady () {
		return ((Boolean) this.getExecutor ().executeScript ("return (window.API != undefined && window.API != null && window.API.enabled);"));
	}

	/**
	 * Clears the blacklist.
	 */
	@Scheduled (fixedRate = 5000)
	public void clearBlacklist () {
		for (Map.Entry<String, Long> blacklistTimeout : this.blacklist.entrySet ()) {
			if (blacklistTimeout.getValue () <= System.currentTimeMillis ()) {
				if (this.blacklistInteractionMap.get (blacklistTimeout.getKey ()) > 1) {
					// get value
					int interactions = this.blacklistInteractionMap.get (blacklistTimeout.getKey ());

					// update value
					this.blacklistInteractionMap.put (blacklistTimeout.getKey (), (interactions - 1));
					this.blacklist.put (blacklistTimeout.getKey (), (System.currentTimeMillis () + BLACKLIST_TIMEOUT));
				} else {
					this.blacklist.remove (blacklistTimeout.getKey ());
					this.blacklistInteractionMap.remove (blacklistTimeout.getKey ());
				}
			}
		}
	}

	/**
	 * Polls updates to our own user object.
	 */
	@Scheduled (fixedRate = 20000)
	public void pollUser () {
		// disabled?
		if (!this.enabled) return;

		// get json
		String userData = ((String) this.getExecutor ().executeScript ("return JSON.stringify (API.getUser ());"));

		// de-serialize data
		this.user = (new Gson ()).fromJson (userData, User.class);
	}

	/**
	 * Checks whether the connection is still available.
	 */
	@Scheduled (fixedRate = 120000)
	public void pollConnectionErrors () {
		// wait
		if (!this.enabled) return;

		// log
		logger.info ("Checking whether connection is still alive ...");

		// search for the crash dialog
		WebElement element = null;

		try {
			this.driver.findElement (By.cssSelector ("#dialog-alert > .dialog-frame > .title"));
		} catch (NoSuchElementException ignore) { }

		// check state
		if (element != null && element.getText () == CONNECTION_ERROR_TEXT) throw new RuntimeException ("The connection to plug.dj has been terminated. The state of the application is unclear.");

		// log
		logger.info ("The connection is still alive.");
	}

	/**
	 * Polls all cached events.
	 */
	@Scheduled (fixedRate = 1000)
	public void pollEvents () {
		// wait
		if (!this.enabled) return;

		// get event queue
		List<String> eventQueue = ((List<String>) this.getExecutor ().executeScript ("var currentQueue = window.PlugBotQueue; window.PlugBotQueue = [ ]; return currentQueue;"));

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
			try {
				// parse data
				JSONObject object = null;

				// parse for most event types (some types use their own parsing mechanism and are ignored here).
				switch (type) {
					case CHAT:
					case DJ_UPDATE:
					case FAN_JOIN:
					case FRIEND_JOIN:
					case HISTORY_UPDATE:
					case MOD_SKIP:
					case ROOM_SCORE_UPDATE:
					case USER_FAN:
					case USER_JOIN:
					case USER_LEAVE:
					case USER_SKIP:
					case VOTE_SKIP:
					case WAIT_LIST_UPDATE: break;

					default:
						gson.fromJson (eventData, JSONObject.class);
				}

				// define common objects (work around context bug)
				User user = null;
				Media media = null;

				switch (type) {
					case CHAT:
						// decode
						Message message = gson.fromJson (eventData, Message.class);

						// verify type
						switch (message.type) {
							case EMOTE:
								event = new EmoteMessageEvent (this, message);
								break;
							case MODERATION:
								event = new ModerationMessageEvent (this, message);
								break;
							case SYSTEM:
								event = new SystemMessageEvent (this, message);
								break;
							default:
								// verify source
								if (message.fromID == this.user.id) {
									event = new BotMessageEvent (this, message);
									break;
								}

								// split up message
								String[] messageEx = message.message.split (" ");

								// check contents
								if (messageEx.length >= 1 && messageEx[0].equalsIgnoreCase ("@" + this.user.username)) {
									if (this.blacklistInteractionMap.containsKey (message.fromID) && this.blacklistInteractionMap.get (message.fromID) >= BLACKLIST_INTERACTION_AMOUNT) {
										logger.info ("Ignored message from user " + message.from + " (" + message.fromID + "): User has been blacklisted for spamming.");
										break;
									}

									// TODO: Parse & execute command

									// update blacklist interaction
									this.blacklist.put (message.fromID, (System.currentTimeMillis () + BLACKLIST_TIMEOUT));

									if (this.blacklistInteractionMap.containsKey (message.fromID))
										this.blacklistInteractionMap.put (message.fromID, 1);
									else
										this.blacklistInteractionMap.put (message.fromID, (this.blacklistInteractionMap.get (message.fromID) + 1));

									// stop execution
									break;
								}

								// fire event
								event = new UserMessageEvent (this, message);
								break;
						}
						break;
					case CHAT_COMMAND:
						logger.warning ("Chat commands are not supported in PlugBot.");
						break;
					case CURATE_UPDATE:
						// extract user property
						user = gson.fromJson (object.getJSONObject ("user").toString (), User.class);

						// construct event
						event = new CurateUpdateEvent (this, user);
						break;
					case DJ_ADVANCE:
						// check whether booth is empty
						if (object == null) {
							event = new DjBoothEmptyEvent (this);
							break;
						}

						// extract main data
						user = gson.fromJson (object.getJSONObject ("dj").toString (), User.class);
						media = gson.fromJson (object.getJSONObject ("media").toString (), Media.class);

						// construct event
						event = new DjAdvanceEvent (this, user, media);
						break;
					case DJ_UPDATE:
						// verify object
						if (object == null) break;

						// extract user object
						user = gson.fromJson (eventData, User.class);

						// construct event
						event = new DjUpdateEvent (this, user);
						break;
					case FAN_JOIN:
						// extract user object
						user = gson.fromJson (eventData, User.class);

						// construct event
						event = new FanJoinEvent (this, user);
						break;
					case FRIEND_JOIN:
						// extract user object
						user = gson.fromJson (eventData, User.class);

						// construct event
						event = new FriendJoinEvent (this, user);
						break;
					case HISTORY_UPDATE:
						// get type token
						TypeToken<List<HistoryItem>> token = new TypeToken<List<HistoryItem>> () { };

						// deserialize data
						List<HistoryItem> history = gson.fromJson (eventData, token.getType ());

						// construct event
						event = new HistoryUpdateEvent (this, history);
						break;
					case MOD_SKIP:
						// deserialize data
						String username = gson.fromJson (eventData, String.class);

						// construct event
						event = new ModeratorSkipEvent (this, username);
						break;
					case ROOM_SCORE_UPDATE:
						// deserialize data
						RoomScore score = gson.fromJson (eventData, RoomScore.class);

						// construct event
						event = new RoomScoreUpdateEvent (this, score);
						break;
					case USER_FAN:
						// deserialize data
						user = gson.fromJson (eventData, User.class);

						// construct event
						event = new UserFanEvent (this, user);
						break;
					case USER_JOIN:
						// deserialize data
						user = gson.fromJson (eventData, User.class);

						// construct event
						event = new UserJoinEvent (this, user);
						break;
					case USER_LEAVE:
						// deserialize data
						user = gson.fromJson (eventData, User.class);

						// construct event
						event = new UserLeaveEvent (this, user);
						break;
					case USER_SKIP:
						// deserialize data
						user = gson.fromJson (eventData, User.class);

						// construct event
						event = new UserSkipEvent (this, user);
						break;
					case VOTE_SKIP:
						// construct event
						event = new VoteSkipEvent (this);
						break;
					case VOTE_UPDATE:
						// deserialize data
						int vote = object.getInt ("vote");
						user = gson.fromJson (object.getJSONObject ("user").toString (), User.class);

						// construct event
						event = new VoteUpdateEvent (this, user, vote);
						break;
					case WAIT_LIST_UPDATE:
						// construct token
						TypeToken<List<User>> listToken = new TypeToken<List<User>> () { };

						// deserialize data
						List<User> waitList = gson.fromJson (eventData, listToken.getType ());

						// construct event
						event = new WaitListUpdateEvent (this, waitList);
						break;
					default:
						logger.severe ("Received an unhandled event of type " + type.toString () + ". This is a bug!");
						break;
				}
			} catch (JSONException ex) {
				logger.log (Level.WARNING, "Skipping event of type " + type.toString () + ": The json string could not be decoded.", ex);
				continue;
			} catch (Exception ex) {
				logger.log (Level.SEVERE, "Skipping event of type " + type.toString () + ": An exception occurred during parsing and dispatching the event.", ex);
				continue;
			}

			// verify event
			if (event == null) {
				logger.warning ("Skipping event of type " + type.toString () + ": No event instance has been constructed.");
				continue;
			}

			// debug code
			logger.info ("Dispatching event of type " + type.toString () + ".");

			// dispatch
			this.eventManager.fireEvent (event);
		}
	}

	/**
	 * Polls an internal user list.
	 */
	@Scheduled (fixedRate = 5000)
	public void pollUsers () {
		// wait
		if (!this.enabled) return;

		// create token
		TypeToken<List<User>> token = new TypeToken<List<User>> () { };

		// get data
		String userListData = ((String) this.getExecutor ().executeScript ("return JSON.stringify (API.getUsers());"));

		try {
			// decode data
			List<User> userList = (new Gson ()).fromJson (userListData, token.getType ());

			// clear list
			this.userList.clear ();

			// re-populate
			this.userList.addAll (userList);
		} catch (Exception ex) {
			logger.severe ("Could not poll internal user list cache. This might be a bug.");
		}
	}
}
