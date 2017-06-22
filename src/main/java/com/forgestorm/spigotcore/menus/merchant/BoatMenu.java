package com.forgestorm.spigotcore.menus.merchant;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.PurchaseItemStack;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.inventory.ItemStack;

public class BoatMenu extends Menu {

    private final SpigotCore plugin;

    public BoatMenu(SpigotCore plugin) {
        super(plugin);
        this.plugin = plugin;
        init("Boat Merchant", 1);
        makeMenuItems();
    }

	@Override
	protected void makeMenuItems() {
		ItemStack boat, boatItem, exitButton;
		ItemTypes type = ItemTypes.MENU;
        ItemGenerator itemGen = plugin.getItemGen();

		boat = itemGen.generateItem("boat_buy", type);
		boatItem = itemGen.generateItem("boat_item", type);
		exitButton = itemGen.generateItem("exit_button", type);

        setItem(boat, 0, new PurchaseItemStack(plugin, boatItem, 100));
        setItem(exitButton, 8, new Exit(plugin));
    }
}
