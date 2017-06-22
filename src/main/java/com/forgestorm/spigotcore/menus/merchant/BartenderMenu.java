package com.forgestorm.spigotcore.menus.merchant;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.PurchaseItemStack;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.inventory.ItemStack;

public class BartenderMenu extends Menu {

    private final SpigotCore plugin;

    public BartenderMenu(SpigotCore plugin) {
        super(plugin);
        this.plugin = plugin;
        init("Bartender Menu", 1);
        makeMenuItems();
    }

	@Override
	protected void makeMenuItems() {
		ItemStack brewMenuItem, brewPlayerItem, exitButton;
		ItemTypes type = ItemTypes.MENU;
        ItemGenerator itemGen = plugin.getItemGen();

		brewMenuItem = itemGen.generateItem("bartender_drink_item_buy", type);
		brewPlayerItem = itemGen.generateItem("bartender_drink_item", type);
		exitButton = itemGen.generateItem("exit_button", type);

        setItem(brewMenuItem, 0, new PurchaseItemStack(plugin, brewPlayerItem, 100));
        setItem(exitButton, 8, new Exit(plugin));
    }
}
