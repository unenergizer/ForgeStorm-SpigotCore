package com.forgestorm.spigotcore.menus;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.FeatureComingSoon;
import com.forgestorm.spigotcore.menus.actions.OpenSettingsMenu;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.inventory.ItemStack;

public class PlayerMenu extends Menu {

	private final SpigotCore plugin;

	public PlayerMenu(SpigotCore plugin) {
		super(plugin);
		this.plugin = plugin;
		init("Player Menu", 1);
		makeMenuItems();
	}
	
	@Override
	protected void makeMenuItems() {
		ItemTypes type = ItemTypes.MENU;
		ItemGenerator itemGen = plugin.getItemGen();
		ItemStack achievements, stats, settings, backButton, exitButton;
		
		settings = itemGen.generateItem("player_info_settings", type);
		achievements = itemGen.generateItem("player_info_achievements", type);
		stats = itemGen.generateItem("player_info_stats", type);
		backButton = itemGen.generateItem("back_button", type);
		exitButton = itemGen.generateItem("exit_button", type);

		setItem(settings, 0, new OpenSettingsMenu(plugin));
		setItem(achievements, 1, new FeatureComingSoon());
		setItem(stats, 2, new FeatureComingSoon());
		setItem(backButton, 7, MainMenu.class);
		setItem(exitButton, 8, new Exit(plugin));
	}
}
