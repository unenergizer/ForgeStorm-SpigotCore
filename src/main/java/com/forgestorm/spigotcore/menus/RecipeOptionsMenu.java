package com.forgestorm.spigotcore.menus;

import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.actions.SelectRecipeDisplay;
import com.forgestorm.spigotcore.util.item.ItemGenerator;

public class RecipeOptionsMenu extends Menu {

	private final SpigotCore plugin;
	
	public RecipeOptionsMenu(SpigotCore plugin) {
		super(plugin);
		this.plugin = plugin;
		init("Recipe Options", 1);
		makeMenuItems();
	}

	@Override
	protected void makeMenuItems() {
		ItemTypes type = ItemTypes.MENU;
		ItemGenerator itemGen = plugin.getItemGen();
		
		ItemStack showAll, showCollected;
		showAll = itemGen.generateItem("recipe_options_menu_show_all", type);
		showCollected = itemGen.generateItem("recipe_options_menu_show_collected", type);
		
		setItem(showAll, 0, new SelectRecipeDisplay(plugin, true));
		setItem(showCollected, 1, new SelectRecipeDisplay(plugin, false));
	}
}
