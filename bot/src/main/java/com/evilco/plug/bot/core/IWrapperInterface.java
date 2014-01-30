package com.evilco.plug.bot.core;

import org.springframework.stereotype.Component;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Component
public interface IWrapperInterface {

	/**
	 * Callback in case the application shuts down.
	 */
	public void onShutdown ();
}
