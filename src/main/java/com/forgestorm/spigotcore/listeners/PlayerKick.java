package com.forgestorm.spigotcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.player.RemoveNetworkPlayer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerKick implements Listener {

	private final SpigotCore plugin;
	
	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) {
		Player player = event.getPlayer();
		
		//Remove the player
		new RemoveNetworkPlayer(plugin).removeNetworkPlayer(player);

		//Do not show logout message.
		event.setLeaveMessage("");
	}
}