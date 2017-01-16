package com.forgestorm.spigotcore.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.forgestorm.spigotcore.SpigotCore;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BlockBreak implements Listener {
	
	private final SpigotCore PLUGIN;

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();										//The player who triggered the event.
		Material tool = player.getInventory().getItemInMainHand().getType();	//The tool the player may be holding.
		Block block = event.getBlock();											//Gets the actual block broken.
		
		//Let players break their portals.
		if (event.getBlock().getType().equals(Material.PORTAL)) {
			//TODO: Check if portal was the players portal...
			if (!player.isOp() && !player.getGameMode().equals(GameMode.CREATIVE)) {
				event.setCancelled(true);
			}
			PLUGIN.getPlayerRealmManager().removePlayerRealmAtLocation(player, event.getBlock().getLocation());
		}
		
		//See if the item is a profession item.
		if (!PLUGIN.getProfession().toggleProfession(player, tool, block)) {
			event.setCancelled(true);
		}

		//Cancel the event if the block isn't broke by someone in creative.
		if (!player.getGameMode().equals(GameMode.CREATIVE) && player.isOp()) {
			event.setCancelled(true);
		}
	}
}
