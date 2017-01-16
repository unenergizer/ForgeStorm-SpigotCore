package com.forgestorm.spigotcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.forgestorm.spigotcore.SpigotCore;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerPortal implements Listener {

	private final SpigotCore PLUGIN;
	
	@EventHandler
	public void onPlayerTeleport(PlayerPortalEvent event) {
		
		Player player = event.getPlayer();

		//If player enters a Ender portal, teleport them back to spawn pad.
		if (event.getCause().equals(TeleportCause.NETHER_PORTAL)) {
			
			//Cancel teleportation to the NETHER
			event.setCancelled(true);
			
			//Send the player to this player realm.
			PLUGIN.getPlayerRealmManager().joinRealm(player, player.getLocation());
		}
	}
}
