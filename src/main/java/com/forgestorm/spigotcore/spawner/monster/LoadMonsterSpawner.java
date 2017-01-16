package com.forgestorm.spigotcore.spawner.monster;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.forgestorm.spigotcore.constants.FilePaths;

public class LoadMonsterSpawner {

	private final String FILE_PATH = FilePaths.MONSTER_SPAWNER.toString();

	public LoadMonsterSpawner() {
		if(!(new File(FILE_PATH)).exists()){
			createConfig();
		}
	}

	public List<MonsterSpawnerData> loadConfigValues() {
		List<MonsterSpawnerData> allSpawners = new ArrayList<>();
		
		//Load the configuration file.
		File file = new File(FILE_PATH);
		FileConfiguration config =  YamlConfiguration.loadConfiguration(file);

		//Number of spawner ID's.
		int numberOfSpawners = config.getInt("totalSpawners");

		for (int i = 0; i < numberOfSpawners; i++) {
			MonsterSpawnerData spawner = new MonsterSpawnerData();

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
