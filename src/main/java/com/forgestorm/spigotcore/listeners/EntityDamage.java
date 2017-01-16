package com.forgestorm.spigotcore.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import com.forgestorm.spigotcore.SpigotCore;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EntityDamage implements Listener {
	
	private final SpigotCore PLUGIN;
	
	@EventHandler
	public void onEntityDamage(EntityDamageEvent event) {
		Entity entity = event.getEntity();
		
		//If the player falls into the void, tp them to the lobby spawn.
		if (event.getCause().equals(DamageCause.VOID) && entity instanceof Player) {
			event.setCancelled(true);
			PLUGIN.getPlayerRealmManager().leaveRealm((Player) entity);
		}
		
		//Mount Check
		if (entity instanceof Horse || entity instanceof Pig) {
			
			//This mount should be a player mount.
			if (entity.getPassenger() != null && entity.getPassenger() instanceof Player) {
				Player player = (Player) entity.getPassenger();
				PLUGIN.getMountManager().removePlayerMount(player);
			}
		}
	}
}
