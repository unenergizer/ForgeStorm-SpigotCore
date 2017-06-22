package com.forgestorm.spigotcore.menus.merchant;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.PurchaseItemStack;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MerchantWoodCuttingMenu extends Menu {

    private final SpigotCore plugin;

    public MerchantWoodCuttingMenu(SpigotCore plugin) {
        super(plugin);
        this.plugin = plugin;
        init("Wood Cutting Merchant", 1);
        makeMenuItems();
    }

    @Override
    protected void makeMenuItems() {
        ItemStack woodAxe, stoneAxe, ironAxe, diamondAxe, goldAxe, exitButton;
        ItemTypes type = ItemTypes.MENU;
        ItemGenerator itemGen = plugin.getItemGen();

        // Menu items
        woodAxe = itemGen.generateItem("item_merchant_wood_cutting_wood", type);
        stoneAxe = itemGen.generateItem("item_merchant_wood_cutting_stone", type);
        ironAxe = itemGen.generateItem("item_merchant_wood_cutting_iron", type);
        diamondAxe = itemGen.generateItem("item_merchant_wood_cutting_diamond", type);
        goldAxe = itemGen.generateItem("item_merchant_wood_cutting_gold", type);

        exitButton = itemGen.generateItem("exit_button", type);


        // Set items in menu
        setItem(woodAxe, 0, new PurchaseItemStack(plugin, new ItemStack(Material.WOOD_AXE), 20));
        setItem(stoneAxe, 1, new PurchaseItemStack(plugin, new ItemStack(Material.STONE_AXE), 100));
        setItem(ironAxe, 2, new PurchaseItemStack(plugin, new ItemStack(Material.IRON_AXE), 200));
        setItem(diamondAxe, 3, new PurchaseItemStack(plugin, new ItemStack(Material.DIAMOND_AXE), 350));
        setItem(goldAxe, 4, new PurchaseItemStack(plugin, new ItemStack(Material.GOLD_AXE), 400));
        setItem(exitButton, 8, new Exit(plugin));
    }
}
