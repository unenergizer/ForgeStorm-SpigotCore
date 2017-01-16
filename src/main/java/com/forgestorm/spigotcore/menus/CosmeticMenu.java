package com.forgestorm.spigotcore.menus;

import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.FeatureComingSoon;
import com.forgestorm.spigotcore.util.item.ItemGenerator;

public class CosmeticMenu extends Menu {
	
	private final SpigotCore PLUGIN;
	
	public CosmeticMenu(SpigotCore plugin) {
		super(plugin);
		PLUGIN = plugin;
		init("Cosmetic Menu", 2);
		makeMenuItems();
	}

	@Override
	protected void makeMenuItems() {
		ItemTypes type = ItemTypes.MENU;
		ItemGenerator itemGen = PLUGIN.getItemGen();

		ItemStack emotes, gadgets, hats, morphs, mounts, effects, pets, 
			stickers, suits, titles, win, backButton, exitButton;

		emotes = itemGen.generateItem("cosmetic_menu_emotes", type);
		gadgets = itemGen.generateItem("cosmetic_menu_gadgets", type);
		hats = itemGen.generateItem("cosmetic_menu_hats", type);
		morphs = itemGen.generateItem("cosmetic_menu_morphs", type);
		mounts = itemGen.generateItem("cosmetic_menu_mounts", type);
		effects = itemGen.generateItem("cosmetic_menu_effects", type);
		pets = itemGen.generateItem("cosmetic_menu_pets", type);
		stickers = itemGen.generateItem("cosmetic_menu_stickers", type);
		suits = itemGen.generateItem("cosmetic_menu_suits", type);
		titles = itemGen.generateItem("cosmetic_menu_titles", type);
		win = itemGen.generateItem("cosmetic_menu_winscreen", type);
		backButton = itemGen.generateItem("back_button", type);
		exitButton = itemGen.generateItem("exit_button", type);

		setItem(emotes, 0, new FeatureComingSoon());
		setItem(gadgets, 1, new FeatureComingSoon());
		setItem(hats, 2, new FeatureComingSoon());
		setItem(morphs, 3, new FeatureComingSoon());
		setItem(mounts, 4, new FeatureComingSoon());
		setItem(effects, 5, new FeatureComingSoon());
		setItem(pets, 6, new FeatureComingSoon());
		setItem(stickers, 7, new FeatureComingSoon());
		setItem(suits, 8, new FeatureComingSoon());
		setItem(titles, 9, new FeatureComingSoon());
		setItem(win, 10, new FeatureComingSoon());
		setItem(backButton, 16, MainMenu.class);
		setItem(exitButton, 17, new Exit(PLUGIN));
	}
}
