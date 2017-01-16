package com.forgestorm.spigotcore.menus.tracking;

import java.util.Map;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.SetCompassTarget;

public class TrackNPCMenu extends Menu {

	private final SpigotCore PLUGIN;
	
	public TrackNPCMenu(SpigotCore plugin) {
		super(plugin);
		PLUGIN = plugin;
		int neededRows = 1 + (PLUGIN.getCitizenManager().getNpcMenuLocations().size() / 9);
		init("Npc Tracking", neededRows);
		makeMenuItems();
	}
	
	@Override
	protected void makeMenuItems() {
		Map<Location, ItemStack> npcMenuItems = PLUGIN.getCitizenManager().getNpcMenuLocations();
		
		int i = 0;
		for (Map.Entry<Location, ItemStack> entry : npcMenuItems.entrySet()) {
			setItem(entry.getValue(), i++, new SetCompassTarget(PLUGIN, entry.getKey()));
		}
	}
}
