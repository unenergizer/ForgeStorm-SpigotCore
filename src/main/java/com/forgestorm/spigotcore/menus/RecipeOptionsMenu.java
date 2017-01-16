package com.forgestorm.spigotcore.menus;

import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.actions.SelectRecipeDisplay;
import com.forgestorm.spigotcore.util.item.ItemGenerator;

public class RecipeOptionsMenu extends Menu {

	private final SpigotCore PLUGIN;
	
	public RecipeOptionsMenu(SpigotCore plugin) {
		super(plugin);
		PLUGIN = plugin;
		init("Recipe Options", 1);
		makeMenuItems();
	}

	@Override
	protected void makeMenuItems() {
		ItemTypes type = ItemTypes.MENU;
		ItemGenerator itemGen = PLUGIN.getItemGen();
		
		ItemStack showall, showcollected;
		showall = itemGen.generateItem("recipe_options_menu_show_all", type);
		showcollected = itemGen.generateItem("recipe_options_menu_show_collected", type);
		
		setItem(showall, 0, new SelectRecipeDisplay(PLUGIN, true));
		setItem(showcollected, 1, new SelectRecipeDisplay(PLUGIN, false));
	}
}
