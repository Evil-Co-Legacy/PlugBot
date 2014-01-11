package com.evilco.bot.PlugBot.plugins.LengthLimiter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @package com.evilco.bot.PlugBot.plugins.LengthLimiter
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@XmlRootElement (name = "configuration")
public class LengthLimiterConfiguration {

	/**
	 * Defines the minimal length for a warning (in minutes).
	 */
	@XmlElement (name = "warnLength")
	public int warnLength = 8;

	/**
	 * Defines the minimal length for a skip (in minutes).
	 */
	@XmlElement (name = "skipLength")
	public int skipLength = 10;
}
