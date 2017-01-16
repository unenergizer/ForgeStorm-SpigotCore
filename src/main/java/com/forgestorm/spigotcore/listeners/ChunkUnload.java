package com.forgestorm.spigotcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.entity.EntityManager;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChunkUnload implements Listener {

	private final SpigotCore PLUGIN;
	
	@EventHandler
	public void onChunkUnload(ChunkUnloadEvent event) {
		World main = Bukkit.getWorlds().get(0);
		World chunkWorld = event.getWorld();
		Chunk chunk = event.getChunk();
		
		if (chunkWorld.equals(main)) {
			//Remove entities.
			Entity[] entities = chunk.getEntities();
			
			//Clear living entities from unloaded chunks.
			for (Entity entity: entities) {
				if (entity instanceof LivingEntity && !(entity instanceof Player)) {
					EntityManager mm = PLUGIN.getEntityManager();
					
					//Remove RPGEntity from Map.
					if (mm.getRpgEntity().containsKey(entity.getUniqueId())) {
						mm.removeRPGEntity(entity.getUniqueId());
					}
					
					entity.remove();
				}
			}
			
			//double timeLoaded = PLUGIN.getChunkManager().getChunkInfo(chunk).getTimeLoaded();
			//System.out.println("[Unloaded Chunk] X: " + chunk.getX() + " - Z: " + chunk.getZ() + " -> TimeLoaded: " + timeLoaded);
			//System.out.println("");
			//System.out.println("---------------[ LOAD SPAWNERS ]--------------");
			//System.out.println("[FSRPG] Chunk unloaded. Removed " + entitiesRemoved + " entities.");
			
			PLUGIN.getChunkManager().removeChunk(chunk);
		}
	}
}
