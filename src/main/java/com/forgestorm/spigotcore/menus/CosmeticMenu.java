package com.forgestorm.spigotcore.menus;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.FeatureComingSoon;
import com.forgestorm.spigotcore.menus.actions.RunPlayerCommand;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.inventory.ItemStack;

public class CosmeticMenu extends Menu {

    private final SpigotCore plugin;

    public CosmeticMenu(SpigotCore plugin) {
        super(plugin);
        this.plugin = plugin;
        init("Cosmetic Menu", 2);
        makeMenuItems();
    }

	@Override
	protected void makeMenuItems() {
		ItemTypes type = ItemTypes.MENU;
        ItemGenerator itemGen = plugin.getItemGen();

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

        setItem(emotes, 0, new RunPlayerCommand(plugin, "uc menu emotes", 0));
        setItem(gadgets, 1, new RunPlayerCommand(plugin, "uc menu gadgets", 0));
        setItem(hats, 2, new RunPlayerCommand(plugin, "uc menu hats", 0));
        setItem(morphs, 3, new RunPlayerCommand(plugin, "uc menu morphs", 0));
        setItem(mounts, 4, new RunPlayerCommand(plugin, "uc menu mounts", 0));
        setItem(effects, 5, new RunPlayerCommand(plugin, "uc menu particleeffects", 0));
        setItem(pets, 6, new RunPlayerCommand(plugin, "uc menu pets", 0));
        setItem(stickers, 7, new FeatureComingSoon());
        setItem(suits, 8, new RunPlayerCommand(plugin, "uc menu suits", 0));
        setItem(titles, 9, new FeatureComingSoon());
        setItem(win, 10, new FeatureComingSoon());
        setItem(backButton, 16, MainMenu.class);
        setItem(exitButton, 17, new Exit(plugin));
    }
}
