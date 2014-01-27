package com.evilco.plug.bot.core.authentication;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Service
public class GoogleAuthenticationProvider implements IAuthenticationProvider {

	/**
	 * Stores the internal logger instance.
	 */
	protected static final Logger logger = Logger.getLogger ("GoogleAuthenticationProvider");

	/**
	 * Caches the current web driver instance.
	 */
	@Autowired
	protected WebDriver driver;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void authenticate (String username, String password) {
		WebElement element = this.driver.findElement (By.id ("google"));
		element.click ();

		// wait until we hit google
		while (!this.driver.getCurrentUrl ().contains ("google.com"));

		// log
		logger.info ("Entering Google login information.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getProviderName () {
		return "Google Accounts";
	}
}
