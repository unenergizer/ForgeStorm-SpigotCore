package com.forgestorm.spigotcore.menus.realm;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.PurchaseItemStack;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RealmShopPage4 extends Menu {

    private final SpigotCore plugin;

    public RealmShopPage4(SpigotCore plugin) {
        super(plugin);
        this.plugin = plugin;
        init("Realm Shop: Page 4", 6);
        makeMenuItems();
    }

    @Override
    protected void makeMenuItems() {
        ItemTypes type = ItemTypes.MENU;
        ItemGenerator itemGen = plugin.getItemGen();

        // Row 1
        ItemStack log0 = new ItemStack(Material.REDSTONE_BLOCK);
        ItemStack log1 = new ItemStack(Material.REDSTONE);
        ItemStack log2 = new ItemStack(Material.REDSTONE_TORCH_ON);
        ItemStack log3 = new ItemStack(Material.LEVER);
        ItemStack log2_0 = new ItemStack(Material.DAYLIGHT_DETECTOR);
        ItemStack log2_1 = new ItemStack(Material.STONE_PLATE);
        ItemStack netherrack = new ItemStack(Material.WOOD_PLATE);
        ItemStack stone = new ItemStack(Material.GOLD_PLATE);
        ItemStack bedrock = new ItemStack(Material.IRON_PLATE);

        setBuyItem(log0, 0, new PurchaseItemStack(plugin, log0, 20), 20);
        setBuyItem(log1, 1, new PurchaseItemStack(plugin, log1, 20), 20);
        setBuyItem(log2, 2, new PurchaseItemStack(plugin, log2, 20), 20);
        setBuyItem(log3, 3, new PurchaseItemStack(plugin, log3, 20), 20);
        setBuyItem(log2_0, 4, new PurchaseItemStack(plugin, log2_0, 20), 20);
        setBuyItem(log2_1, 5, new PurchaseItemStack(plugin, log2_1, 20), 20);
        setBuyItem(netherrack, 6, new PurchaseItemStack(plugin, netherrack, 20), 20);
        setBuyItem(stone, 7, new PurchaseItemStack(plugin, stone, 20), 20);
        setBuyItem(bedrock, 8, new PurchaseItemStack(plugin, bedrock, 20), 20);

        // Row 2
        ItemStack plank0 = new ItemStack(Material.WOOD_BUTTON);
        ItemStack plank1 = new ItemStack(Material.STONE_BUTTON);
        ItemStack plank2 = new ItemStack(Material.DIODE);
        ItemStack plank3 = new ItemStack(Material.REDSTONE_LAMP_OFF);
        ItemStack plank2_0 = new ItemStack(Material.PISTON_STICKY_BASE, 1, (short) 0, (byte) 1);
        ItemStack plank2_1 = new ItemStack(Material.PISTON_BASE, 1, (short) 0, (byte) 1);
        ItemStack netherBrick = new ItemStack(Material.NOTE_BLOCK);
        ItemStack cobble = new ItemStack(Material.FENCE_GATE);
        ItemStack mossyCobble = new ItemStack(Material.SPRUCE_FENCE_GATE);

        setBuyItem(plank0, 9, new PurchaseItemStack(plugin, plank0, 20), 20);
        setBuyItem(plank1, 10, new PurchaseItemStack(plugin, plank1, 20), 20);
        setBuyItem(plank2, 11, new PurchaseItemStack(plugin, plank2, 20), 20);
        setBuyItem(plank3, 12, new PurchaseItemStack(plugin, plank3, 20), 20);
        setBuyItem(plank2_0, 13, new PurchaseItemStack(plugin, plank2_0, 20), 20);
        setBuyItem(plank2_1, 14, new PurchaseItemStack(plugin, plank2_1, 20), 20);
        setBuyItem(netherBrick, 15, new PurchaseItemStack(plugin, netherBrick, 20), 20);
        setBuyItem(cobble, 16, new PurchaseItemStack(plugin, cobble, 20), 20);
        setBuyItem(mossyCobble, 17, new PurchaseItemStack(plugin, mossyCobble, 20), 20);

        // Row 3
        ItemStack stair0 = new ItemStack(Material.BIRCH_FENCE_GATE);
        ItemStack stair1 = new ItemStack(Material.JUNGLE_FENCE_GATE);
        ItemStack stair2 = new ItemStack(Material.DARK_OAK_FENCE_GATE);
        ItemStack stair3 = new ItemStack(Material.ACACIA_FENCE_GATE);
        ItemStack stair2_0 = new ItemStack(Material.WOOD_DOOR);
        ItemStack stair2_1 = new ItemStack(Material.IRON_DOOR);
        ItemStack netherStairs = new ItemStack(Material.SPRUCE_DOOR_ITEM);
        ItemStack cobbleStairs = new ItemStack(Material.BIRCH_DOOR_ITEM);
        ItemStack mossyBrick = new ItemStack(Material.JUNGLE_DOOR_ITEM);

        setBuyItem(stair0, 18, new PurchaseItemStack(plugin, stair0, 20), 20);
        setBuyItem(stair1, 19, new PurchaseItemStack(plugin, stair1, 20), 20);
        setBuyItem(stair2, 20, new PurchaseItemStack(plugin, stair2, 20), 20);
        setBuyItem(stair3, 21, new PurchaseItemStack(plugin, stair3, 20), 20);
        setBuyItem(stair2_0, 22, new PurchaseItemStack(plugin, stair2_0, 20), 20);
        setBuyItem(stair2_1, 23, new PurchaseItemStack(plugin, stair2_1, 20), 20);
        setBuyItem(netherStairs, 24, new PurchaseItemStack(plugin, netherStairs, 20), 20);
        setBuyItem(cobbleStairs, 25, new PurchaseItemStack(plugin, cobbleStairs, 20), 20);
        setBuyItem(mossyBrick, 26, new PurchaseItemStack(plugin, mossyBrick, 20), 20);

        // Row 4
        ItemStack slab0 = new ItemStack(Material.ACACIA_DOOR_ITEM);
        ItemStack slab1 = new ItemStack(Material.DARK_OAK_DOOR_ITEM);
        ItemStack slab2 = new ItemStack(Material.TRAP_DOOR);
        ItemStack slab3 = new ItemStack(Material.IRON_TRAPDOOR);
        ItemStack slab2_0 = new ItemStack(Material.POWERED_RAIL);
        ItemStack slab2_1 = new ItemStack(Material.DETECTOR_RAIL);
        ItemStack netherSlab = new ItemStack(Material.RAILS);
        ItemStack cobbleSlab = new ItemStack(Material.ACTIVATOR_RAIL);
        ItemStack stoneSlab = new ItemStack(Material.MINECART);

        setBuyItem(slab0, 27, new PurchaseItemStack(plugin, slab0, 20), 20);
        setBuyItem(slab1, 28, new PurchaseItemStack(plugin, slab1, 20), 20);
        setBuyItem(slab2, 29, new PurchaseItemStack(plugin, slab2, 20), 20);
        setBuyItem(slab3, 30, new PurchaseItemStack(plugin, slab3, 20), 20);
        setBuyItem(slab2_0, 31, new PurchaseItemStack(plugin, slab2_0, 20), 20);
        setBuyItem(slab2_1, 32, new PurchaseItemStack(plugin, slab2_1, 20), 20);
        setBuyItem(netherSlab, 33, new PurchaseItemStack(plugin, netherSlab, 20), 20);
        setBuyItem(cobbleSlab, 34, new PurchaseItemStack(plugin, cobbleSlab, 20), 20);
        setBuyItem(stoneSlab, 35, new PurchaseItemStack(plugin, stoneSlab, 20), 20);

        // Row 6
        ItemStack page1 = new ItemStack(Material.PAPER, 1);
        ItemStack page2 = new ItemStack(Material.PAPER, 2);
        ItemStack page3 = new ItemStack(Material.PAPER, 3);
        //ItemStack page4 = new ItemStack(Material.PAPER, 4);
        ItemStack page5 = new ItemStack(Material.PAPER, 5);
        ItemStack page6 = new ItemStack(Material.PAPER, 6);
        ItemStack page7 = new ItemStack(Material.PAPER, 7);
        ItemStack page8 = new ItemStack(Material.PAPER, 8);
        ItemStack exitButton = itemGen.generateItem("exit_button", type);
        ItemStack currentPage = new ItemStack(Material.EMPTY_MAP, 4);

        setPageItem(page1, 45, RealmShopPage1.class, 1);
        setPageItem(page2, 46, RealmShopPage2.class, 2);
        setPageItem(page3, 47, RealmShopPage3.class, 3);
        setPageItem(currentPage, 48, 4);
        setPageItem(page5, 49, RealmShopPage5.class, 5);
        setPageItem(page6, 50, RealmShopPage6.class, 6);
        setPageItem(page7, 51, RealmShopPage7.class, 7);
        setPageItem(page8, 52, RealmShopPage8.class, 8);
        setItem(exitButton, 53, new Exit(plugin));
    }
}
