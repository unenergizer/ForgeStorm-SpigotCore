package com.forgestorm.spigotcore.world;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;

import com.forgestorm.spigotcore.SpigotCore;

public class ChunkManager {

	private final SpigotCore PLUGIN;

	private Map<Location, ChunkData> chunkData = new HashMap<>();

	public ChunkManager(SpigotCore plugin) {
		PLUGIN = plugin;

		loadServerStartupChunks();
	}

	private void loadServerStartupChunks() {
		for (Chunk chunk: Bukkit.getWorlds().get(0).getLoadedChunks()) {
			addNewChunk(chunk);
		}
	}

	public void addNewChunk(Chunk chunk) {
		List<Integer> ids = PLUGIN.getEntitySpawnerManager().assignSpawners(chunk);

		//TODO: Loot Chest Spawns.

		//TODO: Random Buff Spawns.

		//Only save the chunk info in the map if it has spawner's.
		if (!ids.isEmpty()) {
			Location location = chunk.getBlock(0, 0, 0).getLocation();
			int x = location.getBlockX();
			int z = location.getBlockZ();

			Bukkit.getLogger().info("[FSRPG] Chunk x" + x + " z" + z + " Spawners: " + ids.toString());
			Bukkit.getLogger().info("[FSRPG] Chunk x" + x + " z" + z + " added to map. Total chunks: " + chunkData.size());

			ChunkData rpgChunk = new ChunkData(PLUGIN, location);
			rpgChunk.setSpawnerIDs(ids);

			chunkData.put(location, rpgChunk);
			PLUGIN.getEntitySpawnerManager().spawnMonsters(rpgChunk);
		}
	}

	public void removeChunk(Chunk chunk) {
		chunkData.remove(chunk);
	}

	public void forceRemoveChunk(Location location) {
		Chunk chunk = location.getChunk();
		chunk.unload(true);
		chunkData.remove(chunk);
	}

	public ChunkData getChunkInfo(Chunk chunk) {
		return chunkData.get(chunk);
	}
}
