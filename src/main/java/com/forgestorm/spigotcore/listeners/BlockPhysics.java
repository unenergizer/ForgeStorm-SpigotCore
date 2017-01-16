package com.forgestorm.spigotcore.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class BlockPhysics implements Listener {
	
	@EventHandler
	public void onBlockPhysics(BlockPhysicsEvent event) {

		//Prevent the nether portals from breaking.
		if (event.getBlock().getType().equals(Material.PORTAL)) {
			event.setCancelled(true);
		}
		//Prevent the nether portals from breaking.
		if (event.getChangedType().equals(Material.PORTAL)) {
			event.setCancelled(true);
		}
	}
}
