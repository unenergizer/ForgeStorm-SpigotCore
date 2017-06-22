package com.forgestorm.spigotcore.listeners;

import lombok.AllArgsConstructor;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

@AllArgsConstructor
public class ChunkLoad implements Listener {
	
	@EventHandler
	public void onChunkLoad(ChunkLoadEvent event) {
		Chunk chunk = event.getChunk();
		
		//Prevent new chunks.
		if (event.isNewChunk()) {
			chunk.unload(false);
			return;
		}
	}
}
