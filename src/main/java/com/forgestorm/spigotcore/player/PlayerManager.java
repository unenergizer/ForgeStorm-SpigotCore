package com.forgestorm.spigotcore.player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class PlayerManager {

	private final SpigotCore PLUGIN;

    private final Map<UUID, PlayerProfileData> profiles = new HashMap<>();
    private final Map<UUID, Integer> regenerationDelay = new HashMap<>();

	public PlayerManager(SpigotCore plugin) {
		PLUGIN = plugin;
        EntityType.pig
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
        boolean isLooking = PLUGIN.getLocationTrackingManager().getTargetLocations().containsKey(player.getUniqueId());

        //Show player health if they are in combart and if they are not tracking anything.
        if (profile.isInCombat() || !isLooking) {
            String hp = Integer.toString((int) profile.getHealth());
            String maxHP = Integer.toString(profile.getMaxHealth());

            PLUGIN.getTitleManagerAPI().sendActionbar(player, ChatColor.RED + "" + ChatColor.BOLD + "\u2764 "
                    + ChatColor.WHITE + ChatColor.BOLD + hp
                    + ChatColor.RED + ChatColor.BOLD + "/"
                    + ChatColor.WHITE + ChatColor.BOLD + maxHP);
        }
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

	private long time = 0;

	private void startPlayerAttributeUpdates() {
		new BukkitRunnable() {

			@Override
			public void run() {
				for (Player player: Bukkit.getOnlinePlayers()) {
					PlayerProfileData profile = getPlayerProfile(player);

					if (profile.isLoaded()) {

					    //Update energy/fatigue every tick.
                        adjustEnergyRegen(player, profile);

                        //Update every 20 ticks.
					    if (time++ % 5 == 0) {
					        adjustHealthRegen(player, profile);
                            PLUGIN.getTarkanScoreboard().updateScoreboard(player);
                        }
                    }
				}
			}
		}.runTaskTimerAsynchronously(PLUGIN, 0, 1);
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
			} else {
				profile.setHealth(healthPointsFinal);
			}

			double healthPercent = healthPointsFinal / maxHealthPoints;
			double hpDisplay = healthPercent;

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
        float maxEnergy = profile.getMaxEnergy(); //100

        float energyToAdd = profile.getArmorEnergyRegen() / maxEnergy;
        float valueStoredCurrently = profile.getEnergy();

        float baseAmount = profile.getBaseEnergyRegen() / maxEnergy;

        float nextLevelNoTrun = energyToAdd + valueStoredCurrently + baseAmount;
        float expAmount = nextLevelNoTrun / 100.0f;

        int nextLevelTrun = (int)(nextLevelNoTrun);
        if (nextLevelTrun >= 100) {
            profile.setEnergy(maxEnergy); //Num between 0-100;
            player.setLevel((int) maxEnergy); //Num between 0-100;
            player.setExp(1.0f); //Num between 0-1f;
        } else {

            UUID uuid = player.getUniqueId();
            boolean alreadyInMap = false;

            if (!regenerationDelay.containsKey(uuid)) {

                profile.setEnergy(nextLevelNoTrun); //Num between 0-100;
                player.setLevel(nextLevelTrun); //Num between 0-100;
                player.setExp(expAmount); //Num between 0-1f;
            } else {

                alreadyInMap = true;

                int amount = regenerationDelay.get(uuid) + 1;

                regenerationDelay.replace(uuid, amount);

                if (amount > 20 * 2) {
                    regenerationDelay.remove(uuid);
                }
            }

            if (nextLevelTrun == 0 && !alreadyInMap) {
                regenerationDelay.put(uuid, 0);
            }
        }
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
