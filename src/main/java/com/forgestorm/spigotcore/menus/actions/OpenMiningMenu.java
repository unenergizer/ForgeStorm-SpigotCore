package com.forgestorm.spigotcore.menus.actions;

import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.menus.profession.MiningTrainerMenu;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenMiningMenu implements ClickAction {

	private final SpigotCore PLUGIN;
	
	@Override
	public void click(Player player) {
		new MiningTrainerMenu(PLUGIN, player, false).open(player);
	}
}
