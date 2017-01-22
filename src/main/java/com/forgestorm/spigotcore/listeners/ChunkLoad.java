package com.forgestorm.spigotcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.entity.EntityManager;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChunkLoad implements Listener {

	private final SpigotCore plugin;
	
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event) {
		World main = Bukkit.getWorlds().get(0);
		World chunkWorld = event.getWorld();
		Chunk chunk = event.getChunk();
		
		//Prevent new chunks.
		if (event.isNewChunk()) {
			chunk.unload(false);
			return;
		}
		
		if (chunkWorld.equals(main)) {
			
			//System.out.println("[Loaded Chunk] X: " + chunk.getX() + " - Z: " + chunk.getZ());
			
			//Clear living entities from newly loaded chunks.
			for (Entity entity: chunk.getEntities()) {
				if (entity instanceof LivingEntity && !(entity instanceof Player)) {
					EntityManager mm = plugin.getEntityManager();
					boolean hasRPGEntity = mm.getRpgEntity().containsKey(entity.getUniqueId());
					
					//Remove RPGEntity from Map.
					if (!hasRPGEntity) {
						entity.remove();
					}
				}
			}
			
			plugin.getChunkManager().addNewChunk(chunk);
		}
	}
}
