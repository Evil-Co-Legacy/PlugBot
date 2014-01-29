package com.evilco.plug.bot.launchwrapper.shell;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultHistoryFileNameProvider;
import org.springframework.stereotype.Component;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Component
@Order (Ordered.HIGHEST_PRECEDENCE)
public class WrapperFileNameProvider extends DefaultHistoryFileNameProvider {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getHistoryFileName () {
		return "PlugBot.log";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getProviderName () {
		return "wrapper filename provider";
	}
}
