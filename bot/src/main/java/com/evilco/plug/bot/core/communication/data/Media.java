package com.evilco.plug.bot.core.communication.data;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class Media {

	/**
	 * Stores the media author.
	 */
	public String author;

	/**
	 * Stores the media ID for the current platform.
	 */
	public String cid;

	/**
	 * Stores the media duration in seconds.
	 */
	public double duration;

	/**
	 * Stores the media format (1 = YouTube, 2 = SoundCloud
	 */
	public int format;
}