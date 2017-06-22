package com.forgestorm.spigotcore.menus.realm;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.PurchaseItemStack;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class RealmShopPage8 extends Menu {

    private final SpigotCore plugin;

    public RealmShopPage8(SpigotCore plugin) {
        super(plugin);
        this.plugin = plugin;
        init("Realm Shop: Page 8", 6);
        makeMenuItems();
    }

    @Override
    protected void makeMenuItems() {
        ItemTypes type = ItemTypes.MENU;
        ItemGenerator itemGen = plugin.getItemGen();

        // Row 1
        ItemStack item00 = new ItemStack(Material.CLAY);
        ItemStack item01 = new ItemStack(Material.STAINED_CLAY, 1, (short) 0, (byte) 0);
        ItemStack item02 = new ItemStack(Material.STAINED_CLAY, 1, (short) 0, (byte) 1);
        ItemStack item03 = new ItemStack(Material.STAINED_CLAY, 1, (short) 0, (byte) 2);
        ItemStack item04 = new ItemStack(Material.STAINED_CLAY, 1, (short) 0, (byte) 3);
        ItemStack item05 = new ItemStack(Material.STAINED_CLAY, 1, (short) 0, (byte) 4);
        ItemStack item06 = new ItemStack(Material.STAINED_CLAY, 1, (short) 0, (byte) 5);
        ItemStack item07 = new ItemStack(Material.STAINED_CLAY, 1, (short) 0, (byte) 6);
        ItemStack item08 = new ItemStack(Material.STAINED_CLAY, 1, (short) 0, (byte) 7);

        setBuyItem(item00, 0, new PurchaseItemStack(plugin, item00, 20), 20);
        setBuyItem(item01, 1, new PurchaseItemStack(plugin, item01, 20), 20);
        setBuyItem(item02, 2, new PurchaseItemStack(plugin, item02, 20), 20);
        setBuyItem(item03, 3, new PurchaseItemStack(plugin, item03, 20), 20);
        setBuyItem(item04, 4, new PurchaseItemStack(plugin, item04, 20), 20);
        setBuyItem(item05, 5, new PurchaseItemStack(plugin, item05, 20), 20);
        setBuyItem(item06, 6, new PurchaseItemStack(plugin, item06, 20), 20);
        setBuyItem(item07, 7, new PurchaseItemStack(plugin, item07, 20), 20);
        setBuyItem(item08, 8, new PurchaseItemStack(plugin, item08, 20), 20);

        // Row 2
        ItemStack item09 = new ItemStack(Material.STAINED_CLAY, 1, (short) 0, (byte) 8);
        ItemStack item10 = new ItemStack(Material.STAINED_CLAY, 1, (short) 0, (byte) 9);
        ItemStack item11 = new ItemStack(Material.STAINED_CLAY, 1, (short) 0, (byte) 10);
        ItemStack item12 = new ItemStack(Material.STAINED_CLAY, 1, (short) 0, (byte) 11);
        ItemStack item13 = new ItemStack(Material.STAINED_CLAY, 1, (short) 0, (byte) 12);
        ItemStack item14 = new ItemStack(Material.STAINED_CLAY, 1, (short) 0, (byte) 13);
        ItemStack item15 = new ItemStack(Material.STAINED_CLAY, 1, (short) 0, (byte) 14);
        ItemStack item16 = new ItemStack(Material.STAINED_CLAY, 1, (short) 0, (byte) 15);
        ItemStack item17 = new ItemStack(Material.HARD_CLAY);

        setBuyItem(item09, 9, new PurchaseItemStack(plugin, item09, 20), 20);
        setBuyItem(item10, 10, new PurchaseItemStack(plugin, item10, 20), 20);
        setBuyItem(item11, 11, new PurchaseItemStack(plugin, item11, 20), 20);
        setBuyItem(item12, 12, new PurchaseItemStack(plugin, item12, 20), 20);
        setBuyItem(item13, 13, new PurchaseItemStack(plugin, item13, 20), 20);
        setBuyItem(item14, 14, new PurchaseItemStack(plugin, item14, 20), 20);
        setBuyItem(item15, 15, new PurchaseItemStack(plugin, item15, 20), 20);
        setBuyItem(item16, 16, new PurchaseItemStack(plugin, item16, 20), 20);
        setBuyItem(item17, 17, new PurchaseItemStack(plugin, item17, 20), 20);

        // Row 3
        ItemStack item18 = new ItemStack(Material.BANNER, 1, (short) 0, (byte) 0);
        ItemStack item19 = new ItemStack(Material.BANNER, 1, (short) 0, (byte) 1);
        ItemStack item20 = new ItemStack(Material.BANNER, 1, (short) 0, (byte) 2);
        ItemStack item21 = new ItemStack(Material.BANNER, 1, (short) 0, (byte) 3);
        ItemStack item22 = new ItemStack(Material.BANNER, 1, (short) 0, (byte) 4);
        ItemStack item23 = new ItemStack(Material.BANNER, 1, (short) 0, (byte) 5);
        ItemStack item24 = new ItemStack(Material.BANNER, 1, (short) 0, (byte) 6);
        ItemStack item25 = new ItemStack(Material.BANNER, 1, (short) 0, (byte) 7);
        ItemStack item26 = new ItemStack(Material.BANNER, 1, (short) 0, (byte) 8);

        setBuyItem(item18, 18, new PurchaseItemStack(plugin, item18, 20), 20);
        setBuyItem(item19, 19, new PurchaseItemStack(plugin, item19, 20), 20);
        setBuyItem(item20, 20, new PurchaseItemStack(plugin, item20, 20), 20);
        setBuyItem(item21, 21, new PurchaseItemStack(plugin, item21, 20), 20);
        setBuyItem(item22, 22, new PurchaseItemStack(plugin, item22, 20), 20);
        setBuyItem(item23, 23, new PurchaseItemStack(plugin, item23, 20), 20);
        setBuyItem(item24, 24, new PurchaseItemStack(plugin, item24, 20), 20);
        setBuyItem(item25, 25, new PurchaseItemStack(plugin, item25, 20), 20);
        setBuyItem(item26, 26, new PurchaseItemStack(plugin, item26, 20), 20);

        // Row 4
        ItemStack item27 = new ItemStack(Material.BANNER, 1, (short) 0, (byte) 9);
        ItemStack item28 = new ItemStack(Material.BANNER, 1, (short) 0, (byte) 10);
        ItemStack item29 = new ItemStack(Material.BANNER, 1, (short) 0, (byte) 11);
        ItemStack item30 = new ItemStack(Material.BANNER, 1, (short) 0, (byte) 12);
        ItemStack item31 = new ItemStack(Material.BANNER, 1, (short) 0, (byte) 13);
        ItemStack item32 = new ItemStack(Material.BANNER, 1, (short) 0, (byte) 14);
        ItemStack item33 = new ItemStack(Material.BANNER, 1, (short) 0, (byte) 15);

        setBuyItem(item27, 27, new PurchaseItemStack(plugin, item27, 20), 20);
        setBuyItem(item28, 28, new PurchaseItemStack(plugin, item28, 20), 20);
        setBuyItem(item29, 29, new PurchaseItemStack(plugin, item29, 20), 20);
        setBuyItem(item30, 30, new PurchaseItemStack(plugin, item30, 20), 20);
        setBuyItem(item31, 31, new PurchaseItemStack(plugin, item31, 20), 20);
        setBuyItem(item32, 32, new PurchaseItemStack(plugin, item32, 20), 20);
        setBuyItem(item33, 33, new PurchaseItemStack(plugin, item33, 20), 20);

        // Row 6
        ItemStack page1 = new ItemStack(Material.PAPER, 1);
        ItemStack page2 = new ItemStack(Material.PAPER, 2);
        ItemStack page3 = new ItemStack(Material.PAPER, 3);
        ItemStack page4 = new ItemStack(Material.PAPER, 4);
        ItemStack page5 = new ItemStack(Material.PAPER, 5);
        ItemStack page6 = new ItemStack(Material.PAPER, 6);
        ItemStack page7 = new ItemStack(Material.PAPER, 7);
        //ItemStack page8 = new ItemStack(Material.PAPER, 8);
        ItemStack exitButton = itemGen.generateItem("exit_button", type);
        ItemStack currentPage = new ItemStack(Material.EMPTY_MAP, 8);

        setPageItem(page1, 45, RealmShopPage1.class, 1);
        setPageItem(page2, 46, RealmShopPage2.class, 2);
        setPageItem(page3, 47, RealmShopPage3.class, 3);
        setPageItem(page4, 48, RealmShopPage4.class, 4);
        setPageItem(page5, 49, RealmShopPage5.class, 5);
        setPageItem(page6, 50, RealmShopPage6.class, 6);
        setPageItem(page7, 51, RealmShopPage7.class, 7);
        setPageItem(currentPage, 52, 8);
        setItem(exitButton, 53, new Exit(plugin));
    }
}
