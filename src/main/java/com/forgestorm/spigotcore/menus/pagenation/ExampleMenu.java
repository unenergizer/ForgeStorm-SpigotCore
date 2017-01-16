package com.forgestorm.spigotcore.menus.pagenation;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.util.item.ItemGenerator;

public class ExampleMenu extends PagenationMenu {
	
	public ExampleMenu(SpigotCore plugin) {
		super(plugin, "Example menu", 5);
		makeMenuItems();
	}

	@Override
	protected void makeMenuItems() {		
		addMenuPages( new PageExample());
		makePages();
	}
	
	
}

class PageExample implements MenuPage {

	@Override
	public List<SlotData> generateItems(SpigotCore plugin) {
		
		ItemGenerator itemGen = plugin.getItemGen();
		ItemStack myItem = itemGen.generateItem("", ItemTypes.MENU);
		
		SlotData slotData = new SlotData(myItem, 0);
		
		List<SlotData> items = new ArrayList<>();
		items.add(slotData);
		
		return items;
	}
}
