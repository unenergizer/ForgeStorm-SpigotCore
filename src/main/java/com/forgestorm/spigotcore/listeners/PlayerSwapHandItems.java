package com.forgestorm.spigotcore.listeners;

import com.forgestorm.spigotcore.constants.CommonSounds;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerSwapHandItems implements Listener {

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        System.out.println("Compass swap hand");
        if (event.getMainHandItem().getType() == Material.COMPASS) {
            event.getPlayer().sendMessage(ChatColor.RED + "You can not put your compass in your offhand.");
            CommonSounds.ACTION_FAILED.playSound(event.getPlayer());
            event.setCancelled(true);
        }
    }
}
