package com.forgestorm.spigotcore.menus.merchant;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.PurchaseItemStack;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.inventory.ItemStack;

public class MerchantMiningMenu extends Menu {

    private final SpigotCore plugin;

    public MerchantMiningMenu(SpigotCore plugin) {
        super(plugin);
        this.plugin = plugin;
        init("Mining Merchant", 1);
        makeMenuItems();
    }

    @Override
    protected void makeMenuItems() {
        ItemStack woodPick, stonePick, ironPick, diamondPick, goldPick, exitButton;
        ItemStack woodPickItem, stonePickItem, ironPickItem, diamondPickItem, goldPickItem;
        ItemTypes type = ItemTypes.MENU;
        ItemGenerator itemGen = plugin.getItemGen();

        // Menu items
        woodPick = itemGen.generateItem("item_merchant_mining_wood", type);
        stonePick = itemGen.generateItem("item_merchant_mining_stone", type);
        ironPick = itemGen.generateItem("item_merchant_mining_iron", type);
        diamondPick = itemGen.generateItem("item_merchant_mining_diamond", type);
        goldPick = itemGen.generateItem("item_merchant_mining_gold", type);

        exitButton = itemGen.generateItem("exit_button", type);

        // Purchased items
        woodPickItem = itemGen.generateItem("item_merchant_mining_wood_item", type);
        stonePickItem = itemGen.generateItem("item_merchant_mining_stone_item", type);
        ironPickItem = itemGen.generateItem("item_merchant_mining_iron_item", type);
        diamondPickItem = itemGen.generateItem("item_merchant_mining_diamond_item", type);
        goldPickItem = itemGen.generateItem("item_merchant_mining_gold_item", type);


        // Set items in menu
        setItem(woodPick, 0, new PurchaseItemStack(plugin, woodPickItem, 20));
        setItem(stonePick, 1, new PurchaseItemStack(plugin, stonePickItem, 100));
        setItem(ironPick, 2, new PurchaseItemStack(plugin, ironPickItem, 200));
        setItem(diamondPick, 3, new PurchaseItemStack(plugin, diamondPickItem, 350));
        setItem(goldPick, 4, new PurchaseItemStack(plugin, goldPickItem, 400));
        setItem(exitButton, 8, new Exit(plugin));
    }
}
