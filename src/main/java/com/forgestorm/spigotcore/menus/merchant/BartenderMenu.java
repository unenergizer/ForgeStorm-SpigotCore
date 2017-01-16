package com.forgestorm.spigotcore.menus.merchant;

import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.PurchaseItemStack;
import com.forgestorm.spigotcore.util.item.ItemGenerator;

public class BartenderMenu extends Menu {
	
	private final SpigotCore PLUGIN;
	
	public BartenderMenu(SpigotCore plugin) {
		super(plugin);
		PLUGIN = plugin;
		init("ForgeStorm Links", 1);
		makeMenuItems();
	}

	@Override
	protected void makeMenuItems() {
		ItemStack brewMenuItem, brewPlayerItem, exitButton;
		ItemTypes type = ItemTypes.MENU;
		ItemGenerator itemGen = PLUGIN.getItemGen();

		brewMenuItem = itemGen.generateItem("bartender_drink_item_buy", type);
		brewPlayerItem = itemGen.generateItem("bartender_drink_item", type);
		exitButton = itemGen.generateItem("exit_button", type);
		
		setItem(brewMenuItem, 0, new PurchaseItemStack(PLUGIN, brewPlayerItem, 100));
		setItem(exitButton, 8, new Exit(PLUGIN));
	}
}
