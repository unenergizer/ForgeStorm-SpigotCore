package com.forgestorm.spigotcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

import com.forgestorm.spigotcore.SpigotCore;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InventoryDrag implements Listener {

	private final SpigotCore PLUGIN;
	
	@EventHandler
	public void onInventoryDrag(InventoryDragEvent event) {
		Player player = (Player) event.getWhoClicked();
		
		if (PLUGIN.getProfileManager().getProfile(player).getCurrentMenu() != null) {
			event.setCancelled(true);
		}
	}
}
