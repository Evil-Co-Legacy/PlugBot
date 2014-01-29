package com.evilco.plug.bot.core.communication.data;

import com.evilco.plug.bot.core.command.ICommandSender;
import com.evilco.plug.bot.core.communication.PageCommunicationAdapter;

import java.lang.reflect.Field;

/**
 * @auhtor Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public class UserCommandSender extends User implements ICommandSender {

	/**
	 * Stores the parent communication adapter.
	 */
	protected final PageCommunicationAdapter adapter;

	/**
	 * Constructs a new User Command Sender.
	 * @param adapter
	 * @param user
	 */
	public UserCommandSender (PageCommunicationAdapter adapter, User user) {
		for (Field field : user.getClass ().getFields ()) {
			try {
				field.setAccessible (true);
				field.set (this, field.get (user));
			} catch (IllegalAccessException ignore) { }
		}

		this.adapter = adapter;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasPermission (PermissionLevel level) {
		return this.adapter.hasPermission (this.id, level);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void sendMessage (String message) {
		this.adapter.sendChat ("@" + this.username + " " + message);
	}
}
