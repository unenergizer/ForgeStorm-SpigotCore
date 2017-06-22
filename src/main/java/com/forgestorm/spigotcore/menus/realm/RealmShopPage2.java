package com.forgestorm.spigotcore.menus.realm;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.PurchaseItemStack;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RealmShopPage2 extends Menu {

    private final SpigotCore plugin;

    public RealmShopPage2(SpigotCore plugin) {
        super(plugin);
        this.plugin = plugin;
        init("Realm Shop: Page 2", 6);
        makeMenuItems();
    }

    @Override
    protected void makeMenuItems() {
        ItemTypes type = ItemTypes.MENU;
        ItemGenerator itemGen = plugin.getItemGen();

        // Row 1
        ItemStack smoothBrick = new ItemStack(Material.SMOOTH_BRICK);
        ItemStack sandStone = new ItemStack(Material.SANDSTONE);
        ItemStack redSandStone = new ItemStack(Material.RED_SANDSTONE);
        ItemStack quartz = new ItemStack(Material.QUARTZ_BLOCK);
        ItemStack purpur = new ItemStack(Material.PURPUR_BLOCK);
        ItemStack obsidian = new ItemStack(Material.OBSIDIAN);
        ItemStack grass = new ItemStack(Material.GRASS);
        ItemStack granite = new ItemStack(Material.STONE, 1, (short) 0, (byte) 1);
        ItemStack diorite = new ItemStack(Material.STONE, 1, (short) 0, (byte) 3);

        setBuyItem(smoothBrick, 0, new PurchaseItemStack(plugin, smoothBrick, 20), 20);
        setBuyItem(sandStone, 1, new PurchaseItemStack(plugin, sandStone, 20), 20);
        setBuyItem(redSandStone, 2, new PurchaseItemStack(plugin, redSandStone, 20), 20);
        setBuyItem(quartz, 3, new PurchaseItemStack(plugin, quartz, 20), 20);
        setBuyItem(purpur, 4, new PurchaseItemStack(plugin, purpur, 20), 20);
        setBuyItem(obsidian, 5, new PurchaseItemStack(plugin, obsidian, 20), 20);
        setBuyItem(grass, 6, new PurchaseItemStack(plugin, grass, 20), 20);
        setBuyItem(granite, 7, new PurchaseItemStack(plugin, granite, 20), 20);
        setBuyItem(diorite, 8, new PurchaseItemStack(plugin, diorite, 20), 20);

        // Row 2
        ItemStack plank0 = new ItemStack(Material.SMOOTH_BRICK, 1, (short) 0, (byte) 2);
        ItemStack plank1 = new ItemStack(Material.SANDSTONE, 1, (short) 0, (byte) 2);
        ItemStack plank2 = new ItemStack(Material.RED_SANDSTONE, 1, (short) 0, (byte) 2);
        ItemStack plank3 = new ItemStack(Material.QUARTZ_BLOCK, 1, (short) 0, (byte) 2);
        ItemStack plank2_0 = new ItemStack(Material.PURPUR_PILLAR); //TODO: Get Material
        ItemStack plank2_1 = new ItemStack(Material.BRICK); //TODO: Get Material
        ItemStack netherBrick = new ItemStack(Material.DIRT);
        ItemStack cobble = new ItemStack(Material.STONE, 1, (short) 0, (byte) 2);
        ItemStack mossyCobble = new ItemStack(Material.STONE, 1, (short) 0, (byte) 4);

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
        ItemStack stair0 = new ItemStack(Material.SMOOTH_STAIRS);
        ItemStack stair1 = new ItemStack(Material.SANDSTONE_STAIRS);
        ItemStack stair2 = new ItemStack(Material.RED_SANDSTONE_STAIRS);
        ItemStack stair3 = new ItemStack(Material.QUARTZ_STAIRS);
        ItemStack stair2_0 = new ItemStack(Material.PURPUR_STAIRS);
        ItemStack stair2_1 = new ItemStack(Material.BRICK_STAIRS);
        ItemStack netherStairs = new ItemStack(Material.DIRT, 1, (short) 0, (byte) 1);
        ItemStack cobbleStairs = new ItemStack(Material.MYCEL);
        ItemStack mossyBrick = new ItemStack(Material.STONE, 1, (short) 0, (byte) 5);

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
        ItemStack slab0 = new ItemStack(Material.STEP, 1, (short) 0, (byte) 5);
        ItemStack slab1 = new ItemStack(Material.STEP, 1, (short) 0, (byte) 1);
        ItemStack slab2 = new ItemStack(Material.STONE_SLAB2);
        ItemStack slab3 = new ItemStack(Material.STEP, 1, (short) 0, (byte) 7);
        ItemStack slab2_0 = new ItemStack(Material.PURPUR_SLAB);
        ItemStack slab2_1 = new ItemStack(Material.STEP, 1, (short) 0, (byte) 4);
        ItemStack netherSlab = new ItemStack(Material.DIRT, 1, (short) 0, (byte) 2);
        ItemStack cobbleSlab = new ItemStack(Material.SMOOTH_BRICK, 1, (short) 0, (byte) 1);
        ItemStack stoneSlab = new ItemStack(Material.STONE, 1, (short) 0, (byte) 6);

        setBuyItem(slab0, 27, new PurchaseItemStack(plugin, slab0, 20), 20);
        setBuyItem(slab1, 28, new PurchaseItemStack(plugin, slab1, 20), 20);
        setBuyItem(slab2, 29, new PurchaseItemStack(plugin, slab2, 20), 20);
        setBuyItem(slab3, 30, new PurchaseItemStack(plugin, slab3, 20), 20);
        setBuyItem(slab2_0, 31, new PurchaseItemStack(plugin, slab2_0, 20), 20);
        setBuyItem(slab2_1, 32, new PurchaseItemStack(plugin, slab2_1, 20), 20);
        setBuyItem(netherSlab, 33, new PurchaseItemStack(plugin, netherSlab, 20), 20);
        setBuyItem(cobbleSlab, 34, new PurchaseItemStack(plugin, cobbleSlab, 20), 20);
        setBuyItem(stoneSlab, 35, new PurchaseItemStack(plugin, stoneSlab, 20), 20);

        // Row 5
        ItemStack fence0 = new ItemStack(Material.SMOOTH_BRICK, 1, (short) 0, (byte) 3);
        ItemStack fence1 = new ItemStack(Material.SANDSTONE, 1, (short) 0, (byte) 1);
        ItemStack fence2 = new ItemStack(Material.RED_SANDSTONE, 1, (short) 0, (byte) 1);
        ItemStack fence3 = new ItemStack(Material.QUARTZ_BLOCK, 1, (short) 0, (byte) 1);
        ItemStack fence2_0 = new ItemStack(Material.SAND, 1, (short) 0, (byte) 1);
        ItemStack fence2_1 = new ItemStack(Material.SAND);
        ItemStack netherFence = new ItemStack(Material.SOUL_SAND);
        ItemStack cobbleWall = new ItemStack(Material.ENDER_STONE);
        ItemStack mossyWall = new ItemStack(Material.END_BRICKS);

        setBuyItem(fence0, 36, new PurchaseItemStack(plugin, fence0, 20), 20);
        setBuyItem(fence1, 37, new PurchaseItemStack(plugin, fence1, 20), 20);
        setBuyItem(fence2, 38, new PurchaseItemStack(plugin, fence2, 20), 20);
        setBuyItem(fence3, 39, new PurchaseItemStack(plugin, fence3, 20), 20);
        setBuyItem(fence2_0, 40, new PurchaseItemStack(plugin, fence2_0, 20), 20);
        setBuyItem(fence2_1, 41, new PurchaseItemStack(plugin, fence2_1, 20), 20);
        setBuyItem(netherFence, 42, new PurchaseItemStack(plugin, netherFence, 20), 20);
        setBuyItem(cobbleWall, 43, new PurchaseItemStack(plugin, cobbleWall, 20), 20);
        setBuyItem(mossyWall, 44, new PurchaseItemStack(plugin, mossyWall, 20), 20);

        // Row 6
        ItemStack page1 = new ItemStack(Material.PAPER, 1);
        //ItemStack page2 = new ItemStack(Material.PAPER, 2);
        ItemStack page3 = new ItemStack(Material.PAPER, 3);
        ItemStack page4 = new ItemStack(Material.PAPER, 4);
        ItemStack page5 = new ItemStack(Material.PAPER, 5);
        ItemStack page6 = new ItemStack(Material.PAPER, 6);
        ItemStack page7 = new ItemStack(Material.PAPER, 7);
        ItemStack page8 = new ItemStack(Material.PAPER, 8);
        ItemStack exitButton = itemGen.generateItem("exit_button", type);
        ItemStack currentPage = new ItemStack(Material.EMPTY_MAP, 2);

        setPageItem(page1, 45, RealmShopPage1.class, 1);
        setPageItem(currentPage, 46, 2);
        setPageItem(page3, 47, RealmShopPage3.class, 3);
        setPageItem(page4, 48, RealmShopPage4.class, 4);
        setPageItem(page5, 49, RealmShopPage5.class, 5);
        setPageItem(page6, 50, RealmShopPage6.class, 6);
        setPageItem(page7, 51, RealmShopPage7.class, 7);
        setPageItem(page8, 52, RealmShopPage8.class, 8);
        setItem(exitButton, 53, new Exit(plugin));
    }
}
