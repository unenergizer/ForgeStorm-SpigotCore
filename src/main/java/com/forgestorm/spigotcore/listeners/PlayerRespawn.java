package com.forgestorm.spigotcore.listeners;

import com.forgestorm.spigotcore.SpigotCore;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

@AllArgsConstructor
public class PlayerRespawn implements Listener {

	private final SpigotCore plugin;

	@EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        // Update the players health under their name.
        plugin.getScoreboardManager().updatePlayerHP(player, 20);

        // If they are in the tutorial and died, tp them back.
        if (plugin.getProfileManager().getProfile(player).isInTutorial()) {
            //TODO: RELOCATE
            //plugin.getTeleports().teleportTutorial(player);
        }
    }
}
