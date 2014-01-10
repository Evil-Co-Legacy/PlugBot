package com.evilco.bot.PlugBot.authentication;

import com.evilco.bot.PlugBot.core.configuration.Configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @package com.evilco.bot.PlugBot.authentication
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class GoogleAuthenticator implements IAuthenticator {

	/**
	 * Stores the account details to use.
	 */
	protected Configuration.ConfigurationAccount account = null;

	/**
	 * Constructs a new GoogleAuthenticator.
	 * @param account
	 */
	public GoogleAuthenticator (Configuration.ConfigurationAccount account) {
		this.account = account;
	}

	/**
	 * {@inheritDoc}
	 */
	public void Authenticate (WebDriver driver) {
		// fill out username
		WebElement usernameElement = driver.findElement (By.id ("Email"));
		usernameElement.sendKeys (this.account.username);

		// fill out password
		WebElement passwordElement = driver.findElement (By.id ("Passwd"));
		passwordElement.sendKeys (this.account.password);

		// submit form
		WebElement submitElement = driver.findElement (By.id ("signIn"));
		submitElement.click ();
	}

	/**
	 * {@inheritDoc}
	 */
	public String GetProviderName () {
		return "google";
	}
}
