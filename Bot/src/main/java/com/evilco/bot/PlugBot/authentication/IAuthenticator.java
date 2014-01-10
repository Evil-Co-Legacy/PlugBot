package com.evilco.bot.PlugBot.authentication;

import org.openqa.selenium.WebDriver;

/**
 * @package com.evilco.bot.PlugBot.authentication
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public interface IAuthenticator {

	/**
	 * Authenticates the bot with this authenticator.
	 */
	public void Authenticate (WebDriver driver);

	/**
	 * Returns the provider name to use.
	 * @return
	 */
	public String GetProviderName ();
}
