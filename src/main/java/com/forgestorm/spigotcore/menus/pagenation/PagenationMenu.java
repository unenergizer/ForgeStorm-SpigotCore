package com.forgestorm.spigotcore.menus.pagenation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.ClickAction;
import com.forgestorm.spigotcore.util.item.ItemGenerator;

public abstract class PagenationMenu extends Menu {

	private final SpigotCore PLUGIN;
	private List<MenuPage> pages = new ArrayList<>();
	private int rows;
	
	public PagenationMenu(SpigotCore plugin, String title, int rows) {
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
	 * @param pages
	 */
	protected void addMenuPages(MenuPage ... pages) {
		for (int i = 0; i < pages.length; i++) {
			this.pages.add(pages[i]);
		}
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
		PagenationMenu menu = (PagenationMenu) PLUGIN.
				getProfileManager().
				getProfile(player).
				getCurrentMenu();
		
		menu.changePage(slot);
	}
	
}