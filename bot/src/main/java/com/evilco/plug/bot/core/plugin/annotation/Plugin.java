package com.evilco.plug.bot.core.plugin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.TYPE)
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
