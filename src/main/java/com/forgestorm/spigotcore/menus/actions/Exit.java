package com.forgestorm.spigotcore.menus.actions;

import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Exit implements ClickAction {

	private final SpigotCore PLUGIN;
	
	@Override
	public void click(Player player) {
		player.closeInventory();
		PLUGIN.getProfileManager().getProfile(player).setCurrentMenu(null);
	}
}
