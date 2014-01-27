package com.evilco.plug.bot.core.configuration;

import com.evilco.plug.bot.core.Bot;
import com.evilco.plug.bot.core.event.EventManager;
import com.evilco.plug.bot.core.plugin.PluginManager;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Configuration
@ComponentScan (basePackages = {"com.evilco.plug.bot.core"})
public class CoreBeanConfiguration {

	@Bean (name = "Bot")
	@Scope ("singleton")
	public Bot bot () {
		return new Bot ();
	}

	/**
	 * Constructs a new bot configuration.
	 * @return
	 */
	@Bean
	@Scope ("singleton")
	public BotConfiguration configuration () {
		return BotConfiguration.newInstance ();
	}

	/**
	 * Constructs a new event manager.
	 * @return
	 */
	@Bean
	@Scope ("singleton")
	public EventManager eventManager () {
		return (new EventManager ());
	}

	/**
	 * Constructs a new plugin manager.
	 * @return
	 */
	@Bean
	@Scope ("singleton")
	public PluginManager pluginManager () {
		return (new PluginManager ());
	}

	/**
	 * Reads the current driver instance.
	 * @return
	 */
	@Bean
	@Scope ("singleton")
	public WebDriver driver () {
		return (this.bot ().getDriver ());
	}
}
