package com.forgestorm.spigotcore.menus.actions;

import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.menus.SettingsMenu;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenSettingsMenu implements ClickAction {

	private final SpigotCore PLUGIN;
	
	@Override
	public void click(Player player) {
		new SettingsMenu(PLUGIN, player).open(player);
	}
}
