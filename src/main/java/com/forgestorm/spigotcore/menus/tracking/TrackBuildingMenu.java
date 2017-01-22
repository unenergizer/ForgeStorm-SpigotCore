package com.forgestorm.spigotcore.menus.tracking;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.FilePaths;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.SetCompassTarget;
import com.forgestorm.spigotcore.util.item.ItemBuilder;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Iterator;

public class TrackBuildingMenu extends Menu {

	private final SpigotCore PLUGIN;	
	private final FileConfiguration config;
	
	public TrackBuildingMenu(SpigotCore plugin) {
		super(plugin);
		PLUGIN = plugin;
		config = YamlConfiguration.loadConfiguration(
				new File(FilePaths.HELP_TRACKER.toString()));
		
		ConfigurationSection outSection = config.getConfigurationSection("BuildingLocations");
		int total = outSection.getKeys(false).size();
		
		int neededRows = 1 + ((total + 2) / 9);
		init("Building Location Tracking", neededRows);
		
		makeMenuItems();
	}

	@Override
	protected void makeMenuItems() {
			
		ConfigurationSection outSection = config.getConfigurationSection("BuildingLocations");
		Iterator<String> outIt = outSection.getKeys(false).iterator();
		
		int invSlot = 0;
		while (outIt.hasNext()) {
	
			String building = outIt.next();
			
			String name = outSection.getString(building + ".name");
			String material = outSection.getString(building + ".material");
			String lore = outSection.getString(building + ".lore");
			
			int x = outSection.getInt(building + ".x");
			int y = outSection.getInt(building + ".y");
			int z = outSection.getInt(building + ".z");
			
			//Set item
			ItemStack item = new ItemBuilder(Material.valueOf(material))
								.setTitle(name)
								.addLore(ChatColor.GRAY + lore)
								.build(true);
			
			setItem(item,
					invSlot++,
					new SetCompassTarget(PLUGIN, new Location(Bukkit.getWorlds().get(0), x, y, z))
					);
		
		}
	}
}
