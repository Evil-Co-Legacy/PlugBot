package com.evilco.plug.bot.launchwrapper.shell;

import com.evilco.plug.bot.core.Bot;
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
public class WrapperBannerProvider extends DefaultBannerProvider {

	/**
	 * Stores a cached version of the version.
	 */
	protected String cachedVersion = null;

	/**
	 * Indicates whether the build is a development build.
	 */
	protected boolean isDevelopmentVersion = false;

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
		banner.append (this.getVersion () + OsUtils.LINE_SEPARATOR);

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
		// get version
		if (this.cachedVersion == null) {
			// get package
			Package p = Bot.class.getPackage ();

			// check package
			this.cachedVersion = (p != null && p.getImplementationVersion () != null ? p.getImplementationVersion () : "Development Snapshot");

			// check whether we have a development build
			String version = this.cachedVersion.toLowerCase ();
			this.isDevelopmentVersion = (version.contains ("development") || version.contains ("a") || version.contains ("b") || version.contains ("rc") || version.contains ("rc"));
		}

		// return cached version
		return this.cachedVersion;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getWelcomeMessage () {
		StringBuffer message = new StringBuffer ();

		// display development notice
		if (this.isDevelopmentVersion) {
			message.append ("NOTE: You are running a development snapshots. These snapshots are not supported and might contain major issues." + OsUtils.LINE_SEPARATOR);
			message.append ("Please only use development snapshots for testing and report all upcoming problems (crashes etc)." + OsUtils.LINE_SEPARATOR + OsUtils.LINE_SEPARATOR);
		}

		// append default message
		message.append ("Welcome to the PlugBot Console. For assistance press or type \"help\" and hit ENTER." + OsUtils.LINE_SEPARATOR);

		// return finished message
		return message.toString ();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getProviderName () {
		return "wrapper banner provider";
	}
}
