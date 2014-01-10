package com.evilco.bot.PlugBot.core.data;

/**
 * @package com.evilco.bot.PlugBot.core.data
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PlugUser {

	/**
	 * Stores the avatarId of the user.
	 */
	public String avatarID;

	/**
	 * Indicates whether the user grabbed the current song.
	 */
	public boolean curated;

	/**
	 * Indicates how many points a user earned through grabbing songs (or playing songs other users grabbed?).
	 */
	public int curatorPoints;

	/**
	 * Stores the date a user joined in.
	 */
	public String dateJoined;

	/**
	 * Indicates how many points a user earned through playing songs.
	 */
	public int djPoints;

	/**
	 * Indicates how many fans a user has.
	 */
	public int fans;

	/**
	 * Stores the unique user identifier.
	 */
	public String id;

	/**
	 * Stores the user's language (unused).
	 */
	public String language;

	/**
	 * Indicates how many points a user earned through listening.
	 */
	public int listenerPoints;

	/**
	 * Stores the current permission level.
	 */
	public int permission;

	/**
	 * ???
	 */
	public int relationship;

	/**
	 * Stores the current availability status (???)
	 */
	public int status;

	/**
	 * ???
	 */
	public int uIndex;

	/**
	 * Stores the current username.
	 */
	public String username;

	/**
	 * ???
	 */
	public int vote;

	/**
	 * Stores the current waitlist index of this user.
	 */
	public int wlIndex;
}
