package com.forgestorm.spigotcore.listeners;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.menus.RecipeOptionsMenu;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InventoryOpen implements Listener {
	
	private final SpigotCore PLUGIN;
	
	@EventHandler
	public void onInventoryOpen(InventoryOpenEvent event) {
		
		if (event.getPlayer() instanceof Player) {
			Player player = (Player) event.getPlayer();
			Location loc = player.getLocation();
			Inventory inv = event.getInventory();
			InventoryType invType = inv.getType();
			
			switch(invType) {
			case ANVIL:
				//OPEN MENU
				event.setCancelled(true);
				new RecipeOptionsMenu(PLUGIN).open(player);
				break;
			case BEACON:
				player.playSound(loc, Sound.BLOCK_NOTE_BASS, 1F, .5F);
				event.setCancelled(true);
				break;
			case BREWING:
				player.playSound(loc, Sound.BLOCK_NOTE_BASS, 1F, .5F);
				event.setCancelled(true);
				break;
			case CHEST:	
				//player.playSound(loc, Sound.BLOCK_NOTE_BASS, 1F, .5F);
				//event.setCancelled(true);		
				break;
			case CRAFTING:
				player.playSound(loc, Sound.BLOCK_NOTE_BASS, 1F, .5F);
				event.setCancelled(true);
				break;
			case CREATIVE:
				event.setCancelled(true);
				break;
			case DISPENSER:
				player.playSound(loc, Sound.BLOCK_NOTE_BASS, 1F, .5F);
				event.setCancelled(true);
				break;
			case DROPPER:
				player.playSound(loc, Sound.BLOCK_FIRE_EXTINGUISH, 1F, 1F);
				event.setCancelled(true);
				break;
			case ENCHANTING:
				player.playSound(loc, Sound.BLOCK_NOTE_BASS, 1F, .5F);
				event.setCancelled(true);
				break;
			case ENDER_CHEST:
				break;
			case FURNACE:
				player.playSound(loc, Sound.BLOCK_FIRE_EXTINGUISH, 1F, 1F);
				event.setCancelled(true);
				break;
			case HOPPER:
				player.playSound(loc, Sound.BLOCK_FIRE_EXTINGUISH, 1F, 1F);
				event.setCancelled(true);
				break;
			case MERCHANT:
				event.setCancelled(true);
				break;
			case PLAYER:
				break;
			case WORKBENCH:
				player.playSound(loc, Sound.BLOCK_ANVIL_USE, .5F, .1F);
				event.setCancelled(true);
				break;
			default:
				break;
			
			}
		}
	}
}