package com.forgestorm.spigotcore.menus.pagenation;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.ClickAction;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class PaginationMenu extends Menu {

	private final SpigotCore PLUGIN;
	private final List<MenuPage> pages = new ArrayList<>();
	private final int rows;
	
	public PaginationMenu(SpigotCore plugin, String title, int rows) {
		super(plugin);
		PLUGIN = plugin;
		makeMenuItems();
		this.rows = rows;
		init(title, rows + 1);
	}
	
	/**
	 * Adds the pages to the pages list so they may be
	 * switched to
	 * 
	 * @param pages Menu pages to add to the list.
	 */
	protected void addMenuPages(MenuPage ... pages) {
		Collections.addAll(this.pages, pages);
	}
	
	/**
	 * Generates the pages for the menu.
	 * 
	 * This should only be called after 'addMenuPages'
	 * is called
	 */
	protected void makePages() {
		
		ItemGenerator itemGen = PLUGIN.getItemGen();
		ItemStack page = itemGen.generateItem("", ItemTypes.MENU);
		
		for (int i = rows * 9; i < pages.size() + (rows * 9); i++) {
			page.setAmount(i - (rows * 9));
			setItem(page, i, new ChangePage(PLUGIN, i - (rows * 9)));	
		}
		
		// Generate page 1
		makePage(1);
	}
	
	void changePage(int slot) {
		makePage(slot);
	}
	
	/**
	 * Changes to the page by swapping the items out
	 * 
	 * @param page the page to switch to based on it's slot
	 * in the list
	 */
	private void makePage(int page) {
		clear();
		
		List<SlotData> items = pages.get(page).generateItems(PLUGIN);
		
		for (SlotData slotData : items) {
			
			ItemStack item = slotData.getItem();
			int slot = slotData.getSlot();
			ClickAction action = slotData.getClickAction();
			Class<? extends Menu> clazz = slotData.getClazz();
			
			if (action != null) setItem(item, slot, action);
			if (clazz != null) setItem(item, slot, clazz);
			
			if (action == null && clazz == null) {
				setItem(item, slot);
			}
		}
	}
	
	/**
	 * Call to the first page from here
	 */
	abstract protected void makeMenuItems();
}

class ChangePage implements ClickAction {

	private final SpigotCore PLUGIN;
	private int slot;
	
	ChangePage(SpigotCore plugin, int slot) {
		PLUGIN = plugin;
		this.slot = slot;
	}
	
	@Override
	public void click(Player player) {
		PaginationMenu menu = (PaginationMenu) PLUGIN.
				getProfileManager().
				getProfile(player).
				getCurrentMenu();
		
		menu.changePage(slot);
	}
	
}