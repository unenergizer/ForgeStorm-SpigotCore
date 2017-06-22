package com.forgestorm.spigotcore.listeners;

import com.forgestorm.spigotcore.SpigotCore;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

@AllArgsConstructor
public class PlayerDeath implements Listener {

    private final SpigotCore plugin;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // Update the players health under their name.
        plugin.getScoreboardManager().updatePlayerHP(event.getEntity());
    }
}
