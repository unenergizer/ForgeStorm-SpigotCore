package com.forgestorm.spigotcore.listeners;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.help.Tutorial;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;

@AllArgsConstructor
public class PlayerToggleFlight implements Listener {

	private final SpigotCore plugin;

	@EventHandler
	public void onPlayerFly(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		Tutorial tutorial = plugin.getTutorial();

		//For double jump. Also make sure the player isn't in the tutorial.
		if (!tutorial.getActivePlayers().containsKey(player)) {
			if (player.getGameMode() != GameMode.CREATIVE) {
				event.setCancelled(true);
				player.setAllowFlight(false);
				player.setFlying(false);
				player.setVelocity(player.getLocation().getDirection().multiply(2.0D).setY(0.9D));
				Bukkit.getWorlds().get(0).playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1f, 1f);
			}
		}
	}
}