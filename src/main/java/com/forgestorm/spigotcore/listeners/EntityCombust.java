package com.forgestorm.spigotcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

public class EntityCombust implements Listener {

	@EventHandler
	public void onEntityCombust(EntityCombustEvent event) {
        // Prevent livingEntities from catching on fire.
        if (!(event.getEntity() instanceof Player)) {
            event.setCancelled(true);
        }
	}
}
