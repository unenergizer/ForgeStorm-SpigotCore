package com.forgestorm.spigotcore.redis;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.Redis;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Getter
public class RedisProfileManager {

	private final SpigotCore PLUGIN;
	private final HashMap<String, PlayerProfileData> profiles = new HashMap<>();

	public RedisProfileManager(SpigotCore plugin) {
		PLUGIN = plugin;

		//If the server reloads, this will load player profiles.
		for (Player players : Bukkit.getOnlinePlayers()) {
			PlayerProfileData data = loadProfile(players);
			new SetupNetworkPlayer(plugin, players, data).runTaskTimer(PLUGIN, 0, 20);
		}
	}

	/**
	 * Called when the server is going to shutdown.
	 */
	public void disable() {
		for (Player players : Bukkit.getOnlinePlayers()) {
			unloadProfile(players);
		}
	}

	private void createProfile(Player player) {
		//Prevent NPC's from getting profiles.
		if (player.hasMetadata("NPC")) {
			return;
		}

		String uuid = player.getUniqueId().toString();
		PlayerProfileData profile = profiles.get(uuid);

		// DEFAULT VALUES
		int userGroup = 0;
		boolean isModerator = false;
		boolean isAdmin = false;
		boolean isBanned = false;
		int warningPoints = 0;
		int bankCurrency = 0;
		int currency = 0;
		int lifetimeCurrency = 0;
		int premiumCurrency = 0;
		int lifetimePremiumCurrency = 0;
		long playTime = 0;
		long levelTime = 0;
		long experience = 1;
		List<String> achievements = new ArrayList<>();
		List<String> completedTutorials = new ArrayList<>();
		List<String> collectedRecipes = new ArrayList<>();
		
		//RPG SETTINGS
		double health = profile.getBaseMaxHealth();
		double energy = profile.getBaseEnergyRegen();
		String serializedInventory = "";

		//Player Default Settings
		boolean chatFilter = true;
		boolean friendRequests = true;
		boolean guildRequests = true;
		boolean partyRequests = true;
		boolean tradeRequests = true;
		boolean toggleDebug = false;

		//Player Default Profession Exp
		// -1 indicates that they have not purchased the
		// profession
		long farmingExp = -1;
		long fishingExp = -1;
		long lumberjackExp = -1;
		long miningExp = -1;

		//Realm Default Settings
		String realmTitle = "Welcome to " + player.getName() + "'s realm!";
		int realmTier = 1;
		String realmInsideLocation = "8/95/8";

		////////////////////////////////////
		//// LOCAL PROFILE DATA  ///////////
		////////////////////////////////////

		profile.setUserGroup(userGroup);
		profile.setModerator(isModerator);
		profile.setAdmin(isAdmin);
		profile.setBanned(isBanned);
		profile.setWarningPoints(warningPoints);
		profile.setBankCurrency(bankCurrency);
		profile.setCurrency(currency);
		profile.setLifetimeCurrency(lifetimeCurrency);
		profile.setPremiumCurrency(premiumCurrency);
		profile.setLifetimePremiumCurrency(lifetimePremiumCurrency);
		profile.setPlayTime(playTime);
		profile.setLevelTime(levelTime);
		profile.setExperience(experience);
		profile.setAchievements(achievements);
		profile.setOldAchievements(achievements);
		profile.setCompletedTutorials(completedTutorials);
		profile.setOldCompletedTutorials(completedTutorials);
		profile.setCollectedRecipes(collectedRecipes);
		profile.setOldCollectedRecipes(collectedRecipes);

		profile.setHealth(health);
		profile.setEnergy(energy);
		profile.setSerializedInventory(serializedInventory);

		profile.setChatFilter(chatFilter);
		profile.setFriendRequests(friendRequests);
		profile.setGuildRequests(guildRequests);
		profile.setPartyRequests(partyRequests);
		profile.setTradeRequests(tradeRequests);
		profile.setTradeRequests(toggleDebug);

		profile.setFarmingExperience(farmingExp);
		profile.setFishingExperience(fishingExp);
		profile.setLumberjackExperience(lumberjackExp);
		profile.setMiningExperience(miningExp);

		profile.setRealmTier(realmTier);
		profile.setRealmTitle(realmTitle);
		profile.setRealmInsideLocation(realmInsideLocation);

		////////////////////////////////////
		//// REDIS FIRST TIME SAVE  ////////
		////////////////////////////////////
		try (Jedis jedis = PLUGIN.getRedisService().getPoolResource()) {
			Pipeline pipeline = jedis.pipelined();

			//Player Info
			pipeline.set(Redis.PROFILE_USER_GROUP.toString(uuid), Integer.toString(userGroup));
			pipeline.set(Redis.PROFILE_IS_MODERATOR.toString(uuid), Boolean.toString(isModerator));
			pipeline.set(Redis.PROFILE_IS_ADMIN.toString(uuid), Boolean.toString(isAdmin));
			pipeline.set(Redis.PROFILE_IS_BANNED.toString(uuid), Boolean.toString(isBanned));
			pipeline.set(Redis.PROFILE_WARNING_POINTS.toString(uuid), Integer.toString(warningPoints));
			pipeline.set(Redis.PROFILE_CURRENCY_BANK_GEMS.toString(uuid), Integer.toString(bankCurrency));
			pipeline.set(Redis.PROFILE_CURRENCY_GEMS.toString(uuid), Integer.toString(currency));
			pipeline.set(Redis.PROFILE_CURRENCY_GEMS_LIFE_TIME_.toString(uuid), Integer.toString(lifetimeCurrency));
			pipeline.set(Redis.PROFILE_CURRENCY_PREMIUM.toString(uuid), Integer.toString(premiumCurrency));
			pipeline.set(Redis.PROFILE_CURRENCY_PREMIUM_LIFE_TIME.toString(uuid), Integer.toString(lifetimePremiumCurrency));
			pipeline.set(Redis.PROFILE_ONLINE_TIME.toString(uuid), Long.toString(playTime));
			pipeline.set(Redis.PROFILE_ONLINE_LEVEL_TIME.toString(uuid), Long.toString(levelTime));
			pipeline.set(Redis.PROFILE_EXPERIENCE.toString(uuid), Long.toString(experience));
			pipeline.lpush(Redis.PROIFLE_ACHIEVEMENTS.toString(uuid), achievements.toArray(new String[0]));
			pipeline.lpush(Redis.PROFILE_COMPLETED_TUTORIALS.toString(uuid), completedTutorials.toArray(new String[0]));
			pipeline.lpush(Redis.PROFILE_COLLECTED_RECIPES.toString(uuid), collectedRecipes.toArray(new String[0]));
			
			//RPG INFO
			pipeline.set(Redis.PROFILE_LOGOUT_HP.toString(uuid), Double.toString(health));
			pipeline.set(Redis.PROFILE_LOGOUT_ENERGY.toString(uuid), Double.toString(energy));
			pipeline.set(Redis.PROFILE_PLAYER_INVENTORY.toString(uuid), serializedInventory);

			//Player Settings
			pipeline.set(Redis.SETTINGS_P_CHATFILTER.toString(uuid), Boolean.toString(chatFilter));
			pipeline.set(Redis.SETTINGS_P_FRIEND_REQUEST.toString(uuid), Boolean.toString(friendRequests));
			pipeline.set(Redis.SETTINGS_P_GUILD_REQUEST.toString(uuid), Boolean.toString(guildRequests));
			pipeline.set(Redis.SETTINGS_P_PARTY_REQUEST.toString(uuid), Boolean.toString(partyRequests));
			pipeline.set(Redis.SETTINGS_P_TRADE_REQUEST.toString(uuid), Boolean.toString(tradeRequests));
			pipeline.set(Redis.SETTINGS_P_DEBUG_MESSAGES.toString(uuid), Boolean.toString(toggleDebug));

			//Profession info
			pipeline.set(Redis.PROFESSION_FARMING.toString(uuid), Long.toString(farmingExp));
			pipeline.set(Redis.PROFESSION_FISHING.toString(uuid), Long.toString(fishingExp));
			pipeline.set(Redis.PROFESSION_LUMBERJACK.toString(uuid), Long.toString(lumberjackExp));
			pipeline.set(Redis.PROFESSION_MINING.toString(uuid), Long.toString(miningExp));

			//Realm data
			pipeline.set((Redis.REALM_TIER.toString(uuid)), Integer.toString(realmTier));
			pipeline.set((Redis.REALM_TITLE.toString(uuid)), realmTitle);
			pipeline.set((Redis.REALM_PORTAL_INSIDE_LOCATION.toString(uuid)), realmInsideLocation);

			pipeline.sync();
		}
	}

