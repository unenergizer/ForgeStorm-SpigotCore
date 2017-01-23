package com.forgestorm.spigotcore.player;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportSpawn {

	private final Location LOCATION = new Location(Bukkit.getWorlds().get(0), 0.5, 108, -24.5);
	private List<Player> players = new ArrayList<>();

	public void teleportSpawn(Player player) {

		if (!players.contains(player)) {
			players.add(player);

			//Teleport the player.
			player.teleport(LOCATION);
			
			//Remove them from the array.
			players.remove(player);
		}
	}
}
