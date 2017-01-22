package com.forgestorm.spigotcore.listeners;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.Messages;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

@AllArgsConstructor
public class BlockPlace implements Listener {

    private final SpigotCore plugin;

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		Material material = block.getType();

		//Let players break blocks in their realms.
		if (player.getWorld().equals(Bukkit.getWorlds().get(0))) {
			if (!player.isOp() && player.getGameMode() != GameMode.CREATIVE) {
				event.setCancelled(true);
			} else {

				//Check TNT explosions
				if (material.equals(Material.TNT)) {
					//If the block underneath is powered, it is okay to set TNT.
					if (block.isBlockIndirectlyPowered()) {
						player.sendMessage(Messages.BLOCK_PLACE_TNT_SUCCESS.toString());
						event.setCancelled(false);
					} else {
						//If the block is not powered, do not let player set TNT.
						//This will prevent griefing of plants and random placement of TNT.
						player.sendMessage(Messages.BLOCK_PLACE_TNT_FAIL.toString());
						event.setCancelled(true);
					}
				} else {
					//Cancel the event if the player is not an operator.
					event.setCancelled(true);
				}
			}
		} else {
            //If the player is a friend in a realm, they can build.
            event.setCancelled(!plugin.getRealmManager().canBuild(player));
        }
	}
}
