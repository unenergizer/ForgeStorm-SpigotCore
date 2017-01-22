package com.forgestorm.spigotcore.listeners;

import com.forgestorm.spigotcore.world.instance.RealmManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.forgestorm.spigotcore.SpigotCore;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerPortal implements Listener {

	private final SpigotCore plugin;
	
	@EventHandler
	public void onPlayerTeleport(PlayerPortalEvent event) {
		
		Player player = event.getPlayer();

		//If player enters a Ender portal, teleport them back to spawn pad.
		if (event.getCause().equals(TeleportCause.NETHER_PORTAL)) {

			//Cancel teleportation to the NETHER
			event.setCancelled(true);
			RealmManager realmManager = plugin.getRealmManager();

			//Is the player inside a realm or inside the main world?
			if (player.getWorld().equals(Bukkit.getWorlds().get(0))) {
				//The player is inside the main world.
				//Send the player to this player realm.
				realmManager.joinRealm(player, player.getLocation());
			} else {
				//Player is inside a realm.
				//Send them to the main world.
				realmManager.leaveRealm(player);
			}
		}
	}
}
