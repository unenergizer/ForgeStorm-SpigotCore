package com.forgestorm.spigotcore.entity.spawner;

import com.forgestorm.spigotcore.constants.FilePaths;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveEntitySpawner {

	private final String FILE_PATH = FilePaths.ENTITY_SPAWNER.toString();

	public SaveEntitySpawner() {
		if(!(new File(FILE_PATH)).exists()){
			createConfig();
		}
	}

	public void entitySaver(Location location, int spawnRadius, int runRadius, int maxMobCount, int respawnTime){
		File file = new File(FILE_PATH);
		FileConfiguration config =  YamlConfiguration.loadConfiguration(file);
		
		int id = config.getInt("totalSpawners");
		
		int x = (int) location.getX();
		int y = (int) location.getY();
		int z = (int) location.getZ();
		
		//TODO: Temp list.
		List<String> list = new ArrayList<>();
		list.add("PutMobNamesHere");
		list.add("MakeSureTheyExistFirst");
		
		//Set new total number of spawners.
		config.set("totalSpawners", id + 1);
		
		//Add the spawner values
		config.set(id + ".location.x", x);
		config.set(id + ".location.y", y);
		config.set(id + ".location.z", z);
		config.set(id + ".radius.spawn", spawnRadius);
		config.set(id + ".radius.run", runRadius);
		config.set(id + ".spawn.count", maxMobCount);
		config.set(id + ".respawn.time", respawnTime);
		config.set(id + ".mobs", list);

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
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
