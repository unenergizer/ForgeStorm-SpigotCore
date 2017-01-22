package com.forgestorm.spigotcore.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.scheduler.BukkitRunnable;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.util.item.AttributeReader;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InventoryClick implements Listener {

	private final SpigotCore plugin;

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {

			Player player = (Player) event.getWhoClicked();
			SlotType slotType = event.getSlotType();

			///////////////////////////////////
			/// ARMOR / WEAPON RELATED CODE ///
			///////////////////////////////////

			//Check armor slots.
			if (slotType.equals(SlotType.ARMOR)) {
				//Armor was moved into our out of this slot.
				//Lets update the players attributes.
				delayedSlotUpdate(player, event.getCurrentItem().getType());
			}

			//Cancel events from these slot types.
			if (slotType.equals(SlotType.CRAFTING) || slotType.equals(SlotType.FUEL) || slotType.equals(SlotType.RESULT)) {
				event.setCancelled(true);
			}

			/*
			//Check if the player is throwing an soulbound item outside their inventory.
			if (slotType.equals(SlotType.OUTSIDE)) {
				//If player throws out an item that is soulbound, delete it.
				if (event.getCursor() != null && event.getCurrentItem() == null) {

					ItemStack item = event.getCursor();
					if (item.getItemMeta() != null && item != null) {
						//Test if the item is soulbound.
						if (SoulboundManager.isItemSoulbound(item)) {

							//Set the item to air.
							event.setCursor(new ItemStack(Material.AIR));

							//Show them the deleted message.
							SoulboundManager.showSoulboundMessage(player);
						}
					}
				}
			}
			 */

			//Check the armor slot if the player has shift clicked.
			//This will make check if the player has shift clicked
			//armor into the armor slot.
			if (event.isShiftClick()) {

				delayedSlotUpdate(player, event.getCurrentItem().getType());
			}

			/////////////////
			/// MENU CODE ///
			/////////////////
			if (event.getClickedInventory() == null) {
				return;
			}

			if (event.getCurrentItem().getType().equals(Material.COMPASS)) {
				event.setCancelled(true);
			}

			Menu menu = plugin.getProfileManager().getProfile(player).getCurrentMenu();

			if (menu == null) {
				return;
			}

			/*
				//Test if we can allow shift clicking of an item.
				if (event.getClick().equals(ClickType.SHIFT_LEFT) ||
						event.getClick().equals(ClickType.SHIFT_RIGHT)) {
					if (menu != null) {
						event.setCancelled(true);
					}
				}
			 */

			//Is the inventory clicked the menu?
			if (event.getClickedInventory().getType().equals(InventoryType.CHEST)) {
				menu.performAction(player, event.getSlot());

				if (!menu.isMovableSlot(event.getRawSlot())) {
					event.setCancelled(true);
				}
			}
		}
	}


	//It seems that the client responds better if we give it time to
	//set the armor, and then read the contents of the armor slots.
	private void delayedSlotUpdate(final Player player, final Material material) {
		//Play Sound
		if (material.equals(Material.LEATHER_BOOTS) || 
				material.equals(Material.LEATHER_CHESTPLATE) ||
				material.equals(Material.LEATHER_HELMET) ||
				material.equals(Material.LEATHER_LEGGINGS)) {
			player.playSound(player.getEyeLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 1, 1);
		}
		if (material.equals(Material.CHAINMAIL_BOOTS) || 
				material.equals(Material.CHAINMAIL_CHESTPLATE) ||
				material.equals(Material.CHAINMAIL_HELMET) ||
				material.equals(Material.CHAINMAIL_LEGGINGS)) {
			player.playSound(player.getEyeLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1, 1);
		}
		if (material.equals(Material.IRON_BOOTS) || 
				material.equals(Material.IRON_CHESTPLATE) ||
				material.equals(Material.IRON_HELMET) ||
				material.equals(Material.IRON_LEGGINGS)) {
			player.playSound(player.getEyeLocation(), Sound.ITEM_ARMOR_EQUIP_IRON, 1, 1);
		}
		if (material.equals(Material.DIAMOND_BOOTS) || 
				material.equals(Material.DIAMOND_CHESTPLATE) ||
				material.equals(Material.DIAMOND_HELMET) ||
				material.equals(Material.DIAMOND_LEGGINGS)) {
			player.playSound(player.getEyeLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1, 1);
		}
		if (material.equals(Material.GOLD_BOOTS) || 
				material.equals(Material.GOLD_CHESTPLATE) ||
				material.equals(Material.GOLD_HELMET) ||
				material.equals(Material.GOLD_LEGGINGS)) {
			player.playSound(player.getEyeLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1, 1);
		}
		
		//Lets start a repeating task
		new BukkitRunnable() {
			//taskID = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				//TODO: ItemLoreFactory.getInstance().applyHPBonus(player, true);
				new AttributeReader(plugin, player).readArmorAttributes(true);
			} //END Run method.
		}.runTaskLater(plugin, 5);
	}
}
