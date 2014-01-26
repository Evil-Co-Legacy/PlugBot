package com.evilco.bot.PlugBot.console;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultPromptProvider;
import org.springframework.stereotype.Component;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Component
@Order (Ordered.HIGHEST_PRECEDENCE)
public class InterfacePromptProvider extends DefaultPromptProvider {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getPrompt () {
		return "PlugBot>";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getProviderName () {
		return "InterfacePromptProvider";
	}
}
