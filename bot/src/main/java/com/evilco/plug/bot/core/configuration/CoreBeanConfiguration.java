package com.evilco.plug.bot.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Configuration
public class CoreBeanConfiguration {

	/**
	 * Constructs a new bot configuration.
	 * @return
	 */
	@Bean
	public BotConfiguration configuration () {
		return BotConfiguration.newInstance ();
	}
}
