package com.forgestorm.spigotcore.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.combat.Fatigue;
import com.forgestorm.spigotcore.combat.MonsterVersusMonster;
import com.forgestorm.spigotcore.combat.MonsterVersusPlayer;
import com.forgestorm.spigotcore.combat.PlayerVersusMonster;
import com.forgestorm.spigotcore.combat.PlayerVersusPlayer;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;
import com.forgestorm.spigotcore.world.instance.PlayerRealmManager;

import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;

@AllArgsConstructor
public class EntityDamageByEntity implements Listener {

	private final SpigotCore PLUGIN;

	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

		//Player realm / Hologram armor stand
		if (event.getEntity() instanceof ArmorStand && event.getDamager() instanceof Player) {
			PlayerRealmManager prm = PLUGIN.getPlayerRealmManager();
			Player player = (Player) event.getDamager();
			if (prm.hasRealm(player)) {
				prm.removePlayerRealmAtLocation(player, event.getEntity().getLocation());
				return;
			}
		}
		
		//Entity vs Mount
		if (event.getEntity() instanceof Horse || event.getEntity() instanceof Pig) {
			Entity entity = event.getEntity();
			UUID uuid = entity.getUniqueId();
			boolean isInMountMap = PLUGIN.getMountManager().containsMount(uuid);

			Bukkit.broadcastMessage(ChatColor.RED + "Damage Taken. " + ChatColor.BOLD + "Removing mount" + ChatColor.RED + "..");

			if (isInMountMap) {
				PLUGIN.getMountManager().removeMount(uuid);
				return;
			} else {
				entity.remove();
				return;
			}
		}

		//Cancel regular mc damage.
		event.setDamage(0);

		//Arrow damage
		if (event.getDamager() instanceof Projectile) {
			Projectile projectile = (Projectile) event.getDamager();

			if (projectile.getShooter() instanceof LivingEntity && event.getEntity() instanceof LivingEntity) {
				LivingEntity damager = (LivingEntity) projectile.getShooter();
				LivingEntity defender = (LivingEntity) event.getEntity();

				entityTester(damager, defender);
			}
		}

		//Make sure we only run combat code on living entities.
		if (event.getDamager() instanceof LivingEntity && event.getEntity() instanceof LivingEntity) {
			LivingEntity damager = (LivingEntity) event.getDamager();
			LivingEntity defender = (LivingEntity) event.getEntity();

			entityTester(damager, defender);
		}
	}

	private void entityTester(LivingEntity damager, LivingEntity defender) {

		if (!(defender instanceof ArmorStand)) {
			
			//Player versus Player
			if (damager instanceof Player && defender instanceof Player) {
				
				//Check to see if entity is a Citizens NPC.
				if (defender.hasMetadata("NPC")) {

					PLUGIN.getCitizenManager().onCitizenInteract((Player) damager, (Player) defender);
					return;
				}
				
				startCombat((Player) damager);
				startCombat((Player) defender);
				startFatigue((Player) damager);
				new PlayerVersusPlayer(PLUGIN, damager, defender).doCalculations();
				return;
			}

			//Mob versus Mob
			if (!(damager instanceof Player) && !(defender instanceof Player)) {
				new MonsterVersusMonster(PLUGIN, damager, defender).doCalculations();
				return;
			}

			//Mob versus Player
			if (!(damager instanceof Player) && defender instanceof Player) {
				startCombat((Player) defender);
				new MonsterVersusPlayer(PLUGIN, damager, defender).doCalculations();
				return;
			}

			//Player versus Mob, Entity, etc
			if (damager instanceof Player && !(defender instanceof Player)) {
				startCombat((Player) damager);
				startFatigue((Player) damager);

				boolean isInMap = PLUGIN.getEntityManager().getRpgEntity().containsKey(defender.getUniqueId());

				if (isInMap) {
					//Entity is in the map, lets do our damage code.
					new PlayerVersusMonster(PLUGIN, damager, defender).doCalculations();
				} else {
					//Entity does not exist in the monster mappings.
					defender.remove();
				}
			}
		}
	}

	private void startCombat(Player player) {
		PlayerProfileData profile = PLUGIN.getPlayerManager().getPlayerProfile(player);
		if (profile.getCombatTime() == 0) {
			player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You have entered combat!");
		}
		profile.putInCombat();
	}

	private void startFatigue(Player damager) {
		PlayerProfileData profile = PLUGIN.getPlayerManager().getPlayerProfile(damager);
		if (profile.getEnergy() == 0) {
			damager.playSound(damager.getLocation(), Sound.ENTITY_WOLF_PANT, 10F, 1.5F);	
		}
		profile.addFatigue(new Fatigue().getEnergyCost(damager.getInventory().getItemInMainHand().getType()));
	}
}
