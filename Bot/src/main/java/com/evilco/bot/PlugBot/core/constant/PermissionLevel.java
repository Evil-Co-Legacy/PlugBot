package com.evilco.bot.PlugBot.core.constant;

/**
 * @package com.evilco.bot.PlugBot.core.constant
 * @author Johannes Donath <johannesd@evil-co.com>
 * @copyright Copyright (C) 2014 Evil-Co <http://www.evil-co.org>
 */
public enum PermissionLevel {
	USER (0),
	RESIDENT_DJ (1),
	BOUNCER (2),
	MANAGER (3),
	CO_HOST (4),
	HOST (5),
	AMBASSADOR (8),
	ADMIN (10),
	UNKNOWN (-1);

	/**
	 * Stores the numeric permission level associated with the current level.
	 */
	public final int level;

	/**
	 * Constructs a new PermissionLevel.
	 * @param level
	 */
	private PermissionLevel (int level) {
		this.level = level;
	}

	/**
	 * Converts a permission level from it's numeric presentation.
	 * @param level
	 * @return
	 */
	public static PermissionLevel FromInt (int level) {
		for (PermissionLevel permissionLevel : PermissionLevel.values ()) {
			if (permissionLevel.level == level) return permissionLevel; // TODO: This might be a lot better if we'd use maps ...
		}

		return null;
	}
}
