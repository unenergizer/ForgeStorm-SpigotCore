package com.forgestorm.spigotcore.menus.realm;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.PurchaseItemStack;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RealmShopPage3 extends Menu {

    private final SpigotCore plugin;

    public RealmShopPage3(SpigotCore plugin) {
        super(plugin);
        this.plugin = plugin;
        init("Realm Shop: Page 3", 6);
        makeMenuItems();
    }

    @Override
    protected void makeMenuItems() {
        ItemTypes type = ItemTypes.MENU;
        ItemGenerator itemGen = plugin.getItemGen();

        // Row 1
        ItemStack log0 = new ItemStack(Material.TORCH);
        ItemStack log1 = new ItemStack(Material.GLOWSTONE);
        ItemStack log2 = new ItemStack(Material.SNOW_BLOCK);
        ItemStack log3 = new ItemStack(Material.PACKED_ICE);
        ItemStack log2_0 = new ItemStack(Material.ICE);
        ItemStack log2_1 = new ItemStack(Material.RED_NETHER_BRICK);
        ItemStack netherrack = new ItemStack(Material.NETHER_WART_BLOCK);
        ItemStack stone = new ItemStack(Material.MAGMA);
        ItemStack bedrock = new ItemStack(Material.QUARTZ_ORE);

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
        ItemStack plank0 = new ItemStack(Material.LAPIS_BLOCK);
        ItemStack plank1 = new ItemStack(Material.EMERALD_BLOCK);
        ItemStack plank2 = new ItemStack(Material.DIAMOND_BLOCK);
        ItemStack plank3 = new ItemStack(Material.IRON_BLOCK);
        ItemStack plank2_0 = new ItemStack(Material.GOLD_BLOCK);
        ItemStack plank2_1 = new ItemStack(Material.PRISMARINE, 1, (short) 0, (byte) 1);
        ItemStack netherBrick = new ItemStack(Material.PRISMARINE, 1, (short) 0, (byte) 2);
        ItemStack cobble = new ItemStack(Material.PRISMARINE);
        ItemStack mossyCobble = new ItemStack(Material.BONE_BLOCK);

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
        ItemStack stair0 = new ItemStack(Material.HAY_BLOCK);
        ItemStack stair1 = new ItemStack(Material.BOOKSHELF);
        ItemStack stair2 = new ItemStack(Material.PUMPKIN);
        ItemStack stair3 = new ItemStack(Material.LADDER);
        ItemStack stair2_0 = new ItemStack(Material.IRON_FENCE);
        ItemStack stair2_1 = new ItemStack(Material.SPONGE, 1, (short) 0, (byte) 1);
        ItemStack netherStairs = new ItemStack(Material.SPONGE);
        ItemStack cobbleStairs = new ItemStack(Material.BREWING_STAND_ITEM);
        ItemStack mossyBrick = new ItemStack(Material.CAULDRON_ITEM);

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
        ItemStack slab0 = new ItemStack(Material.SIGN);
        ItemStack slab1 = new ItemStack(Material.PAINTING);
        ItemStack slab2 = new ItemStack(Material.ITEM_FRAME);
        ItemStack slab3 = new ItemStack(Material.ARMOR_STAND);
        ItemStack slab2_0 = new ItemStack(Material.END_ROD);
        ItemStack slab2_1 = new ItemStack(Material.SLIME_BLOCK);

        setBuyItem(slab0, 27, new PurchaseItemStack(plugin, slab0, 20), 20);
        setBuyItem(slab1, 28, new PurchaseItemStack(plugin, slab1, 20), 20);
        setBuyItem(slab2, 29, new PurchaseItemStack(plugin, slab2, 20), 20);
        setBuyItem(slab3, 30, new PurchaseItemStack(plugin, slab3, 20), 20);
        setBuyItem(slab2_0, 31, new PurchaseItemStack(plugin, slab2_0, 20), 20);
        setBuyItem(slab2_1, 32, new PurchaseItemStack(plugin, slab2_1, 20), 20);

        // Row 6
        ItemStack page1 = new ItemStack(Material.PAPER, 1);
        ItemStack page2 = new ItemStack(Material.PAPER, 2);
        //ItemStack page3 = new ItemStack(Material.PAPER, 3);
        ItemStack page4 = new ItemStack(Material.PAPER, 4);
        ItemStack page5 = new ItemStack(Material.PAPER, 5);
        ItemStack page6 = new ItemStack(Material.PAPER, 6);
        ItemStack page7 = new ItemStack(Material.PAPER, 7);
        ItemStack page8 = new ItemStack(Material.PAPER, 8);
        ItemStack exitButton = itemGen.generateItem("exit_button", type);
        ItemStack currentPage = new ItemStack(Material.EMPTY_MAP, 3);

        setPageItem(page1, 45, RealmShopPage1.class, 1);
        setPageItem(page2, 46, RealmShopPage2.class, 2);
        setPageItem(currentPage, 47, 3);
        setPageItem(page4, 48, RealmShopPage4.class, 4);
        setPageItem(page5, 49, RealmShopPage5.class, 5);
        setPageItem(page6, 50, RealmShopPage6.class, 6);
        setPageItem(page7, 51, RealmShopPage7.class, 7);
        setPageItem(page8, 52, RealmShopPage8.class, 8);
        setItem(exitButton, 53, new Exit(plugin));
    }
}
