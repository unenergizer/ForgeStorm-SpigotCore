package com.forgestorm.spigotcore.menus.tracking;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.SetCompassTarget;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class TrackNPCMenu extends Menu {

    private final SpigotCore plugin;

    public TrackNPCMenu(SpigotCore plugin) {
        super(plugin);
        this.plugin = plugin;
        int neededRows = 1 + (this.plugin.getCitizenManager().getNpcMenuLocations().size() / 9);
        init("Npc Tracking", neededRows);
        makeMenuItems();
    }
	
	@Override
	protected void makeMenuItems() {
        Map<Location, ItemStack> npcMenuItems = plugin.getCitizenManager().getNpcMenuLocations();

        int i = 0;
        for (Map.Entry<Location, ItemStack> entry : npcMenuItems.entrySet()) {
            setItem(entry.getValue(), i++, new SetCompassTarget(plugin, entry.getKey()));
        }
    }
}
