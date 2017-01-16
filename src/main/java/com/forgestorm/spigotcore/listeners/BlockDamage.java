package com.forgestorm.spigotcore.listeners;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

import com.forgestorm.spigotcore.SpigotCore;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BlockDamage implements Listener {
	
	private final SpigotCore PLUGIN;
	
	@EventHandler
	public void onBlockDamage(BlockDamageEvent event) {
		
		Block block = event.getBlock();
		Player player = event.getPlayer();
		
		//Let players break their portals.
		if (event.getBlock().getType().equals(Material.PORTAL)) {
			//TODO: Check if portal was the players portal...
			if (!player.isOp() && !player.getGameMode().equals(GameMode.CREATIVE)) {
				event.setCancelled(true);
			}
			PLUGIN.getPlayerRealmManager().removePlayerRealmAtLocation(player, block.getLocation());
			return;
		}
		
		//If the player is damaging a CHEST, it must be a loot chest!
		if (block.getType().equals(Material.CHEST)) {
			event.setInstaBreak(true);
			
			/*
			event.getPlayer().sendMessage("DEBUG: You boke a loot chest!");
			
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
			
			event.getPlayer().sendMessage("DEBUG: You boke the end crystal!");
		} else {
			event.setCancelled(true);
		}
	}
}
