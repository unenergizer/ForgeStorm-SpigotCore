package com.forgestorm.spigotcore.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.help.Tutorial;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerMove implements Listener {

	private SpigotCore plugin;

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Tutorial tutorial = plugin.getTutorial();

		//Prevent players in the tutorial from moving.
		if (tutorial.getActivePlayers().containsKey(player)) {
			double moveX = event.getFrom().getX();
			double moveToX = event.getTo().getX();

			double moveY = event.getFrom().getY();
			double moveToY = event.getTo().getY();

			double moveZ = event.getFrom().getZ();
			double moveToZ = event.getTo().getZ();

			//If the tutorial has started, then let the player look around, but not jump, fly, walk, or run.
			if ((moveX != moveToX || moveY != moveToY || moveZ != moveToZ)) {
				//Stop them from moving.
				event.setCancelled(true);
				player.setAllowFlight(true);
				player.setFlying(true);
			}
		}

		//For double jump.
		if (!tutorial.getActivePlayers().containsKey(player)) {
			if ((player.getGameMode() != GameMode.CREATIVE) && (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
				player.setAllowFlight(true);
			}
		}
	}
}
