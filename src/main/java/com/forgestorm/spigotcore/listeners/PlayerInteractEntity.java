package com.forgestorm.spigotcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.forgestorm.spigotcore.SpigotCore;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerInteractEntity implements Listener {
	
	private final SpigotCore PLUGIN;
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {

		if (event.getRightClicked() instanceof Player) {
			Player npc = (Player) event.getRightClicked();
			
			//Check to see if entity is a Citizens NPC.
			if (npc.hasMetadata("NPC")) {
				
				Player player = event.getPlayer();
				PLUGIN.getCitizenManager().onCitizenInteract(player, npc);
			}
		}
	}
}