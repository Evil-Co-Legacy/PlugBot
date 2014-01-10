package com.evilco.bot.PlugBot.bridge;

import com.evilco.bot.PlugBot.core.constant.BanDuration;
import com.evilco.bot.PlugBot.core.constant.PermissionLevel;
import com.evilco.bot.PlugBot.core.constant.UserStatus;
import com.evilco.bot.PlugBot.core.data.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.List;

/**
 * @package com.evilco.bot.PlugBot.bridge
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PlugInterface {

	/**
	 * Stores the used web driver.
	 */
	protected WebDriver driver = null;

	/**
	 * Stores the JSON parser.
	 */
	protected Gson gson = null;

	/**
	 * Constructs a new PlugInterface.
	 * @param driver
	 */
	public PlugInterface (WebDriver driver) {
		this.driver = driver;
		this.gson = new Gson ();
	}

	/**
	 * Displays a message in the chatlog.
	 * Note: This method does not make any sense in this context.
	 * @param message
	 * @param alert
	 */
	public void ChatLog (String message, boolean alert) {
		this.GetJavascriptExecutor ().executeScript ("API.chatLog (arguments[0], arguments[1]);", message, alert);
	}

	/**
	 * Joins the DJ booth (or the waitlist if the booth is already full).
	 */
	public void DJJoin () {
		this.GetJavascriptExecutor ().executeScript ("API.djJoin ();");
	}

	/**
	 * Leaves the DJ booth.
	 */
	public void DJLeave () {
		this.GetJavascriptExecutor ().executeScript ("API.djLeave ();");
	}

	/**
	 * Returns a list of admins in the channel.
	 * @return
	 */
	public List<PlugUser> GetAdmins () {
		// get JSON string
		String data = ((String) this.GetJavascriptExecutor ().executeScript ("return JSON.stringify (API.getAdmins ());")); // Note: We're using JSON strings to communicate to prevent Selenium from crashing

		// decode
		return this.gson.fromJson (data, (new TypeToken<List<PlugUser>> () { }).getType ());
	}

	/**
	 * Returns a list of ambassadors in the channel.
	 * @return
	 */
	public List<PlugUser> GetAmbassadors () {
		// get JSON string
		String data = ((String) this.GetJavascriptExecutor ().executeScript ("return JSON.stringify (API.getAmbassadors ());")); // Note: We're using JSON strings to communicate to prevent Selenium from crashing

		// decode
		return this.gson.fromJson (data, (new TypeToken<List<PlugUser>> () { }).getType ());
	}

	/**
	 * Returns the current audience.
	 * @return
	 */
	public List<PlugUser> GetAudience () {
		// get JSON string
		String data = ((String) this.GetJavascriptExecutor ().executeScript ("return JSON.stringify (API.getAudience ());")); // Note: We're using JSON strings to communicate to prevent Selenium from crashing

		// decode
		return this.gson.fromJson (data, (new TypeToken<List<PlugUser>> () { }).getType ());
	}

	// TODO: Missing function getBannedUsers (): The implementation seems to be not done yet (hacky).

	/**
	 * Returns the current DJ.
	 * @return
	 */
	public PlugUser GetDJ () {
		// get json string
		String data = ((String) this.GetJavascriptExecutor ().executeScript ("return JSON.stringify (API.getDJ());"));

		// decode
		return this.gson.fromJson (data, PlugUser.class);
	}

	/**
	 * Returns the room history.
	 * @return
	 */
	public List<HistoryItem> GetHistory () {
		// get JSON string
		String data = ((String) this.GetJavascriptExecutor ().executeScript ("return JSON.stringify (API.getHistory ());"));

		// decode
		return this.gson.fromJson (data, (new TypeToken<List<HistoryItem>> () { }).getType ());
	}

	/**
	 * Returns the host of the room.
	 * Note: This method returns null if the host is currently not in the room.
	 * @return
	 */
	public PlugUser GetHost () {
		// get json string
		String data = ((String) this.GetJavascriptExecutor ().executeScript ("return JSON.stringify (API.getHost ());"));

		// decode
		return this.gson.fromJson (data, PlugUser.class);
	}

	/**
	 * Returns an instance of the JavascriptExecutor.
	 * @return
	 */
	public JavascriptExecutor GetJavascriptExecutor () {
		return ((JavascriptExecutor) this.driver);
	}

	/**
	 * Returns the currently played media.
	 * @return
	 */
	public PlugMedia GetMedia () {
		// get JSON string
		String data = ((String) this.GetJavascriptExecutor ().executeScript ("return JSON.stringify (API.getMedia ());"));

		// decode
		return this.gson.fromJson (data, PlugMedia.class);
	}

	/**
	 * Returns the upcoming media (for the current user).
	 * @return
	 */
	public QueuedMedia GetNextMedia () {
		// get JSON string
		String data = ((String) this.GetJavascriptExecutor ().executeScript ("return JSON.stringify (API.getNextMedia ());"));

		// decode
		return this.gson.fromJson (data, QueuedMedia.class);
	}

	/**
	 * Returns the current room score.
	 * @return
	 */
	public PlugVotes GetRoomScore () {
		// get JSON string
		String data = ((String) this.GetJavascriptExecutor ().executeScript ("return JSON.stringify (API.getRoomScore ());"));

		// decode
		return this.gson.fromJson (data, PlugVotes.class);
	}

	/**
	 * Returns a list of staff in the channel.
	 * @return
	 */
	public List<PlugUser> GetStaff () {
		// get JSON string
		String data = ((String) this.GetJavascriptExecutor ().executeScript ("return JSON.stringify (API.getStaff ());")); // Note: We're using JSON strings to communicate to prevent Selenium from crashing

		// decode
		return this.gson.fromJson (data, (new TypeToken<List<PlugUser>> () { }).getType ());
	}

	/**
	 * Returns the elapsed time on the current media.
	 * @return
	 */
	public int GetTimeElapsed () {
		return ((Integer) this.GetJavascriptExecutor ().executeScript ("return API.getTimeElapsed ();"));
	}

	/**
	 * Returns the remaining time on the current media.
	 * @return
	 */
	public int GetTimeRemaining () {
		return ((Integer) this.GetJavascriptExecutor ().executeScript ("return API.getTimeRemaining ();"));
	}

	/**
	 * Returns the current user.
	 * @return
	 */
	public PlugUser GetUser () {
		// get json string
		String data = ((String) this.GetJavascriptExecutor ().executeScript ("return JSON.stringify (API.getUser ());"));

		// decode
		return this.gson.fromJson (data, PlugUser.class);
	}

	/**
	 * Returns a user based on his userID.
	 * @param userID
	 * @return
	 */
	public PlugUser GetUser (String userID) {
		// get json string
		String data = ((String) this.GetJavascriptExecutor ().executeScript ("return JSON.stringify (API.getUser (arguments[0]));", userID));

		// decode
		return this.gson.fromJson (data, PlugUser.class);
	}

	/**
	 * Returns a list of users which are currently in the room.
	 * @return
	 */
	public List<PlugUser> GetUsers () {
		// get JSON string
		String data = ((String) this.GetJavascriptExecutor ().executeScript ("return JSON.stringify (API.getUsers ());")); // Note: We're using JSON strings to communicate to prevent Selenium from crashing

		// decode
		return this.gson.fromJson (data, (new TypeToken<List<PlugUser>> () { }).getType ());
	}

	/**
	 * Returns the currently set volume.
	 * @return
	 */
	public float GetVolume () {
		return (((Integer) this.GetJavascriptExecutor ().executeScript ("return API.getVolume ();")) / 100);
	}

	/**
	 * Returns all users on the waitlist.
	 * @return
	 */
	public List<PlugUser> GetWaitList () {
		// get JSON string
		String data = ((String) this.GetJavascriptExecutor ().executeScript ("return JSON.stringify (API.getWaitList ());")); // Note: We're using JSON strings to communicate to prevent Selenium from crashing

		// decode
		return this.gson.fromJson (data, (new TypeToken<List<PlugUser>> () { }).getType ());
	}

	/**
	 * Returns the waitlist position of the current user (-1 if not in waitlist).
	 * @return
	 */
	public int GetWaitListPosition () {
		return ((Integer) this.GetJavascriptExecutor ().executeScript ("return API.getWaitListPosition ();"));
	}

	/**
	 * Returns the waitlist position of the specified user (-1 if not in the waitlist).
	 * @param userID
	 * @return
	 */
	public int GetWaitListPosition (String userID) {
		return ((Integer) this.GetJavascriptExecutor ().executeScript ("return API.getWaitListPosition (arguments[0]);", userID));
	}

	/**
	 * Checks whether the current user has a specific permission level.
	 * @param level
	 * @return
	 */
	public boolean HasPermission (PermissionLevel level) {
		return ((Boolean) this.GetJavascriptExecutor ().executeScript ("return API.hasPermission (null, arguments[0]);", level.level));
	}

	/**
	 * Checks whether a specific user has a specific permission level.
	 * @param userID
	 * @param level
	 * @return
	 */
	public boolean HasPermission (String userID, PermissionLevel level) {
		return ((Boolean) this.GetJavascriptExecutor ().executeScript ("return API.hasPermission (arguments[0], arguments[1]);", userID, level.level));
	}

	/**
	 * Forces the media to be skipped.
	 * Note: Proper permissions needed.
	 */
	public void ModerateForceSkip () {
		this.GetJavascriptExecutor ().executeScript ("API.moderateForceSkip ()");
	}

	/**
	 * Adds a user to the DJ booth.
	 * @param userID
	 */
	public void ModerateAddDJ (String userID) {
		this.GetJavascriptExecutor ().executeScript ("API.moderateAddDJ (arguments[0]);", userID);
	}

	/**
	 * Bans a user.
	 * @param userID
	 * @param reason
	 * @param duration
	 */
	public void ModerateBanUser (String userID, String reason, BanDuration duration) {
		this.GetJavascriptExecutor ().executeScript ("API.moderateBanUser (arguments[0], arguments[1], arguments[2]);", userID, reason, duration.duration);
	}

	/**
	 * Deletes a chat line.
	 * @param chatID
	 */
	public void ModerateDeleteChat (String chatID) {
		this.GetJavascriptExecutor ().executeScript ("API.moderateDeleteChat (arguments[0]);", chatID);
	}

	/**
	 * Locks the wait list.
	 * @param value
	 * @param removeAll
	 */
	public void ModerateLockWaitList (boolean value, boolean removeAll) {
		this.GetJavascriptExecutor ().executeScript ("API.moderateLockWaitList (arguments[0], arguments[1]);", value, removeAll);
	}

	/**
	 * Moves a DJ to a specific spot in the waitlist.
	 * @param userID
	 * @param position
	 */
	public void ModerateMoveDJ (String userID, int position) {
		this.GetJavascriptExecutor ().executeScript ("API.moderateMoveDJ (arguments[0], arguments[1]);", userID, position);
	}

	/**
	 * Removes a user from the DJ booth.
	 * @param userID
	 */
	public void ModerateRemoveDJ (String userID) {
		this.GetJavascriptExecutor ().executeScript ("API.moderateRemoveDJ (arguments[0]);", userID);
	}

	/**
	 * Sets a user's role.
	 * @param userID
	 * @param level
	 */
	public void ModerateSetRole (String userID, PermissionLevel level) {
		this.GetJavascriptExecutor ().executeScript ("API.moderateSetRole (arguments[0], arguments[1]);", userID, level.level);
	}

	/**
	 * Unbans a user.
	 * @param userID
	 */
	public void ModerateUnbanUser (String userID) {
		this.GetJavascriptExecutor ().executeScript ("API.moderateUnbanUser (arguments[0]);", userID);
	}

	/**
	 * Sends a message to the chat.
	 * @param message
	 */
	public void SendChat (String message) {
		this.GetJavascriptExecutor ().executeScript ("API.sendChat (arguments[0]);", message);
	}

	/**
	 * Sets the current status.
	 * @param status
	 */
	public void SetStatus (UserStatus status) {
		this.GetJavascriptExecutor ().executeScript ("API.setStatus (arguments[0]);", status);
	}

	/**
	 * Sets the volume.
	 * @param value
	 */
	public void SetVolume (float value) {
		this.GetJavascriptExecutor ().executeScript ("API.setVolume (arguments[0]);", (value * 100));
	}
}
