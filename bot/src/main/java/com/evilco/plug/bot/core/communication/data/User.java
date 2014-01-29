package com.evilco.plug.bot.core.communication.data;

import com.evilco.plug.bot.core.command.ICommandSender;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class User {

	/**
	 * Stores the user avatar.
	 */
	public String avatarID;

	/**
	 * Defines whether a user curated the current song.
	 */
	public boolean curated;

	/**
	 * Stores the amount of points a user gained through curating.
	 */
	public int curatorPoints;

	/**
	 * Stores the date on which a user registered.
	 */
	public String dateJoined;

	/**
	 * Stores the amount of points a user gained through DJing.
	 */
	public int djPoints;

	/**
	 * Stores the amount of fans a user has.
	 */
	public int fans;

	/**
	 * Stores the user UUID.
	 */
	public String id;

	/**
	 * Stores the interface language the user is currently using.
	 */
	public String language;

	/**
	 * Stores the amount of points a user gained by listening (wooting and mehing).
	 */
	public int listenerPoints;

	/**
	 * Stores the permission level the current user has in the current room.
	 */
	public PermissionLevel permission;

	/**
	 * ???
	 * @todo Documentation needed
	 */
	public int relationship;

	/**
	 * Stores the current availability status of a user.
	 */
	public int status;

	/**
	 * ???
	 * @todo Documentation needed
	 */
	public int uIndex;

	/**
	 * Stores the current username of a user.
	 */
	public String username;

	/**
	 * Current Vote (???)
	 * @todo Documentation needed
	 */
	public int vote;

	/**
	 * Stores the current index of the waitlist a user is on.
	 */
	public int wlIndex;
}
