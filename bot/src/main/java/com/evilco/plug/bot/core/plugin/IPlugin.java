package com.evilco.plug.bot.core.plugin;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IPlugin {

	/**
	 * Enable callback.
	 */
	public void onEnable ();

	/**
	 * Disable callback.
	 */
	public void onDisable ();
}
