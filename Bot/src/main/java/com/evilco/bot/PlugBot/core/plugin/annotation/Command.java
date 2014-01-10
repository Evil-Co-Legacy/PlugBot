package com.evilco.bot.PlugBot.core.plugin.annotation;

import com.evilco.bot.PlugBot.core.constant.PermissionLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @package com.evilco.bot.PlugBot.core.plugin.annotation
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

	/**
	 * The aliases for this command.
	 * @return
	 */
	public String[] alias ();

	/**
	 * A short description about the command.
	 * @return
	 */
	public String description ();

	/**
	 * The minimal permission level needed to access this command.
	 * @return
	 */
	public PermissionLevel permission () default PermissionLevel.USER;

	/**
	 * A short usage example.
	 * @return
	 */
	public String usage ();
}
