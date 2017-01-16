package com.forgestorm.spigotcore.menus.actions;

import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.menus.CraftingMenu;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SelectRecipe implements ClickAction {

	private final SpigotCore PLUGIN;
	private final String RECIPE;
	
	@Override
	public void click(Player player) {
		new CraftingMenu(PLUGIN, RECIPE).open(player);
	}

}
