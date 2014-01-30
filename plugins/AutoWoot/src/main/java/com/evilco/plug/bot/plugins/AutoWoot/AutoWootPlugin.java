package com.evilco.plug.bot.plugins.AutoWoot;

import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;
import com.evilco.plug.bot.core.event.communication.dj.DjAdvanceEvent;
import com.evilco.plug.bot.core.plugin.AbstractPlugin;
import com.evilco.plug.bot.core.plugin.annotation.Plugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Plugin (name = "AutoWoot", version = "1.0.0-SNAPSHOT", description = "Automatically woots all played tracks.", author = "Johannes \"Akkarin\" Donath")
@Component
public class AutoWootPlugin extends AbstractPlugin implements ApplicationListener<DjAdvanceEvent> {

	/**
	 * Caches the communication adapter.
	 */
	@Autowired
	private PageCommunicationAdapter pageCommunicationAdapter;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onApplicationEvent (DjAdvanceEvent event) {
		this.pageCommunicationAdapter.votePositive ();
	}
}
