package com.forgestorm.spigotcore.menus.merchant;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.PurchaseItemStack;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MerchantFishingMenu extends Menu {

    private final SpigotCore plugin;

    public MerchantFishingMenu(SpigotCore plugin) {
        super(plugin);
        this.plugin = plugin;
        init("Fishing Merchant", 1);
        makeMenuItems();
    }

    @Override
    protected void makeMenuItems() {
        ItemStack woodPick, exitButton;
        ItemTypes type = ItemTypes.MENU;
        ItemGenerator itemGen = plugin.getItemGen();

        // Menu items
        woodPick = itemGen.generateItem("item_merchant_fishing_rod", type);
        exitButton = itemGen.generateItem("exit_button", type);


        // Set items in menu
        setItem(woodPick, 0, new PurchaseItemStack(plugin, new ItemStack(Material.FISHING_ROD), 20));
        setItem(exitButton, 8, new Exit(plugin));
    }
}
