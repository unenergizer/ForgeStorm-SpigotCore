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
import org.bukkit.event.block.BlockDamageEvent;

@AllArgsConstructor
public class BlockDamage implements Listener {
	
	private final SpigotCore plugin;
	
	@EventHandler
	public void onBlockDamage(BlockDamageEvent event) {
		
		Block block = event.getBlock();
		Player player = event.getPlayer();

		if (player.getWorld().equals(Bukkit.getWorlds().get(0))) {

			//Let players break their portals.
			if (event.getBlock().getType().equals(Material.PORTAL)) {

                //Prevent Admin accidental deletion
				if (!player.isOp() && !player.getGameMode().equals(GameMode.CREATIVE)) {
					event.setCancelled(true);
				}

				plugin.getRealmManager().removePlayerRealmAtLocation(player, block.getLocation());
				return;
			}

			//If the player is damaging a CHEST, it must be a loot chest!
			if (block.getType().equals(Material.CHEST)) {
				event.setInstaBreak(true);
			
			/*
			event.getPlayer().sendMessage("DEBUG: You broke a loot chest!");
			
			Chest chest = (Chest) block.getState();
			Inventory inventory = chest.getInventory();
			Location location = block.getLocation();
			
			//If the block is a empty loot chest, put loot inside it.
			if (LootTableChestManager.isChestEmpty(inv)) {
				LootTableChestManager.toggleChestLoot(inv);
			}
			*/

				//Setup the broken chest to be regenerated.
				//LootTableChestManager.toggleChestRespawn(inv, loc);
			} else if (block.getType().equals(Material.END_CRYSTAL)) {
				event.setInstaBreak(true);

				event.getPlayer().sendMessage("DEBUG: You broke the end crystal!");
			} else {
				event.setCancelled(true);
			}
		} else {
            //If the player is a friend in a realm, they can build.
            event.setCancelled(!plugin.getRealmManager().canBuild(player));
        }
	}
}
