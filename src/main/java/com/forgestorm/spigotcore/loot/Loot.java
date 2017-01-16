package com.forgestorm.spigotcore.loot;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

public abstract class Loot {
	
	protected String filePath;
	protected File file;
	protected FileConfiguration config;
	protected Location location;
	protected List<ItemStack> items = new ArrayList<>();
	
	public abstract List<ItemStack> generateItems();
	public abstract void spawnItems();
	public abstract void saveConfig();
	
	public void loadConfig() {
		//If configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(filePath)).exists()){
			saveConfig();
		} else {
			//lets load the configuration file.
			file = new File(filePath);
			config =  YamlConfiguration.loadConfiguration(file);
		}
	}
}
