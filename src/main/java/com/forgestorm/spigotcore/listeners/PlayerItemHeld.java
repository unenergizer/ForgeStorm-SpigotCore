package com.forgestorm.spigotcore.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.util.item.AttributeReader;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerItemHeld implements Listener {

	public final SpigotCore plugin;

	@EventHandler
	public void onPlayerSwitchItem(PlayerItemHeldEvent event) {
        final Player player = event.getPlayer();
        
        delayedSlotUpdate(player);
        
        if (player.getInventory().getItem(event.getNewSlot()) != null) {
            ItemStack itemStack = player.getInventory().getItem(event.getNewSlot());
           
            if (itemStack.getType() == Material.BOW) {
            	
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.4F);
            }
            
            if (itemStack.getType() == Material.WOOD_SWORD || itemStack.getType() == Material.STONE_SWORD || itemStack.getType() == Material.IRON_SWORD
                    || itemStack.getType() == Material.DIAMOND_SWORD || itemStack.getType() == Material.GOLD_SWORD) {
            	                
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.4F);
            }
            
            if (itemStack.getType() == Material.WOOD_AXE || itemStack.getType() == Material.STONE_AXE 
            		|| itemStack.getType() == Material.IRON_AXE || itemStack.getType() == Material.DIAMOND_AXE
                    || itemStack.getType() == Material.GOLD_AXE) {
                            
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.4F);
            }
            
            if (itemStack.getType() == Material.WOOD_SPADE || itemStack.getType() == Material.STONE_SPADE 
            		|| itemStack.getType() == Material.IRON_SPADE || itemStack.getType() == Material.DIAMOND_SPADE
                    || itemStack.getType() == Material.GOLD_SPADE) {           	
                
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.4F);
            }
            
            if (itemStack.getType() == Material.WOOD_HOE || itemStack.getType() == Material.STONE_HOE 
            		|| itemStack.getType() == Material.IRON_HOE || itemStack.getType() == Material.DIAMOND_HOE
                    || itemStack.getType() == Material.GOLD_HOE) {
            	          
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.4F);
            }
        }
	}
	
	private void delayedSlotUpdate(final Player player) {
		//Lets start a repeating task
		new BukkitRunnable() {
		//taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				//TODO: ItemLoreFactory.getInstance().applyHPBonus(player, true);
				new AttributeReader(plugin, player).readWeaponAttributes(true);
			} //END Run method.
		}.runTaskLater(plugin, 5);
	}
}
