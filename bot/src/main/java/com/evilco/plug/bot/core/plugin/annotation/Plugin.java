package com.evilco.plug.bot.core.plugin.annotation;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public @interface Plugin {

	/**
	 * Stores the plugin author.
	 * @return
	 */
	public String author () default "";

	/**
	 * Stores the plugin description.
	 * @return
	 */
	public String description () default "";

	/**
	 * Stores the plugin name.
	 * @return
	 */
	public String name ();

	/**
	 * Stores the plugin URL.
	 * @return
	 */
	public String url () default "";

	/**
	 * Stores the plugin version.
	 * @return
	 */
	public String version () default "1.0.0-SNAPSHOT";
}
