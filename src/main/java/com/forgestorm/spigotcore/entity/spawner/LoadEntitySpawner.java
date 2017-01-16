package com.forgestorm.spigotcore.entity.spawner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.forgestorm.spigotcore.constants.FilePaths;

public class LoadEntitySpawner {

	private final String FILE_PATH = FilePaths.ENTITY_SPAWNER.toString();

	public LoadEntitySpawner() {
		if(!(new File(FILE_PATH)).exists()){
			createConfig();
		}
	}
	
	public List<EntitySpawnerData> loadConfigValues() {
		List<EntitySpawnerData> allSpawners = new ArrayList<>();
		
		//Load the configuration file.
		File file = new File(FILE_PATH);
		FileConfiguration config =  YamlConfiguration.loadConfiguration(file);

		ConfigurationSection section = config.getConfigurationSection("");
		Iterator<String> it = section.getKeys(false).iterator();
		
		int i;
		while (it.hasNext()) {
		
			i = Integer.parseInt(it.next());
			EntitySpawnerData spawner = new EntitySpawnerData();

			int x = config.getInt(i + ".location.x");
			int y = config.getInt(i + ".location.y");
			int z = config.getInt(i + ".location.z");
			Location location = new Location(Bukkit.getWorlds().get(0), x, y, z);
			
			spawner.setId(i);
			spawner.setLocation(location);
			spawner.setSpawnRadius(config.getInt(i + ".radius.spawn"));
			spawner.setRunRadius(config.getInt(i + ".radius.run"));
			spawner.setMaxMobCount(config.getInt(i + ".spawn.count"));
			spawner.setRespawnTime(config.getInt(i + ".respawn.time"));
			spawner.setMobs(config.getList(i + ".mobs"));
			
			allSpawners.add(spawner);
		}
		
		return allSpawners;
	}

	private void createConfig() {
		File file = new File(FILE_PATH);
		FileConfiguration config =  YamlConfiguration.loadConfiguration(file);
		
		config.set("totalSpawners", -1);
		
		try {
			config.save(file);	//Save the file.
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
