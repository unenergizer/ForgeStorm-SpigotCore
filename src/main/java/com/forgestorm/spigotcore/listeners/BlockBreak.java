package com.forgestorm.spigotcore.listeners;

import com.forgestorm.spigotcore.SpigotCore;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

@AllArgsConstructor
public class BlockBreak implements Listener {
	private final SpigotCore plugin;

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();										//The player who triggered the event.
		Material tool = player.getInventory().getItemInMainHand().getType();	//The tool the player may be holding.
		Block block = event.getBlock();											//Gets the actual block broken.

		//Let players break blocks in their realms.
		if (player.getWorld().equals(Bukkit.getWorlds().get(0))) {
			if (!player.isOp() && player.getGameMode() != GameMode.CREATIVE) {
				event.setCancelled(true);
			} else {

				//Let players break their portals.
				if (block.getType() == Material.PORTAL) {
					//TODO: Check if portal was the players portal...
					if (!player.isOp() && player.getGameMode() != GameMode.CREATIVE) {
						event.setCancelled(true);
					}
					plugin.getRealmManager().removePlayerRealmAtLocation(player, event.getBlock().getLocation());
				}

				//See if the item is a profession item.
				if (!plugin.getProfession().toggleProfession(player, tool, block)) {
					event.setCancelled(true);
				}
			}
		} else {
            //If the player is a friend in a realm, they can build.
            event.setCancelled(!plugin.getRealmManager().canBuild(player));
        }
	}
}
