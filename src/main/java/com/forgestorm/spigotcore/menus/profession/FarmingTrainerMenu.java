package com.forgestorm.spigotcore.menus.profession;

import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.util.item.ItemGenerator;

public class FarmingTrainerMenu extends Menu {
	
	private final SpigotCore PLUGIN;
	
	public FarmingTrainerMenu(SpigotCore plugin) {
		super(plugin);
		PLUGIN = plugin;
		init("Farming Trainer", 1);
		makeMenuItems();
	}

	@Override
	protected void makeMenuItems() {
		ItemStack exitButton;
		ItemTypes type = ItemTypes.MENU;
		ItemGenerator itemGen = PLUGIN.getItemGen();
		
	
		exitButton = itemGen.generateItem("exit_button", type);

		setItem(exitButton, 8, new Exit(PLUGIN));
	}
}
