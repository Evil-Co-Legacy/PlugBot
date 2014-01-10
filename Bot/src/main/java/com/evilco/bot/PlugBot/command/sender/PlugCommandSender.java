package com.evilco.bot.PlugBot.command.sender;

import com.evilco.bot.PlugBot.bridge.PlugInterface;
import com.evilco.bot.PlugBot.command.ICommandSender;
import com.evilco.bot.PlugBot.core.constant.PermissionLevel;
import com.evilco.bot.PlugBot.core.data.PlugUser;

import java.lang.reflect.Field;

/**
 * @package com.evilco.bot.PlugBot.command.sender
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class PlugCommandSender extends PlugUser implements ICommandSender {

	/**
	 * Stores the internal interface.
	 */
	protected PlugInterface plugInterface = null;

	/**
	 * Constructs a new command sender.
	 * @param user
	 */
	public PlugCommandSender (PlugUser user, PlugInterface plugInterface) {
		for (Field field : user.getClass ().getFields ()) {
			try {
				field.set (this, field.get (user));
			} catch (Exception ex) {
				ex.printStackTrace ();
			}
		}

		this.plugInterface = plugInterface;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PermissionLevel GetPermissionLevel () {
		return PermissionLevel.FromInt (this.permission);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void SendMessage (String message) {
		// build message
		StringBuilder builder = new StringBuilder ();

		// append @
		builder.append ("@");

		// append username
		builder.append (this.username);

		// append spacer
		builder.append (" ");

		// append message
		builder.append (message);

		this.plugInterface.SendChat (builder.toString ());
	}
}