	/**
	 * Called when a player log's into the server.
	 * @param player The player who joined the server.
	 * @return Returns a loaded profile.
	 */
	public PlayerProfileData loadProfile(Player player) {

		//Prevent NPC's from getting profiles.
		if (player.hasMetadata("NPC")) {
			return null;
		}

		PlayerProfileData profile = new PlayerProfileData(PLUGIN, player);
		String uuid = player.getUniqueId().toString();	
		String newPlayerTest;

		//Save the profile for access later.
		profiles.put(uuid, profile);

		//If the player exists, the string will not be null.
		try (Jedis jedis = PLUGIN.getRedisService().getPoolResource()) {
			newPlayerTest = jedis.get(Redis.SETTINGS_P_CHATFILTER.toString(uuid));
		}

		//Create or load player?
		if (newPlayerTest == null) {
			createProfile(player);

		} else  {
			try (Jedis jedis = PLUGIN.getRedisService().getPoolResource()) {

				//Player Info
				profile.setUserGroup(Integer.parseInt(jedis.get(Redis.PROFILE_USER_GROUP.toString(uuid))));
				profile.setModerator(Boolean.valueOf(jedis.get(Redis.PROFILE_IS_MODERATOR.toString(uuid))));
				profile.setAdmin(Boolean.valueOf(jedis.get(Redis.PROFILE_IS_ADMIN.toString(uuid))));
				profile.setBanned(Boolean.valueOf(jedis.get(Redis.PROFILE_IS_BANNED.toString(uuid))));
				profile.setWarningPoints(Integer.parseInt(jedis.get(Redis.PROFILE_WARNING_POINTS.toString(uuid))));
				profile.setBankCurrency(Integer.parseInt(jedis.get(Redis.PROFILE_CURRENCY_BANK_GEMS.toString(uuid))));
				profile.setCurrency(Integer.parseInt(jedis.get(Redis.PROFILE_CURRENCY_GEMS.toString(uuid))));
				profile.setLifetimeCurrency(Integer.parseInt(jedis.get(Redis.PROFILE_CURRENCY_GEMS_LIFE_TIME_.toString(uuid))));
				profile.setPremiumCurrency(Integer.parseInt(jedis.get(Redis.PROFILE_CURRENCY_PREMIUM.toString(uuid))));
				profile.setLifetimePremiumCurrency(Integer.parseInt(jedis.get(Redis.PROFILE_CURRENCY_PREMIUM_LIFE_TIME.toString(uuid))));
				profile.setPlayTime(Long.parseLong(jedis.get((Redis.PROFILE_ONLINE_TIME.toString(uuid)))));
				profile.setLevelTime(Long.parseLong(jedis.get((Redis.PROFILE_ONLINE_LEVEL_TIME.toString(uuid)))));
				profile.setExperience(Long.valueOf(jedis.get(Redis.PROFILE_EXPERIENCE.toString(uuid))));
				
				//RPG Info
				profile.setHealth(Double.valueOf(jedis.get(Redis.PROFILE_LOGOUT_HP.toString(uuid))));
				profile.setEnergy(Double.valueOf(jedis.get(Redis.PROFILE_LOGOUT_ENERGY.toString(uuid))));
				profile.setSerializedInventory(jedis.get(Redis.PROFILE_PLAYER_INVENTORY.toString(uuid)));

				//We keep two lists to compare/contrast elements in it for
				//saving the profile later, when the player logs out.
				profile.setAchievements(jedis.lrange(Redis.PROIFLE_ACHIEVEMENTS.toString(uuid), 0, -1));
				profile.setOldAchievements(jedis.lrange(Redis.PROIFLE_ACHIEVEMENTS.toString(uuid), 0, -1));
				profile.setCompletedTutorials(jedis.lrange(Redis.PROFILE_COMPLETED_TUTORIALS.toString(uuid), 0, -1));
				profile.setOldCompletedTutorials(jedis.lrange(Redis.PROFILE_COMPLETED_TUTORIALS.toString(uuid), 0, -1));
				profile.setCollectedRecipes(jedis.lrange(Redis.PROFILE_COLLECTED_RECIPES.toString(uuid), 0, -1));
				profile.setOldCollectedRecipes(jedis.lrange(Redis.PROFILE_COLLECTED_RECIPES.toString(uuid), 0, -1));

				//Player Settings
				profile.setChatFilter(Boolean.parseBoolean(jedis.get(Redis.SETTINGS_P_CHATFILTER.toString(uuid))));
				profile.setFriendRequests(Boolean.parseBoolean(jedis.get(Redis.SETTINGS_P_FRIEND_REQUEST.toString(uuid))));
				profile.setGuildRequests(Boolean.parseBoolean(jedis.get(Redis.SETTINGS_P_GUILD_REQUEST.toString(uuid))));
				profile.setPartyRequests(Boolean.parseBoolean(jedis.get(Redis.SETTINGS_P_PARTY_REQUEST.toString(uuid))));
				profile.setTradeRequests(Boolean.parseBoolean(jedis.get(Redis.SETTINGS_P_TRADE_REQUEST.toString(uuid))));
				profile.setToggleDebug(Boolean.parseBoolean(jedis.get(Redis.SETTINGS_P_DEBUG_MESSAGES.toString(uuid))));

				//Player profession
				profile.setFarmingExperience(Long.valueOf(jedis.get(Redis.PROFESSION_FARMING.toString(uuid))));
				profile.setFishingExperience(Long.valueOf(jedis.get(Redis.PROFESSION_FISHING.toString(uuid))));
				profile.setLumberjackExperience(Long.valueOf(jedis.get(Redis.PROFESSION_LUMBERJACK.toString(uuid))));
				profile.setMiningExperience(Long.valueOf(jedis.get(Redis.PROFESSION_MINING.toString(uuid))));

				//Realm Data
				profile.setRealmTier(Integer.valueOf(jedis.get((Redis.REALM_TIER.toString(uuid)))));
				profile.setRealmTitle(jedis.get(Redis.REALM_TITLE.toString(uuid)));
				profile.setRealmInsideLocation(jedis.get(Redis.REALM_PORTAL_INSIDE_LOCATION.toString(uuid)));
			}
		}

		//Profile has been loaded.
		profile.setLoaded(true);

		return profile;
	}

