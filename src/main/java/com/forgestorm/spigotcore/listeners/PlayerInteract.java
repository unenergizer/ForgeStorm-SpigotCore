package com.forgestorm.spigotcore.listeners;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.combat.Fatigue;
import com.forgestorm.spigotcore.menus.MainMenu;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;
import com.forgestorm.spigotcore.util.item.AttributeReader;
import com.forgestorm.spigotcore.world.instance.Realm;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class PlayerInteract implements Listener {
	
	private final SpigotCore plugin;
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {	
		Player player = event.getPlayer();
		Block block = event.getClickedBlock();
		
		//Player to place realm portal (only if they are in the main world).
		if (player.isSneaking() && player.getWorld().equals(Bukkit.getWorlds().get(0))) {
			if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				if (event.getItem() != null && event.getItem().getType().equals(Material.COMPASS)) {
					plugin.getRealmManager().addPlayerRealm(player, block.getLocation());
					return;
				}
			}
		}

		//Check for realm portal move.
		if (player.getWorld().getName().equals(player.getUniqueId().toString())) {
			if (player.isSneaking() && event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
				if (block.getType() != Material.PORTAL) {
					if (event.getItem() != null && event.getItem().getType().equals(Material.COMPASS)) {
						Realm realm = plugin.getRealmManager().getPlayerRealm(player);

						//Remove Old Realm inside Portal
						realm.getInsideBlockTop().setType(Material.AIR);
						realm.getInsideBlockBottom().setType(Material.AIR);

						//Set New Realm inside Portal
						realm.setPortalInsideLocation(block.getLocation().add(0, 1, 0));
						realm.setPlayerInsidePortal();

						//Save location to player profile.
                        double x = realm.getPortalInsideLocation().getX();
                        double y = realm.getPortalInsideLocation().getY();
                        double z = realm.getPortalInsideLocation().getZ();
                        plugin.getProfileManager().getProfile(player).setRealmInsideLocation(x + "/" + y + "/" + z);
						return;
					}
				}
			}
		}
		
		//Dragon Egg Teleport game.
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block.getType().equals(Material.DRAGON_EGG) 
				|| event.getAction().equals(Action.LEFT_CLICK_BLOCK) && block.getType().equals(Material.DRAGON_EGG)) {

			//Cancel the default egg respawn.
			event.setCancelled(true);
			
			//Toggle the egg click.
			plugin.getDragonEggTP().toggleEggClick(player, event.getClickedBlock().getLocation());
		}

		//Armor and Weapon Actions
		if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK) ||
				event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			if (event.getItem() != null) {

				switch(event.getItem().getType()) {

				///////////
				// Armor //
				///////////

				//Tier #1
				case LEATHER_BOOTS:
					delayedSlotUpdate(player);
					break;
				case LEATHER_CHESTPLATE:
					delayedSlotUpdate(player);
					break;
				case LEATHER_HELMET:
					delayedSlotUpdate(player);
					break;
				case LEATHER_LEGGINGS:
					delayedSlotUpdate(player);
					break;

					//Tier #2	
				case CHAINMAIL_BOOTS:
					delayedSlotUpdate(player);
					break;
				case CHAINMAIL_CHESTPLATE:
					delayedSlotUpdate(player);
					break;
				case CHAINMAIL_HELMET:
					delayedSlotUpdate(player);
					break;
				case CHAINMAIL_LEGGINGS:
					delayedSlotUpdate(player);
					break;

					//Tier #3	
				case IRON_BOOTS:
					delayedSlotUpdate(player);
					break;
				case IRON_CHESTPLATE:
					delayedSlotUpdate(player);
					break;
				case IRON_HELMET:
					delayedSlotUpdate(player);
					break;
				case IRON_LEGGINGS:
					delayedSlotUpdate(player);
					break;

					//Tier #4
				case GOLD_BOOTS:
					delayedSlotUpdate(player);
					break;
				case GOLD_CHESTPLATE:
					delayedSlotUpdate(player);
					break;
				case GOLD_HELMET:
					delayedSlotUpdate(player);
					break;
				case GOLD_LEGGINGS:
					delayedSlotUpdate(player);
					break;

					//Tier #5
				case DIAMOND_BOOTS:
					delayedSlotUpdate(player);
					break;
				case DIAMOND_CHESTPLATE:
					delayedSlotUpdate(player);
					break;
				case DIAMOND_HELMET:
					delayedSlotUpdate(player);
					break;
				case DIAMOND_LEGGINGS:
					delayedSlotUpdate(player);
					break;

					//////////
					// Axes //
					//////////
				case WOOD_AXE: //Tier #1
					startFatigue(player);
					break;
				case STONE_AXE: //Tier #2
					startFatigue(player);
					break;
				case IRON_AXE: //Tier #3
					startFatigue(player);
					break;	
				case GOLD_AXE: //Tier #4
					startFatigue(player);
					break;
				case DIAMOND_AXE: //Tier #5
					startFatigue(player);
					break;		

					///////////////////////
					// Polearms / Spears //
					///////////////////////
				case WOOD_SPADE: //Tier #1
					startFatigue(player);
					break;
				case STONE_SPADE: //Tier #2
					startFatigue(player);
					break;
				case IRON_SPADE: //Tier #3
					startFatigue(player);
					break;
				case GOLD_SPADE: //Tier #4
					startFatigue(player);
					break;		
				case DIAMOND_SPADE: //Tier #5
					startFatigue(player);
					break;

					////////////
					// Swords //
					////////////
				case WOOD_SWORD: //Tier #1
					startFatigue(player);
					break;
				case STONE_SWORD: //Tier #2
					startFatigue(player);
					break;
				case IRON_SWORD: //Tier #3
					startFatigue(player);
					break;
				case GOLD_SWORD: //Tier #4
					startFatigue(player);
					break;
				case DIAMOND_SWORD: //Tier #5
					startFatigue(player);
					break;

					///////////
					// Wands //
					///////////
				case WOOD_HOE: //Tier #1
					startFatigue(player);
					break;
				case STONE_HOE: //Tier #2
					startFatigue(player);
					break;
				case IRON_HOE: //Tier #3
					startFatigue(player);
					break;
				case GOLD_HOE: //Tier #4
					startFatigue(player);
					break;
				case DIAMOND_HOE: //Tier #5
					startFatigue(player);
					break;

					///////////////
					// Craftable //
					///////////////
				case ANVIL:
					//Disable item renaming.
					event.setCancelled(true);
					break;
				case COMPASS:
					//Open players main menu.
					new MainMenu(plugin).open(player);
					break;
				case WORKBENCH:
					//Disable item crafting.
					event.setCancelled(true);
					break;
				default:
					break;
				}
			}
		}
	}
	
	private void startFatigue(Player player) {
		PlayerProfileData profile = plugin.getPlayerManager().getPlayerProfile(player);
		if (profile.getEnergy() == 0) {
			player.playSound(player.getLocation(), Sound.ENTITY_WOLF_PANT, 10F, 1.5F);	
		}
		profile.addFatigue(new Fatigue().getEnergyCost(player.getInventory().getItemInMainHand().getType()));
	}
	
	//It seems that the client responds better if we give it time to
	//set the armor, and then read the contents of the armor slots.
	private void delayedSlotUpdate(final Player player) {
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
