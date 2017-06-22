package com.forgestorm.spigotcore.menus.merchant;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.PurchaseItemStack;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.inventory.ItemStack;

public class MerchantMenu extends Menu {

    private final SpigotCore plugin;

    public MerchantMenu(SpigotCore plugin) {
        super(plugin);
        this.plugin = plugin;
        init("Item Merchant", 1);
        makeMenuItems();
    }

    @Override
    protected void makeMenuItems() {
        ItemStack peaceOrbMenu, peaceOrbItem, flyingOrbMenu, flyingOrbItem, exitButton;
        ItemTypes type = ItemTypes.MENU;
        ItemGenerator itemGen = plugin.getItemGen();

        peaceOrbMenu = itemGen.generateItem("item_merchant_orb_peace_menu", type);
        peaceOrbItem = itemGen.generateItem("item_merchant_orb_peace_item", type);
        flyingOrbMenu = itemGen.generateItem("item_merchant_orb_of_flight_menu", type);
        flyingOrbItem = itemGen.generateItem("item_merchant_orb_of_flight_item", type);
        exitButton = itemGen.generateItem("exit_button", type);

        setItem(peaceOrbMenu, 0, new PurchaseItemStack(plugin, peaceOrbItem, 200));
        setItem(flyingOrbMenu, 1, new PurchaseItemStack(plugin, flyingOrbItem, 400));
        setItem(exitButton, 8, new Exit(plugin));
    }
}
