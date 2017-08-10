package com.forgestorm.spigotcore.menus;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import com.forgestorm.spigotcore.menus.actions.ClickAction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Menu {

    private final SpigotCore plugin;
    private final Map<Integer, Class<? extends Menu>> linkedMenus = new HashMap<>();
    private final Map<Integer, Menu> staticLinkedMenus = new HashMap<>();
    private final Map<Integer, ClickAction> clickActions = new HashMap<>();
    private final List<Integer> movableSlots = new ArrayList<>();
    private Inventory inventory;

    public Menu(SpigotCore plugin) {
        this.plugin = plugin;
    }

    protected abstract void makeMenuItems();

    protected Inventory getInventory() {
        return inventory;
    }

    protected List<Integer> getMovableSlots() {
        return movableSlots;
    }

    public void performAction(Player player, int slot) {

        if (linkedMenus.containsKey(slot)) {

            try {

                Constructor<? extends Menu> constructor = linkedMenus.get(slot).getConstructor(SpigotCore.class);
                Menu menu = constructor.newInstance(plugin);
                menu.open(player);

            } catch (InstantiationException | IllegalAccessException
                    | NoSuchMethodException | InvocationTargetException x) {
                x.printStackTrace();
            }
        }

        if (clickActions.containsKey(slot)) {
            clickActions.get(slot).click(player);
        }

        if (staticLinkedMenus.containsKey(slot)) {
            staticLinkedMenus.get(slot).open(player);
        }
    }

    protected void clear() {
        linkedMenus.clear();
        staticLinkedMenus.clear();
        clickActions.clear();
        movableSlots.clear();
    }

    /**
     * Initializes the information for the inventory.
     *
     * @param title The title of the inventory
     * @param rows  The number of rows for the inventory
     */
    protected void init(String title, int rows) {
        inventory = Bukkit.createInventory(null, 9 * rows, title);
    }

    public void setItem(ItemStack item, int slot) {
        inventory.setItem(slot, item);
    }

    public void setItem(ItemStack item, int slot, boolean slotMovable) {
        inventory.setItem(slot, item);
        if (slotMovable) movableSlots.add(slot);
    }

    public void setItem(ItemStack item, int slot, Class<? extends Menu> clazz) {
        setItem(item, slot);
        linkedMenus.put(slot, clazz);
    }

    public void setItem(ItemStack item, int slot, Class<? extends Menu> clazz, boolean slotMovable) {
        setItem(item, slot, slotMovable);
        linkedMenus.put(slot, clazz);
    }

    public void setItem(ItemStack item, int slot, ClickAction clickAction) {
        setItem(item, slot);
        clickActions.put(slot, clickAction);
    }

    public void setBuyItem(ItemStack item, int slot, ClickAction clickAction, int buyAmount) {
        // Add buy message lore
        ItemMeta itemMeta = item.getItemMeta();
        List<String> list = new ArrayList<>();
        list.add("");
        list.add(ChatColor.RED + "" + ChatColor.BOLD + "Buy" + ChatColor.GRAY + " for " + ChatColor.YELLOW + "$" + buyAmount);
        itemMeta.setLore(list);
        item.setItemMeta(itemMeta);

        // Set the item
        setItem(item, slot);
        clickActions.put(slot, clickAction);
    }

    public void setPageItem(ItemStack item, int slot, int page) {
        // Add page title
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Page " + page);
        item.setItemMeta(itemMeta);
        inventory.setItem(slot, item);
    }

    public void setPageItem(ItemStack item, int slot, Class<? extends Menu> clazz, int page) {
        // Add page title
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ChatColor.YELLOW + "Page " + page);
        item.setItemMeta(itemMeta);

        // Set the item
        setItem(item, slot);
        linkedMenus.put(slot, clazz);
    }

    public void setItem(ItemStack item, int slot, ClickAction clickAction, boolean slotMovable) {
        setItem(item, slot, slotMovable);
        clickActions.put(slot, clickAction);
    }

    public void setItem(ItemStack item, int slot, Menu menu) {
        setItem(item, slot);
        staticLinkedMenus.put(slot, menu);
    }

    public void setItem(ItemStack item, int slot, Menu menu, boolean slotMovable) {
        setItem(item, slot, slotMovable);
        staticLinkedMenus.put(slot, menu);
    }

    public void setMovableSlot(int slot) {
        movableSlots.add(slot);
    }

    public boolean isMovableSlot(int slot) {
        return movableSlots.contains(slot);
    }

    public void open(Player player) {
        PlayerProfileData playerProfileData = plugin.getProfileManager().getProfile(player);

        // If the player doesn't have profile data, then don't open menus.
        if (playerProfileData == null) return;

        player.openInventory(inventory);
        playerProfileData.setCurrentMenu(this);
    }
}
