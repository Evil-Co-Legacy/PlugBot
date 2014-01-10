package com.evilco.bot.PlugBot.core.data;

/**
 * @package com.evilco.bot.PlugBot.core.data
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PlugMedia {

	/**
	 * The media author.
	 */
	public String author;

	/**
	 * ???
	 */
	public String cid;

	/**
	 * The media duration in seconds.
	 */
	public int duration;

	/**
	 * ??? (1 = YouTube, 2/0 = SoundCloud)
	 */
	public int format;

	/**
	 * The media unique ID (YouTube/SoundCloud ID)
	 */
	public String id;

	/**
	 * The preview image (if any).
	 */
	public String image;

	/**
	 * ???
	 */
	public int index;

	/**
	 * ???
	 */
	public int modified;

	/**
	 * The media title.
	 */
	public String title;
}
