package com.forgestorm.spigotcore.menus.actions;

import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.menus.RecipeMenu;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OpenPlayerRecipeMenu implements ClickAction {

	private final SpigotCore PLUGIN;
	private final boolean SHOW_ALL;
	
	@Override
	public void click(Player player) {
		new RecipeMenu(PLUGIN, player, SHOW_ALL).open(player);
	}
}
