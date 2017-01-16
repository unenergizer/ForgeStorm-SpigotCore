package com.forgestorm.spigotcore.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.forgestorm.spigotcore.constants.Messages;

public class BlockPlace implements Listener {

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Block block = event.getBlock();
		Material material = block.getType();
		
		if (player.isOp() && player.getGameMode().equals(GameMode.CREATIVE)) {
			event.setCancelled(false);
		} else {
			
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

	}
}
