package com.forgestorm.spigotcore.menus.merchant;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.PurchaseItemStack;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MerchantFarmingMenu extends Menu {

    private final SpigotCore plugin;

    public MerchantFarmingMenu(SpigotCore plugin) {
        super(plugin);
        this.plugin = plugin;
        init("Farming Merchant", 1);
        makeMenuItems();
    }

    @Override
    protected void makeMenuItems() {
        ItemStack woodPick, stonePick, ironPick, diamondPick, goldPick, exitButton;
        ItemTypes type = ItemTypes.MENU;
        ItemGenerator itemGen = plugin.getItemGen();

        // Menu items
        woodPick = itemGen.generateItem("item_merchant_farming_wood", type);
        stonePick = itemGen.generateItem("item_merchant_farming_stone", type);
        ironPick = itemGen.generateItem("item_merchant_farming_iron", type);
        diamondPick = itemGen.generateItem("item_merchant_farming_diamond", type);
        goldPick = itemGen.generateItem("item_merchant_farming_gold", type);

        exitButton = itemGen.generateItem("exit_button", type);


        // Set items in menu
        setItem(woodPick, 0, new PurchaseItemStack(plugin, new ItemStack(Material.WOOD_HOE), 20));
        setItem(stonePick, 1, new PurchaseItemStack(plugin, new ItemStack(Material.STONE_HOE), 100));
        setItem(ironPick, 2, new PurchaseItemStack(plugin, new ItemStack(Material.IRON_HOE), 200));
        setItem(diamondPick, 3, new PurchaseItemStack(plugin, new ItemStack(Material.DIAMOND_HOE), 350));
        setItem(goldPick, 4, new PurchaseItemStack(plugin, new ItemStack(Material.GOLD_HOE), 400));
        setItem(exitButton, 8, new Exit(plugin));
    }
}
