package com.evilco.bot.PlugBot.plugins.UserGreet;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @package com.evilco.bot.PlugBot.plugins.UserGreet
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
@XmlRootElement (name = "configuration")
public class UserGreetConfiguration {

	/**
	 * Indicates whether regular (non staff) users will be
	 * greeted.
	 */
	@XmlElement (name = "greetRegularUsers")
	public boolean greetRegularUsers = true;

	/**
	 * Stores a list of possible greetings.
	 */
	@XmlElementWrapper (name = "greetLines")
	@XmlElement (name = "line")
	public List<String> greetLines;

	/**
	 * Constructs a new UserGreetConfiguration and initializes default values.
	 */
	public UserGreetConfiguration () {
		this.greetLines = new ArrayList<String> (Arrays.asList ((new String[] {
			"Hey, %s!",
			"Welcome, %s!",
			"Connichiwa, %s!",
			"Nice to see you, %s!",
			"Hey everybody! See who just joined us: %s!"
		})));
	}
}
