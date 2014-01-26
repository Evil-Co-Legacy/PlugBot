package com.evilco.bot.PlugBot.console;

import com.evilco.bot.PlugBot.Bot;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultBannerProvider;
import org.springframework.shell.support.util.OsUtils;
import org.springframework.stereotype.Component;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@Component
@Order (Ordered.HIGHEST_PRECEDENCE)
public class InterfaceBannerProvider extends DefaultBannerProvider {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getBanner () {
		// create empty string buffer
		StringBuffer banner = new StringBuffer ();

		// append banner
		banner.append (" ____     ___                     ____             __      " + OsUtils.LINE_SEPARATOR);
		banner.append ("/\\  _`\\  /\\_ \\                   /\\  _`\\          /\\ \\__   " + OsUtils.LINE_SEPARATOR);
		banner.append ("\\ \\ \\L\\ \\\\//\\ \\    __  __     __ \\ \\ \\L\\ \\    ___ \\ \\ ,_\\  " + OsUtils.LINE_SEPARATOR);
		banner.append (" \\ \\ ,__/  \\ \\ \\  /\\ \\/\\ \\  /'_ `\\\\ \\  _ <'  / __`\\\\ \\ \\/  " + OsUtils.LINE_SEPARATOR);
		banner.append ("  \\ \\ \\/    \\_\\ \\_\\ \\ \\_\\ \\/\\ \\L\\ \\\\ \\ \\L\\ \\/\\ \\L\\ \\\\ \\ \\_ " + OsUtils.LINE_SEPARATOR);
		banner.append ("   \\ \\_\\    /\\____\\\\ \\____/\\ \\____ \\\\ \\____/\\ \\____/ \\ \\__\\" + OsUtils.LINE_SEPARATOR);
		banner.append ("    \\/_/    \\/____/ \\/___/  \\/___L\\ \\\\/___/  \\/___/   \\/__/" + OsUtils.LINE_SEPARATOR);
		banner.append ("                              /\\____/                      " + OsUtils.LINE_SEPARATOR);
		banner.append ("                              \\_/__/                       " + OsUtils.LINE_SEPARATOR);

		// append version
		banner.append ((this.getVersion () != null ? "v" + this.getVersion () : "Development Snapshot") + OsUtils.LINE_SEPARATOR);

		// append copyright
		banner.append ("Copyright (C) 2014 Evil-Co <http://www.evil-co.org>" + OsUtils.LINE_SEPARATOR);

		// return banner
		return banner.toString ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getVersion () {
		Package p = Bot.class.getPackage ();

		// check package
		if (p == null) return "(unknown)";

		// get version
		return p.getImplementationVersion ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getWelcomeMessage () {
		StringBuffer message = new StringBuffer ();

		// append default message
		message.append ("Welcome to the PlugBot Console. For assistance press or type \"help\" and hit ENTER." + OsUtils.LINE_SEPARATOR);

		// append SNAPSHOT notice
		String version = this.getVersion ();

		if (version == null || version.toLowerCase ().contains ("snapshot") || version.toLowerCase ().contains ("a") || version.toLowerCase ().contains ("b") || version.toLowerCase ().contains ("rc")) {
			message.append ("NOTE: You are running a development snapshots. These snapshots are not supported and might contain major issues." + OsUtils.LINE_SEPARATOR);
			message.append ("Please only use development snapshots for testing and report all upcoming problems (crashes etc)." + OsUtils.LINE_SEPARATOR);
		}

		// return finished message
		return message.toString ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getProviderName () {
		return "InterfaceBannerProvider";
	}
}
