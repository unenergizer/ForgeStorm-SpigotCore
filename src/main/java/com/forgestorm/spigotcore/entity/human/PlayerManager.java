package com.forgestorm.spigotcore.entity.human;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;

import io.puharesource.mc.titlemanager.api.ActionbarTitleObject;
import lombok.Getter;

@Getter
public class PlayerManager {

	private final SpigotCore PLUGIN;

	private Map<UUID, PlayerProfileData> profiles = new HashMap<>();

	public PlayerManager(SpigotCore plugin) {
		PLUGIN = plugin;

		startPlayerAttributeUpdates();
		startActionBarTitleUpdates();
	}

	/**
	 * This will update action bar text showing the player useful info.
	 * 
	 * @param player The player what will see the data.
	 */
	private void updateActionBarText(Player player) {
		PlayerProfileData profile = getPlayerProfile(player);

		//Show player important attributes!
		String hp = Integer.toString((int) profile.getHealth());
		String maxHP = Integer.toString((int) profile.getMaxHealth());
		String level = Integer.toString(profile.getPlayerLevel());
		String expPercent = Double.toString(profile.getExpPercent());

		new ActionbarTitleObject(ChatColor.GREEN + "" + ChatColor.BOLD + "HP" 
				+ ChatColor.GRAY + ChatColor.BOLD + ": " 
				+ ChatColor.WHITE + ChatColor.BOLD + hp 
				+ ChatColor.GREEN + ChatColor.BOLD + "/" 
				+ ChatColor.WHITE + ChatColor.BOLD + maxHP 
				+ ChatColor.AQUA + ChatColor.BOLD + "   Level" 
				+ ChatColor.GRAY + ChatColor.BOLD + ": "
				+ ChatColor.WHITE + ChatColor.BOLD + level
				+ ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "   XP" 
				+ ChatColor.GRAY + ChatColor.BOLD + ": " 
				+ ChatColor.WHITE + ChatColor.BOLD + expPercent 
				+ ChatColor.LIGHT_PURPLE + ChatColor.BOLD +  "%").send(player);
	}

	
	private void startActionBarTitleUpdates() {
		//for (Player player: Bukkit.getOnlinePlayers())
		new BukkitRunnable() {

			@Override
			public void run() {
				for (Player player: Bukkit.getOnlinePlayers()) {

					if (getPlayerProfile(player).isLoaded()) {

						updateActionBarText(player);
					}
				}
			}
		}.runTaskTimerAsynchronously(PLUGIN, 0, 1);
	}

	private void startPlayerAttributeUpdates() {
		new BukkitRunnable() {

			@Override
			public void run() {
				for (Player player: Bukkit.getOnlinePlayers()) {
					PlayerProfileData profile = getPlayerProfile(player);

					if (profile.isLoaded()) {
						
						adjustHealthRegen(player, profile);
						adjustEnergyRegen(player, profile);
					}
				}
			}
		}.runTaskTimerAsynchronously(PLUGIN, 0, 20);
	}
	
	private void adjustHealthRegen(Player player, PlayerProfileData profile) {
		if (profile.isInCombat()) {
			int combatTime = profile.getCombatTime() - 1;
			profile.setCombatTime(combatTime);
			
			if (combatTime == 0) {
				player.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "You have left combat.");
			}
			
		} else {
			double currentHP = profile.getHealth();
			double maxHealthPoints = profile.getMaxHealth();
			double healthRegen = profile.getArmorHealthRegen();
			double healthPointsFinal = currentHP + healthRegen;

			//Set the players health.
			if (healthPointsFinal >= maxHealthPoints) {
				profile.setHealth(maxHealthPoints);
				currentHP = maxHealthPoints;
			} else {
				profile.setHealth(healthPointsFinal);
			}

			double healthPercent = healthPointsFinal / maxHealthPoints;
			double hpDisplay = healthPercent * 20;

			//Set hearts
			if (hpDisplay > 20) {
				player.setHealth(20);
			} else if (hpDisplay <= 0) {
				player.setHealth(1);
			} else {
				player.setHealth(hpDisplay);
			}
			
			//Update the players HP under their name tag.
			PLUGIN.getScoreboardManager().updatePlayerHP(profile, player);
		}
	}
	
	private void adjustEnergyRegen(Player player, PlayerProfileData profile) {
		double maxEnergy = 100;
		double energy = profile.getEnergy() + profile.getArmorEnergyRegen();
		double energyPercent = energy / maxEnergy;

		//Set the players health.
		if (energy > maxEnergy) {
			profile.setEnergy(maxEnergy);
			energy = maxEnergy;
		} else {
			profile.setEnergy(energy);
		}

		//Set energy bar
		player.setLevel((int) energy);
		player.setExp((float) (energyPercent));
	}

	public void addPlayerProfile(Player player, PlayerProfileData profile) {
		profiles.put(player.getUniqueId(), profile);
	}

	public PlayerProfileData getPlayerProfile(Player player) {
		return profiles.get(player.getUniqueId());
	}

	public PlayerProfileData getRemovedProfile(Player player) {
		return profiles.remove(player.getUniqueId());
	}
}
