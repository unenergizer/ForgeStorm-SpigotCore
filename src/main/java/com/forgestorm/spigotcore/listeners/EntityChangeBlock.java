package com.forgestorm.spigotcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public class EntityChangeBlock implements Listener {
	
	@EventHandler
	public void onEntityChangeBlock(EntityChangeBlockEvent event) {
		//Cancel entity block changes.
		event.setCancelled(true);
	}
}
