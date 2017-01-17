package com.forgestorm.spigotcore.entity;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.entity.spawner.EntitySpawnerData;
import com.forgestorm.spigotcore.entity.spawner.LoadEntitySpawner;
import com.forgestorm.spigotcore.profile.monster.MonsterLoader;
import com.forgestorm.spigotcore.world.ChunkData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntitySpawnerManager {
	
	private final SpigotCore PLUGIN;
	
	private List<EntitySpawnerData> spawners;
	
	public EntitySpawnerManager(SpigotCore plugin) {
		PLUGIN = plugin;
		
		loadAllSpawners();
	}
	
	public List<Integer> assignSpawners(Chunk chunk) {
		//Get spawner ID's
		List<Integer> ids = new ArrayList<>();

		//Add spawner ID's to the RPGChunk
		Location chunkLocation = chunk.getBlock(0, 0, 0).getLocation();
		int chunkX = chunkLocation.getBlockX();
		int chunkZ = chunkLocation.getBlockZ();

		//Loop through and add Spawner ID's to the RPGChunk data class.
		for (EntitySpawnerData spawner: spawners) {
			Location spawnerLocation = spawner.getLocation();
			int spawnerX = spawnerLocation.getBlockX();
			int spawnerZ = spawnerLocation.getBlockZ();

			if (spawnerX >= chunkX && spawnerZ >= chunkZ && spawnerX <= (chunkX + 16) && spawnerZ <= (chunkZ + 16)) {
				ids.add(spawner.getId());
				
				Bukkit.getLogger().info(" ");
				Bukkit.getLogger().info("--------------[ ASSIGN SPAWNERS ]-------------");
				Bukkit.getLogger().info("[FSRPG] Spawner ID " + spawner.getId() + " added!");
			}
		}

		return ids;
	}
	
	public void spawnMonsters(ChunkData rpgChunk) {

		if (rpgChunk.getSpawnerIDs() != null && rpgChunk.getSpawnerIDs().size() > 0) {
			int numberOfSpawners = rpgChunk.getSpawnerIDs().size();
			int x = rpgChunk.getLOCATION().getBlockX();
			int z = rpgChunk.getLOCATION().getBlockZ();

			Bukkit.getLogger().info(" ");
			Bukkit.getLogger().info("--------------[ SPAWN MONSTERS ]-------------");
			Bukkit.getLogger().info("[FSRPG] This chunk (x" + x + " z" + z + ") has " + numberOfSpawners + " spawners.");
			Bukkit.getLogger().info("[FSRPG] Spawners ID's: " + rpgChunk.getSpawnerIDs().toString());

			for (int i = 0; i < numberOfSpawners; i++) {
				int spawnerID = rpgChunk.getSpawnerIDs().get(i);
				int numberOfMobs = getSpawnerData(spawnerID).getMobs().size();
				Location location = getSpawnerData(spawnerID).getLocation();

				Bukkit.getLogger().info("[FSRPG] Spawner ID " + spawnerID + " has " + numberOfMobs + " mobs.");

				for (int j = 0; j < numberOfMobs; j++) {
					String name = getSpawnerData(spawnerID).getMob(j);
					new MonsterLoader(PLUGIN).getConfig(name, location);

					int spawnX = (int) location.getX();
					int spawnY = (int) location.getY();
					int spawnZ = (int) location.getZ();

					Bukkit.getLogger().info("[FSRPG] Spawning: " + name + " at x" + spawnX + " y" + spawnY + " z" + spawnZ);
				}
			}
		}
	}
	
	private void loadAllSpawners() {
		spawners = new LoadEntitySpawner().loadConfigValues();

		Bukkit.getLogger().info(" ");
		Bukkit.getLogger().info("---------------[ LOAD SPAWNERS ]--------------");
		Bukkit.getLogger().info("[FSRPG] Loaded " + spawners.size() + " spawners into memory.");
		Bukkit.getLogger().info("[FSRPG] ID's: " + spawners.toString());
	}
	
	public void addSpawner(EntitySpawnerData spawner) {
		spawners.add(spawner);
	}
	
	public EntitySpawnerData getSpawnerData(int id) {
		return spawners.get(id);
	}
}
