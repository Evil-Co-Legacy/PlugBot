package com.evilco.plug.bot.core.utility;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum Platform {
	LINUX,
	MAC_OS_X,
	SOLARIS,
	WINDOWS,
	UNKNOWN;

	/**
	 * Guesses the current platform.
	 * @return
	 */
	public static Platform guessPlatform () {
		String osName = System.getProperty("os.name").toLowerCase();

		// detect system
		if (osName.contains("win"))
			return WINDOWS;
		if (osName.contains("mac"))
			return MAC_OS_X;
		if (osName.contains("solaris") || osName.contains("sunos"))
			return SOLARIS;
		if (osName.contains("linux"))
			return LINUX;
		if (osName.contains("unix"))
			return LINUX;

		// unknown system
		return UNKNOWN;
	}
}
