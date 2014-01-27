package com.evilco.plug.bot.core.configuration;

import com.evilco.plug.bot.core.Bot;
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

	/**
	 * Constructs a new bot configuration.
	 * @return
	 */
	@Bean
	public BotConfiguration configuration () {
		return BotConfiguration.newInstance ();
	}

	@Bean (name = "Bot")
	@Scope ("prototype")
	public Bot bot () {
		return new Bot ();
	}
}
