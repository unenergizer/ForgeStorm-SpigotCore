package com.forgestorm.spigotcore.menus.actions;

import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.menus.profession.ProfessionMenu;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenProfessionsMenu implements ClickAction {

	private final SpigotCore PLUGIN;
	
	@Override
	public void click(Player player) {
		new ProfessionMenu(PLUGIN, player).open(player);
	}
}
