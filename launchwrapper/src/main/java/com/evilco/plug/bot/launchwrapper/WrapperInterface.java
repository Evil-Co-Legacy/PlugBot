package com.evilco.plug.bot.launchwrapper;

import com.evilco.plug.bot.core.IWrapperInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Component ("wrapperInterface")
public class WrapperInterface implements IWrapperInterface {

	/**
	 * Stores the parent application.
	 */
	protected LaunchwrapperApplication application = null;

	/**
	 * Constructs a new Wrapper Interface.
	 * @param application
	 */
	@Autowired
	public WrapperInterface (LaunchwrapperApplication application) {
		this.application = application;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onShutdown () {
		this.application.shutdown ();
	}
}
