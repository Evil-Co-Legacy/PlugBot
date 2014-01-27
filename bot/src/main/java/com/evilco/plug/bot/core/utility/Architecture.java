package com.evilco.plug.bot.core.utility;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum Architecture {
	x86,
	x64,
	PPC,
	SPARC,
	UNKNOWN;

	/**
	 * Guesses an architecutre.
	 * @param value
	 * @return
	 */
	public static Architecture guessArchitecture () {
		String value = System.getProperty ("os.arch");

		// guess
		if (value.equalsIgnoreCase ("x86") || value.equalsIgnoreCase ("i386")) return x86;
		if (value.equalsIgnoreCase ("amd64")) return x64;
		if (value.equalsIgnoreCase ("ppc")) return PPC;
		if (value.equalsIgnoreCase ("sparc")) return SPARC;

		return UNKNOWN;
	}
}
