package com.forgestorm.spigotcore.profile.player;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ProfessionType;
import com.forgestorm.spigotcore.constants.Usergroup;
import com.forgestorm.spigotcore.experience.PlayerExperience;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.profile.ProfileData;
import com.forgestorm.spigotcore.util.display.FloatingMessage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerProfileData extends ProfileData {

	private final SpigotCore PLUGIN;
	
	//////////////////////
	/// SAVED TO REDIS ///
	//////////////////////
	
	//Player Info
	private String prefix;
	private String ipAddress;
	private int userGroup;
	private boolean isModerator;
	private boolean isAdmin;
	private boolean isBanned;
	private int warningPoints;
	private int bankCurrency;
	private int currency;
	private int lifetimeCurrency;
	private int premiumCurrency;
	private int lifetimePremiumCurrency;
	private long loginTime;
	private long lastLevelTime;
	private long playTime;
	private long levelTime;
	private long experience;
	private List<String> achievements = new ArrayList<>();
	private List<String> completedTutorials = new ArrayList<>();
	private List<String> collectedRecipes = new ArrayList<>();
	private List<String> oldAchievements = new ArrayList<>();
	private List<String> oldCompletedTutorials = new ArrayList<>();
	private List<String> oldCollectedRecipes = new ArrayList<>();
	
	//RPG Info
	private String serializedInventory;
 	
	//Player Settings
	private boolean chatFilter;
	private boolean friendRequests;
	private boolean guildRequests;
	private boolean partyRequests;
	private boolean tradeRequests;
	private boolean toggleDebug;

	// Profession stats
	private long farmingExperience;
	private long fishingExperience;
	private long lumberjackExperience;
	private long miningExperience;
	
	
	//////////////////////////
	/// NOT SAVED TO REDIS ///
	//////////////////////////
	private Player player;
	private int playerLevel;
	private double expPercent;
	private Location location;
	
	/*---- ARMOR ----*/
	private int armorGemFind;
	private int armorItemFind;
	
	/*---- COMBAT ----*/
	private int combatTime;
	private Menu currentMenu;

	//Plugin info
	private boolean loaded;
	private boolean inTutorial;
	private PlayerExperience expCalc;

	public PlayerProfileData(SpigotCore plugin, Player player) {
		PLUGIN = plugin;
		this.player = player;
		uuid = player.getUniqueId();
		name = player.getName();
		ipAddress = player.getAddress().getHostString();
		prefix = "";
		loginTime = lastLevelTime = System.currentTimeMillis() / 1000;
		expCalc = new PlayerExperience();
		
		setUsernameRankPrefix();
	}

	/**
	 * The information for the player is loaded async.  Because of this, we don't
	 * want to parse field data that may not exist yet.  When the profile is
	 * finished loading, the boolean is set to true, and the players is shown
	 * these effects on their client.
	 * 
	 * @param isLoaded If false, we are still waiting on data form the database.
	 */
	public void setLoaded(boolean isLoaded) {
		loaded = isLoaded;
	}
	
	//////////////////////
	/// Combat related ///
	//////////////////////
	public void putInCombat() {
		combatTime = 10;
	}
	
	public boolean isInCombat() {
		if (combatTime > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * This will add fatigue to the player.
	 * 
	 * @param amount
	 */
	public void addFatigue(double amount) {
		double fatigue = energy - amount;
		
		if (fatigue < 0) {
			fatigue = 0;
		}
		
		energy = fatigue;
	}
	
	/**
	 * This is a conditional test to see if the player is fatigued.
	 * 
	 * @return True if the player is fatigued.
	 */
	public boolean isFatigued() {
		if (energy <= 1) {
			return true;
		}
		return false;
	}

	/**
	 * Sets a players experience. This will override any previous values.
	 * 
	 * @param player The player who we will set experience for.
	 * @param argument The amount of experience the player will receive.
	 */
	public void setExperience(long argument) {
		if (argument < 1) argument = 1;

		experience = argument;
		updateUserInterfaceText();
	}

	/**
	 * Adds experience to the players total current experience.
	 * 
	 * @param player The player who will receive additional experience.
	 * @param argument The amount of experience to add.
	 */
	public void addExperience(long argument) {
		int maxAllowedExp = expCalc.getExperience(100);
		long previousExperience = getExperience();
		int previousLevel = expCalc.getLevel(previousExperience);
		long experience = previousExperience + argument;
		int newLevel = expCalc.getLevel(experience);

		//Prevent player from getting too much exp.
		if (experience > maxAllowedExp) {
			experience = maxAllowedExp;
		}

		//Player leveled up!
		if (newLevel > previousLevel) {
			//Show level up message.
			new FloatingMessage().sendFloatingMessage(player, ChatColor.GREEN + "Leveled UP!", ChatColor.GOLD + "You are now level " + newLevel);

			for (double i = 0; i < 2; i++) {
				Firework fw = (Firework) player.getWorld().spawn(player.getLocation().subtract(0, -1, 0), Firework.class);
				FireworkMeta fm = fw.getFireworkMeta();
				fm.addEffect(FireworkEffect.builder()
						.flicker(false)
						.trail(false)
						.with(Type.STAR)
						.withColor(Color.YELLOW)
						.withFade(Color.YELLOW)
						.build());
				fw.setFireworkMeta(fm);
			}

			//Heal the player
			player.setHealth(20);

			//Send the player a message
			player.sendMessage(ChatColor.GREEN + "You have leveled up!"); 
			player.sendMessage(ChatColor.GREEN + "You are now level " +  ChatColor.GOLD + newLevel + ChatColor.GREEN + ".");
			player.sendMessage(ChatColor.GREEN + "You have been healed!");
			
			//Reset current level time.
			setLevelTime(0);
			setLastLevelTime(System.currentTimeMillis() / 1000);
			
			//Set the level.
			setLevel(newLevel);
		}

		setExperience((int) experience);
	}

	/**
	 * This will remove experience from the players current total experience count.
	 * 
	 * @param player The player who will have experience removed from them.
	 * @param argument The amount of experience to remove.
	 */
	public void removeExperience(long argument) {
		long previousExperience = getExperience();
		long experience = previousExperience - argument;

		if (experience < 1) {
			experience = 1;
		}

		setExperience(experience);
	}

	/**
	 * Sets a players level. This will override any previous values.
	 * 
	 * @param player The player who we will set level for.
	 * @param argument The level the player will receive.
	 */
	public void setLevel(int argument) {
		int xp = 0;

		if (argument > 100) {
			xp = expCalc.getExperience(100);
		} else {
			xp = expCalc.getExperience(argument);
		}

		setExperience(xp);
	}

	/**
	 * Adds level(s) to the players current level.
	 * 
	 * @param player The player who will receive additional level(s).
	 * @param argument The amount of level(s) to add.
	 */
	public void addLevel(int argument) {
		int currentLevel = player.getLevel();
		int desiredLevel = currentLevel + argument;
		long currentXP = getExperience();
		long expToAddLevels = 0;

		if (desiredLevel > 100) {
			desiredLevel = 100;
		}

		for (int level = currentLevel + 1; level <= desiredLevel; level++) {
			expToAddLevels += expCalc.getExperience(level) - expCalc.getExperience(level - 1);
		}

		setExperience(currentXP + expToAddLevels);
	}

	/**
	 * This will remove level(s) from the players current level.
	 * 
	 * @param player The player who will have level(s) removed from them.
	 * @param argument The amount of level(s) to remove.
	 */
	public void removeLevel(int argument) {
		int currentLevel = player.getLevel();
		int desiredLevel = player.getLevel() - argument;
		long currentXP = getExperience();
		long expToAddLevels = 0;

		if (desiredLevel < 0) {
			desiredLevel = 1;
		}

		for (int level = currentLevel; level > desiredLevel; level--) {
			expToAddLevels -= expCalc.getExperience(level) - expCalc.getExperience(level - 1);
		}

		setExperience(currentXP + expToAddLevels);
	}

	/**
	 * This will update the variables that are used to display the players
	 * experience and level above their users hot bar.
	 */
	private void updateUserInterfaceText() {
		expPercent = expCalc.getPercentToLevel(experience);
		playerLevel = expCalc.getLevel(experience);
	}

	//Sets the players chat prefix.
	public void setUsernameRankPrefix() {
		String prefix = "";

		//Set Admin prefix
		if (isAdmin) {
			prefix = prefix.concat(Usergroup.USER_PREFIX_ADMINISTRATOR.getUsergroupPrefix());
		}

		//Set moderator prefix
		if (isModerator) {
			prefix = prefix.concat(Usergroup.USER_PREFIX_MODERATOR.getUsergroupPrefix());
		}

		//Paid rank prefix
		if (getUserGroup() == 1) {
			prefix = prefix.concat(Usergroup.USER_PREFIX_USERGROUP_1.getUsergroupPrefix());
		} else if (getUserGroup() == 2) {
			prefix = prefix.concat(Usergroup.USER_PREFIX_USERGROUP_2.getUsergroupPrefix());
		} else if (getUserGroup() == 3) {
			prefix = prefix.concat(Usergroup.USER_PREFIX_USERGROUP_3.getUsergroupPrefix());
		} else if (getUserGroup() == 4) {
			prefix = prefix.concat(Usergroup.USER_PREFIX_USERGROUP_4.getUsergroupPrefix());
		}

		//Append the prefix to the player.
		player.setCustomName(prefix + ChatColor.GRAY + player.getName());
		player.setCustomNameVisible(true);
	}

	/**
	 * This will set administrators to server OP.
	 */
	public void setOperatorRank() {
		if (isAdmin) {
			player.setOp(true);
		} else {
			//Set to false, just in case!
			player.setOp(false);
		}
	}

	/**
	 * Sets the player's premium currency. This will override the player's 
	 * current premium currency count.
	 * 
	 * @param player The player who will have their premium currency changed.
	 * @param argument The new amount of premium currency the player will have.
	 */
	public void setPremiumCurrency(int argument) {
		if (argument < 0) {
			argument = 0;
		}

		premiumCurrency = argument;
	}

	/**
	 * This will add premium currency to the player's current premium currency 
	 * count.
	 * 
	 * @param player The player who will receive premium currency.
	 * @param argument The amount of premium currency to add.
	 * @return 
	 */
	public void addPremiumCurrency(int argument) {
		int previousCurrency = getPremiumCurrency();
		int premiumCurrency = previousCurrency + argument;

		if (premiumCurrency < 0) {
			premiumCurrency = 0;
		}

		setPremiumCurrency(premiumCurrency);
	}

	/**
	 * This will remove premium currency from the player's current premium 
	 * currency count.
	 * 
	 * @param player The player who will have premium currency deducted.
	 * @param argument The amount of premium currency to remove.
	 * @return Returns true if the player has enough balance to subtract from.
	 */
	public boolean removePremiumCurrency(int argument) {
		int previousCurrency = getPremiumCurrency();
		int premiumCurrency = previousCurrency - argument;

		if (premiumCurrency < 0) {
			return false;
		}

		setPremiumCurrency(premiumCurrency);
		return true;
	}

	/**
	 * Sets the player's currency. This will override the player's current 
	 * currency count.
	 * 
	 * @param player The player who will have their currency changed.
	 * @param argument The new amount of currency the player will have.
	 */
	public void setCurrency(int argument) {
		if (argument < 0) {
			argument = 0;
		}

		currency = argument;
	}

	/**
	 * This will add currency to the player's current currency count.
	 * 
	 * @param player The player who will receive currency.
	 * @param argument The amount of currency to add.
	 */
	public void addCurrency(int argument) {
		int previousCurrency = getCurrency();
		int currency = previousCurrency + argument;

		if (currency < 0) {
			currency = 0;
		}

		setCurrency(currency);
	}

	/**
	 * This will remove currency from the player's current currency count.
	 * 
	 * @param player The player who will have currency deducted.
	 * @param argument The amount of currency to remove.
	 * @return Returns true if the player has enough balance to subtract from.
	 */
	public boolean removeCurrency(int argument) {
		int previousCurrency = getCurrency();
		int currency = previousCurrency - argument;

		if (currency < 0) {
			return false;
		}

		setCurrency(currency);
		return true;
	}
	
	/**
	 * This will add currency to the player's bank.
	 * 
	 * @param argument The amount of money to add.
	 */
	public void addBankCurrency(int argument) {
		if (argument < 0) { argument = 0; }
		setBankCurrency(argument);
	}
	
	/**
	 * This will remove currency from the players bank.
	 * 
	 * @param argument The amount to remove.
	 * @return True if the amount requested isn't greater than the amount 
	 * available.
	 */
	public boolean removeBankCurrency(int argument) {
		int previousBankCurrency = getBankCurrency();
		int updatedBankCurrency = previousBankCurrency - argument;

		if (updatedBankCurrency < 0) {
			return false;
		}

		setBankCurrency(updatedBankCurrency);
		return true;
	}

	/**
	 * This will promote the players usergroup!
	 */
	public void promote() {
		userGroup += 1;
		setUsernameRankPrefix();
		setOperatorRank();
		PLUGIN.getScoreboardManager().assignPlayer(player);
	}

	/**
	 * This will promote a user to a moderator.
	 */
	public void promoteMod() {
		setModerator(true);
		setUsernameRankPrefix();
		setOperatorRank();
		PLUGIN.getScoreboardManager().assignPlayer(player);
	}

	/**
	 * This will promote a user to administrator.
	 */
	public void promoteAdmin() {
		setAdmin(true);
		setUsernameRankPrefix();
		setOperatorRank();
		PLUGIN.getScoreboardManager().assignPlayer(player);
	}

	/**
	 * Demotes the player.
	 * 
	 * @return True if demotion was successful.
	 */
	public boolean demote() {
		if (userGroup - 1 >= 0) userGroup -= 1;
		setUsernameRankPrefix();
		setOperatorRank();
		PLUGIN.getScoreboardManager().assignPlayer(player);
		return userGroup - 1 >= 0 ? true : false;
	}


	/**
	 * This will demote a moderator.
	 */
	public void demoteMod() {
		setModerator(false);
		setUsernameRankPrefix();
		setOperatorRank();
		PLUGIN.getScoreboardManager().assignPlayer(player);
	}

	/**
	 * This will demote an administrator.
	 */
	public void demoteAdmin() {
		if (!player.getName().equals("unenergizer")) isAdmin = false;
		setUsernameRankPrefix();
		setOperatorRank();
		PLUGIN.getScoreboardManager().assignPlayer(player);
	}
	
	/**
	 * This will add an achievement to the players achievement list!
	 * 
	 * @param achievementNames
	 */
	public void addAchievement(String... achievementNames) {
		for (String id : achievementNames) {
			if (getAchievements().contains(id)) { return; }
			getAchievements().add(id);
		}
	}
	
	/**
	 * This will test to see if the player has the supplied profession.
	 * Note that -1 means they do not have the profession. When a player buys a
	 * profession we will set the experience value from -1 to 0.
	 * 
	 * @param type The profession to test for.
	 * @return True if the player has that profession.
	 */
	public boolean hasProfession(ProfessionType type) {
		switch(type) {
		case FARMING:
			return getFarmingExperience() >= 0;
		case FISHING:
			return getFishingExperience() >= 0;
		case LUMBERJACK:
			return getLumberjackExperience() >= 0;
		case MINING:
			return getMiningExperience() >= 0;
		}
		
		return false;
	}
}