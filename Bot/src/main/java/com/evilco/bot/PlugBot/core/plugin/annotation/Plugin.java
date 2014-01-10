package com.evilco.bot.PlugBot.core.plugin.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @package com.evilco.bot.PlugBot.core.plugin.annotation
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Retention (RetentionPolicy.RUNTIME)
@Target (ElementType.TYPE)
public @interface Plugin {

	/**
	 * The plugin author.
	 * @return
	 */
	public String author ();

	/**
	 * The plugin description.
	 * @return
	 */
	public String description () default "";

	/**
	 * The plugin name.
	 * @return
	 */
	public String name ();

	/**
	 * The plugin version.
	 * @return
	 */
	public String version () default "1.0.0-SNAPSHOT";
}
