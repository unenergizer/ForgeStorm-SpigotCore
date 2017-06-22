package com.forgestorm.spigotcore.listeners;

import com.forgestorm.spigotcore.constants.CommonSounds;
import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

@AllArgsConstructor
public class PlayerDropItem implements Listener {

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
        System.out.println("Compass drop");
        if (event.getItemDrop().getItemStack().getType() == Material.COMPASS) {
            event.getPlayer().sendMessage(ChatColor.RED + "You can not drop your compass.");
            CommonSounds.ACTION_FAILED.playSound(event.getPlayer());
            event.setCancelled(true);
        }
    }
}
