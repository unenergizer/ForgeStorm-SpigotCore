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

public class TrackProfessionMenu extends Menu {

    private final SpigotCore plugin;
    private final FileConfiguration config;

    public TrackProfessionMenu(SpigotCore plugin) {
        super(plugin);
        this.plugin = plugin;
        config = YamlConfiguration.loadConfiguration(
                new File(FilePaths.HELP_TRACKER.toString()));

        ConfigurationSection outSection = config.getConfigurationSection("ProfessionLocations");
		Iterator<String> outIt = outSection.getKeys(false).iterator();
		
		int total = 0;
		while (outIt.hasNext()) {
			ConfigurationSection inSection = config.getConfigurationSection("ProfessionLocations." + outIt.next());
			total += inSection.getKeys(false).size();
		}
		
		int neededRows = 1 + (total / 9);
		init("Professions Items Tracking", neededRows);
		
		makeMenuItems();
	}

	@Override
	protected void makeMenuItems() {
			
		ConfigurationSection outSection = config.getConfigurationSection("ProfessionLocations");
		Iterator<String> outIt = outSection.getKeys(false).iterator();
		
		int invSlot = 0;
		while (outIt.hasNext()) {
			ConfigurationSection inSection = config.getConfigurationSection("ProfessionLocations." + outIt.next());

			for (String s : inSection.getKeys(false)) {
				int i = Integer.parseInt(s);

				String name = inSection.getString(i + ".name");
				String material = inSection.getString(i + ".material");
				String lore = inSection.getString(i + ".lore");

				int x = inSection.getInt(i + ".x");
				int y = inSection.getInt(i + ".y");
				int z = inSection.getInt(i + ".z");

				//Set item
				ItemStack item = new ItemBuilder(Material.valueOf(material))
						.setTitle(name)
						.addLore(ChatColor.GRAY + lore)
						.build(true);

				setItem(item,
						invSlot++,
                        new SetCompassTarget(plugin, new Location(Bukkit.getWorlds().get(0), x, y, z))
                );
            }
        }
	}
}
