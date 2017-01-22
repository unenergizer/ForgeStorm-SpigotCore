package com.forgestorm.spigotcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.redis.RemoveNetworkPlayer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerQuit implements Listener {

	private final SpigotCore plugin;

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		//Remove the player
		new RemoveNetworkPlayer(plugin).removeNetworkPlayer(player);

		//Do not show logout message.
		event.setQuitMessage("");
	}
}
