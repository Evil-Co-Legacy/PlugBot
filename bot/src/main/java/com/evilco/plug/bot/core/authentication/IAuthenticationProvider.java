package com.evilco.plug.bot.core.authentication;

import org.openqa.selenium.WebDriver;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IAuthenticationProvider {

	/**
	 * Authenticates a client.
	 * @param username
	 * @param password
	 */
	public void authenticate (String username, String password);

	/**
	 * Returns the provider name.
	 * @return
	 */
	public String getProviderName ();
}