	/**
	 * Called when a player logs out.
	 * @param player The player who logged out.
	 */
	public void unloadProfile(Player player) {
		//Prevent NPC's from unloading profiles.
		if (player.hasMetadata("NPC")) {
			return;
		}

		String uuid = player.getUniqueId().toString();
		PlayerProfileData profile = profiles.get(uuid);
		SimpleDateFormat formatter = new SimpleDateFormat("M-dd-yyyy hh:mm:ss");
		String date = formatter.format(new Date());
		long currentTime = System.currentTimeMillis() / 1000;
		long totalTime = (currentTime - profile.getLoginTime()) + profile.getPlayTime();
		long levelTime = (currentTime - profile.getLastLevelTime()) + profile.getLevelTime();

		try (Jedis jedis = PLUGIN.getRedisService().getPoolResource()) {
			Pipeline pipeline = jedis.pipelined();

			pipeline.set(Redis.PROFILE_ID.toString(uuid), Integer.toString(profile.getUserGroup()));
			pipeline.set(Redis.PROFILE_USER_GROUP.toString(uuid), Integer.toString(profile.getUserGroup()));
			pipeline.set(Redis.PROFILE_IS_MODERATOR.toString(uuid), Boolean.toString(profile.isModerator()));
			pipeline.set(Redis.PROFILE_IS_ADMIN.toString(uuid), Boolean.toString(profile.isAdmin()));
			pipeline.set(Redis.PROFILE_IS_BANNED.toString(uuid), Boolean.toString(profile.isBanned()));
			pipeline.set(Redis.PROFILE_WARNING_POINTS.toString(uuid), Integer.toString(profile.getWarningPoints()));
			pipeline.set(Redis.PROFILE_CURRENCY_BANK_GEMS.toString(uuid), Integer.toString(profile.getBankCurrency()));
			pipeline.set(Redis.PROFILE_CURRENCY_GEMS.toString(uuid), Integer.toString(profile.getCurrency()));
			pipeline.set(Redis.PROFILE_CURRENCY_GEMS_LIFE_TIME_.toString(uuid), Integer.toString(profile.getLifetimeCurrency()));
			pipeline.set(Redis.PROFILE_CURRENCY_PREMIUM.toString(uuid), Integer.toString(profile.getPremiumCurrency()));
			pipeline.set(Redis.PROFILE_CURRENCY_PREMIUM_LIFE_TIME.toString(uuid), Integer.toString(profile.getLifetimePremiumCurrency()));
			pipeline.set(Redis.PROIFLE_LASTACTIVITY.toString(uuid), date);
			pipeline.set(Redis.PROFILE_ONLINE_TIME.toString(uuid), Long.toString(totalTime));
			pipeline.set(Redis.PROFILE_ONLINE_LEVEL_TIME.toString(uuid), Long.toString(levelTime));
			pipeline.set(Redis.PROFILE_EXPERIENCE.toString(uuid), Long.toString(profile.getExperience()));

			//RPG INFO
			pipeline.set(Redis.PROFILE_LOGOUT_HP.toString(uuid), Double.toString(profile.getHealth()));
			pipeline.set(Redis.PROFILE_LOGOUT_ENERGY.toString(uuid), Double.toString(profile.getEnergy()));
			pipeline.set(Redis.PROFILE_PLAYER_INVENTORY.toString(uuid), profile.getSerializedInventory());
			
			//////////////////////////////////////////////
			/// BEGIN LIST COMPARE/CONTRAST FOR SAVING ///
			//////////////////////////////////////////////
			
			//PREVENT LIST DUPLICATES
			for (String str : profile.getAchievements()) {
				if (!profile.getAchievements().contains(str)) {
					//Only save new entries.
					pipeline.lpush(Redis.PROIFLE_ACHIEVEMENTS.toString(uuid), str);
				}
			}
			
			for (String str : profile.getCompletedTutorials()) {
				if (!profile.getOldCompletedTutorials().contains(str)) {
					//Only save new entries.
					pipeline.lpush(Redis.PROFILE_COMPLETED_TUTORIALS.toString(uuid), str);
				}
			}			
			
			for (String str : profile.getCollectedRecipes()) {
				if (!profile.getOldCollectedRecipes().contains(str)) {
					//Only save new entries.
					pipeline.lpush(Redis.PROFILE_COLLECTED_RECIPES.toString(uuid), str);
				}
			}
	
			//REMOVE DELETED LIST ELEMENTS
			for (String str : profile.getOldCollectedRecipes()) {
				if (!profile.getCollectedRecipes().contains(str)) {
					System.out.println("SHOULD REMOVE: " + str);
					pipeline.lrem(Redis.PROFILE_COLLECTED_RECIPES.toString(uuid), 0, str);
				}
			}
			
			////////////////////////////////////////////
			/// END LIST COMPARE/CONTRAST FOR SAVING ///
			////////////////////////////////////////////

			//Player Settings
			pipeline.set(Redis.SETTINGS_P_CHATFILTER.toString(uuid), Boolean.toString(profile.isChatFilter()));
			pipeline.set(Redis.SETTINGS_P_FRIEND_REQUEST.toString(uuid), Boolean.toString(profile.isFriendRequests()));
			pipeline.set(Redis.SETTINGS_P_GUILD_REQUEST.toString(uuid), Boolean.toString(profile.isGuildRequests()));
			pipeline.set(Redis.SETTINGS_P_PARTY_REQUEST.toString(uuid), Boolean.toString(profile.isPartyRequests()));
			pipeline.set(Redis.SETTINGS_P_TRADE_REQUEST.toString(uuid), Boolean.toString(profile.isTradeRequests()));
			pipeline.set(Redis.SETTINGS_P_DEBUG_MESSAGES.toString(uuid), Boolean.toString(profile.isToggleDebug()));

			//Professions
			pipeline.set(Redis.PROFESSION_FARMING.toString(uuid), Long.toString(profile.getFarmingExperience()));
			pipeline.set(Redis.PROFESSION_FISHING.toString(uuid), Long.toString(profile.getFishingExperience()));
			pipeline.set(Redis.PROFESSION_LUMBERJACK.toString(uuid), Long.toString(profile.getLumberjackExperience()));
			pipeline.set(Redis.PROFESSION_MINING.toString(uuid), Long.toString(profile.getMiningExperience()));

			//Realm Info
			pipeline.set(Redis.REALM_TIER.toString(uuid), Integer.toString(profile.getRealmTier()));
			pipeline.set(Redis.REALM_TITLE.toString(uuid), profile.getRealmTitle());
			pipeline.set(Redis.REALM_PORTAL_INSIDE_LOCATION.toString(uuid), profile.getRealmInsideLocation());

			pipeline.sync();

			//END
			profiles.remove(uuid);
		}
	}

	public PlayerProfileData getProfile(Player player) {
		return profiles.get(player.getUniqueId().toString());
	}
}

