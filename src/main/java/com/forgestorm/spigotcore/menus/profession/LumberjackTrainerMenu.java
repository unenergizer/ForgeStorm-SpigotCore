package com.forgestorm.spigotcore.menus.profession;

import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.FeatureComingSoon;
import com.forgestorm.spigotcore.util.item.ItemGenerator;

public class LumberjackTrainerMenu extends Menu {
	
	private final SpigotCore PLUGIN;
	
	public LumberjackTrainerMenu(SpigotCore plugin) {
		super(plugin);
		PLUGIN = plugin;
		init("Lumberjack Trainer", 1);
		makeMenuItems();
	}

	@Override
	protected void makeMenuItems() {
		ItemStack farming, fishing, lumberjack, mining, exitButton;
		ItemTypes type = ItemTypes.MENU;
		ItemGenerator itemGen = PLUGIN.getItemGen();

		farming = itemGen.generateItem("profession_farming", type);
		fishing = itemGen.generateItem("profession_fishing", type);
		lumberjack = itemGen.generateItem("profession_lumberjack", type);
		mining = itemGen.generateItem("profession_mining", type);
		exitButton = itemGen.generateItem("exit_button", type);
		
		setItem(farming, 0, new FeatureComingSoon());
		setItem(fishing, 1, new FeatureComingSoon());
		setItem(lumberjack, 2, new FeatureComingSoon());
		setItem(mining, 3, new FeatureComingSoon());
		setItem(exitButton, 8, new Exit(PLUGIN));
	}
}
