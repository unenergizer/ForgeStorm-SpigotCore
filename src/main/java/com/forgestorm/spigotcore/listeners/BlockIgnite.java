package com.forgestorm.spigotcore.listeners;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

public class BlockIgnite implements Listener {

	@EventHandler
	public void onBlockIgnite(BlockIgniteEvent event) {
		
		//Stop fire from being ignited, unless its a player.
		if (event.getPlayer() == null) {
			event.setCancelled(true);
		} else {
			//If a player exist, let them ignite a block if they are in creative.
			if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
				event.setCancelled(true);
			}
		}
	}
}
