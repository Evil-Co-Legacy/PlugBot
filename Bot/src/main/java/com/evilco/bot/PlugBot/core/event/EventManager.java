package com.evilco.bot.PlugBot.core.event;

import com.evilco.bot.PlugBot.Bot;
import com.evilco.bot.PlugBot.core.data.HistoryItem;
import com.evilco.bot.PlugBot.core.data.PlugUser;
import com.evilco.bot.PlugBot.core.data.PlugVotes;
import com.evilco.bot.PlugBot.core.event.chat.ChatEvent;
import com.evilco.bot.PlugBot.core.event.dj.DJAdvanceEvent;
import com.evilco.bot.PlugBot.core.event.dj.DJUpdateEvent;
import com.evilco.bot.PlugBot.core.event.dj.ModSkipEvent;
import com.evilco.bot.PlugBot.core.event.room.*;
import com.evilco.bot.PlugBot.core.event.user.*;
import com.evilco.bot.PlugBot.core.plugin.IPlugin;
import com.evilco.bot.PlugBot.core.plugin.annotation.EventHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.openqa.selenium.JavascriptExecutor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @package com.evilco.bot.PlugBot.core.event
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class EventManager {

	/**
	 * Stores the current driver instance.
	 */
	protected Bot bot = null;

	/**
	 * Stores the JSON deserializer.
	 */
	protected Gson gson = null;

	/**
	 * Stores a mapping between events and methods.
	 */
	protected Map<Method, Class<? extends IEvent>> methodMap = null;

	/**
	 * Stores a mapping between objects and methods.
	 */
	protected Map<Method, IListener> objectMap = null;

	/**
	 * Stores the mapping between objects and plugins.
	 */
	protected Map<IListener, IPlugin> pluginMap = null;

	/**
	 * Constructs a new EventManager.
	 * @param bot
	 */
	public EventManager (Bot bot) {
		this.bot = bot;

		// initialize local variables
		this.gson = new Gson ();

		this.methodMap = new HashMap<Method, Class<? extends IEvent>> ();
		this.objectMap = new HashMap<Method, IListener> ();
		this.pluginMap = new HashMap<IListener, IPlugin> ();
	}

	/**
	 * Fires an event.
	 * @param event
	 */
	public void FireEvent (IEvent event) {
		// iterate over methods
		for (Map.Entry<Method, Class<? extends IEvent>> listenEntry : this.methodMap.entrySet ()) {
			// check whether the event matches
			if (!listenEntry.getValue ().isInstance (event)) continue;

			// get object
			IListener parent = this.objectMap.get (listenEntry.getKey ());

			// call method
			try {
				listenEntry.getKey ().invoke (parent, event);
			} catch (IllegalAccessException ex) {
				this.bot.log.error ("Cannot execute plugin event listener on object " + parent.getClass ().getName () + "!", ex);
				this.bot.log.error ("This is an error in one of the bot plugins. Please notify the author of the plugin.");
			} catch (InvocationTargetException ex) {
				this.bot.log.error ("Cannot execute plugin event listener on object " + parent.getClass ().getName () + "!", ex);
				this.bot.log.error ("This is most likely an error in one of the bot plugins. Please notify the author of the plugin.");
			}
		}

		// log
		this.bot.log.trace ("Fired event {}.", event.getClass ().getName ());
	}

	/**
	 * Returns an instnace of the JavascriptExecutor.
	 * @return
	 */
	public JavascriptExecutor GetJavascriptExecutor () {
		return ((JavascriptExecutor) this.bot.GetDriver ());
	}

	/**
	 * Injects a handler script for a specific event.
	 * @param type
	 */
	public void Inject (EventType type) {
		// inject all event types
		if (type == EventType.ALL) {
			for (EventType e : EventType.values ()) {
				// skip ALL
				if (e == EventType.ALL) continue;

				// inject type
				this.Inject (e);
			}

			// stop execution here
			return;
		}

		// create callback
		this.GetJavascriptExecutor ().executeScript ("API.on (API." + type.toString () + ", function (data) { data = '" + type.toString () + ":' + JSON.stringify (data); window.PlugBotEventHandler (data);});");
	}

	/**
	 * Injects the event handler into the current webpage.
	 */
	public void InjectHandler () {
		// get executor
		JavascriptExecutor executor = this.GetJavascriptExecutor ();

		// inject global event storage
		executor.executeScript ("window.PlugBotEventQueue = [ ];");
		executor.executeScript ("window.PlugBotEventHandler = function (data) { PlugBotEventQueue[PlugBotEventQueue.length] = data }");
	}

	/**
	 * Polls all events out of the internal queue.
	 */
	public void PollEvents () {
		this.bot.log.entry ();

		// get executor
		JavascriptExecutor executor = this.GetJavascriptExecutor ();

		// poll events out of browser
		List<String> events = ((List<String>) executor.executeScript ("var currentQueue = window.PlugBotEventQueue; window.PlugBotEventQueue = [ ]; return currentQueue;"));

		// parse events
		for (String eventString : events) {
			// find first : to determine the type
			int seperatorPosition = eventString.indexOf (":");

			// check position
			if (seperatorPosition < 0) {
				this.bot.log.warn ("Ignoring event string (missing type information): {}", eventString);
				continue;
			}

			// read data
			String type = eventString.substring (0, (seperatorPosition));
			String data = eventString.substring ((seperatorPosition) + 1);

			// get proper event type
			EventType eventType = null;

			// get event type
			try {
				eventType = EventType.valueOf (type);
			} catch (IllegalArgumentException ignore) { }

			// check type
			if (eventType == null) {
				this.bot.log.warn ("Ignoring event string (unknown type): {}", eventString);
				continue;
			}

			// create variable for data
			IEvent event = null;

			try {
				// de-serialize data
				switch (eventType) {
					case CHAT:
						event = this.gson.fromJson (data, ChatEvent.class);
						break;
					case CHAT_COMMAND:
						this.bot.log.warn ("Received a chat command. Chat commands are currently not supported.");
						break;
					case CURATE_UPDATE:
						event = this.gson.fromJson (data, CurateUpdateEvent.class);
						break;
					case DJ_ADVANCE:
						event = this.gson.fromJson (data, DJAdvanceEvent.class);
						break;
					case DJ_UPDATE:
						PlugUser djUpdateUser = this.gson.fromJson (data, PlugUser.class);
						event = new DJUpdateEvent (djUpdateUser);

						// no dj?
						if (event == null) event = new BoothEmptyEvent ();
						break;
					case FAN_JOIN:
						PlugUser fanJoinUser = this.gson.fromJson (data, PlugUser.class);
						event = new FanJoinEvent (fanJoinUser);
						break;
					case FRIEND_JOIN:
						PlugUser friendJoinUser = this.gson.fromJson (data, PlugUser.class);
						event = new FriendJoinEvent (friendJoinUser);
						break;
					case HISTORY_UPDATE:
						List<HistoryItem> items = this.gson.fromJson (data, (new TypeToken<List<HistoryItem>> (){}).getType ());
						event = new HistoryUpdateEvent (items);
						break;
					case MOD_SKIP:
						String username = this.gson.fromJson (data, String.class);
						event = new ModSkipEvent (username);
						break;
					case ROOM_SCORE_UPDATE:
						PlugVotes votes = this.gson.fromJson (data, PlugVotes.class);
						event = new RoomScoreUpdateEvent (votes);
						break;
					case USER_FAN:
						PlugUser userFanUser = this.gson.fromJson (data, PlugUser.class);
						event = new UserFanEvent (userFanUser);
						break;
					case USER_JOIN:
						PlugUser userJoinUser = this.gson.fromJson (data, PlugUser.class);
						event = new UserJoinEvent (userJoinUser);
						break;
					case USER_LEAVE:
						PlugUser userLeaveUser = this.gson.fromJson (data, PlugUser.class);
						event = new UserLeaveEvent (userLeaveUser);
						break;
					case USER_SKIP:
						PlugUser userSkipUser = this.gson.fromJson (data, PlugUser.class);
						event = new UserSkipEvent (userSkipUser);
						break;
					case VOTE_SKIP:
						event = new VoteSkipEvent ();
						break;
					case VOTE_UPDATE:
						event = this.gson.fromJson (data, VoteUpdateEvent.class);
						break;
					case WAIT_LIST_UPDATE:
						List<PlugUser> users = this.gson.fromJson (data, (new TypeToken<List<PlugUser>> () {}).getType ());
						event = new WaitListUpdateEvent (users);
						break;
					default:
						this.bot.log.warn ("Received un-implemented event: " + eventType.toString () + "");
						this.bot.log.warn ("This is a bug. Please report this problem to the developers.");
						break;
				}

				// fire event
				if (event != null) this.FireEvent (event);
			} catch (IllegalStateException ex) {
				this.bot.log.info ("Could not process message of type {}: {}", eventType.toString (), ex.getMessage ());
			}
		}

		this.bot.log.exit ();
	}

	/**
	 * Registers a listener and all of it's annotated methods.
	 * @param listener
	 */
	public void RegisterListener (IListener listener, IPlugin plugin) {
		// generate method list
		for (Method method : listener.getClass ().getMethods ()) {
			// check whether EventHandler annotation is present
			if (!method.isAnnotationPresent (EventHandler.class)) continue;

			// get method parameters to check event type
			Class<?>[] parameterTypes = method.getParameterTypes ();

			// check argument length
			if (parameterTypes.length > 1 || parameterTypes.length == 0) {
				this.bot.log.error ("The event handler {} in listener {} does not accept exactly one parameter. Skipping handler.", method.getName (), listener.getClass ().getName ());
				continue;
			}

			// get event type
			Class<? extends IEvent> eventType;

			try {
				// get event type
				eventType = parameterTypes[0].asSubclass (IEvent.class);
			} catch (ClassCastException ex) {
				this.bot.log.error ("The event handler {} in listener {} does not accept events. Skipping handler.", method.getName (), listener.getClass ().getName ());
				continue;
			}

			// append to lists
			this.pluginMap.put (listener, plugin);
			this.objectMap.put (method, listener);
			this.methodMap.put (method, eventType);
		}
	}

	/**
	 * Unregisters all handlers of a listener.
	 * @param listener
	 */
	public void UnregisterListener (IListener listener) {
		// loop through object list
		for (Map.Entry<Method, IListener> handler : this.objectMap.entrySet ()) {
			// skip non matching listeners
			if (!handler.getValue ().equals (listener)) continue;

			// unregister method
			this.methodMap.remove (handler.getKey ());
		}

		// unregister from plugin map
		this.pluginMap.remove (listener);
	}

	/**
	 * Unregisters all listeners of a single plugin.
	 * @param plugin
	 */
	public void UnregisterListeners (IPlugin plugin) {
		// walk over listener map to find registered listeners
		for (Map.Entry<IListener, IPlugin> pluginEntry : this.pluginMap.entrySet ()) {
			// skip non matching items
			if (!pluginEntry.getValue ().equals (plugin)) continue;

			// unregister listener
			this.UnregisterListener (pluginEntry.getKey ());
		}
	}
}
