package com.forgestorm.spigotcore.menus.actions;

import org.bukkit.entity.Player;

public interface ClickAction {
	/**
	 * Action to execute when clicked.
	 * @param player The player who clicked.
	 */
	void click(Player player);
}
