package com.forgestorm.spigotcore.database;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.UserGroup;
import com.forgestorm.spigotcore.events.UpdateScoreboardEvent;
import com.forgestorm.spigotcore.experience.PlayerExperience;
import com.forgestorm.spigotcore.menus.Menu;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
public class PlayerProfileData {

    private final SpigotCore plugin;

    //////////////////////
    /// SAVED TO REDIS ///
    //////////////////////
	
	//Player Info
    private UUID uuid;
    private String name;
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
    private long joinDate;
    private long loginTime;
    private long lastLevelTime;
    private long playTime;
	private long levelTime;
	private long experience;
	private List<String> achievements = new ArrayList<>();
	private List<String> completedTutorials = new ArrayList<>();
	private List<String> collectedRecipes = new ArrayList<>();
	private String serializedInventory;
    private boolean tutorialFinished;

    //Player Settings
    private boolean chatFilter;
    private boolean friendRequests;
	private boolean guildRequests;
	private boolean partyRequests;
	private boolean tradeRequests;
	private boolean toggleDebug;

	//Profession stats
    private boolean farmingActive;
    private boolean fishingActive;
    private boolean lumberjackActive;
    private boolean miningActive;
    private boolean cookingActive;
    private boolean smeltingActive;
    private long farmingExperience;
    private long fishingExperience;
    private long lumberjackExperience;
	private long miningExperience;
    private long cookingExperience;
    private long smeltingExperience;

    //RealmCommands data
    private int realmTier;
	private String realmTitle;
	private String realmInsideLocation;
    private boolean hasRealm;

    /////////////////////////////
    /// NOT SAVED TO DATABASE ///
    /////////////////////////////
    private Player player;
    private int playerLevel;
    private double expPercent;
	private Location location;
	private Menu currentMenu;
    private String chatPrefix;
    private boolean savingData = false;

    //PluginCommand info
    private boolean inTutorial;
    private PlayerExperience expCalc;

	public PlayerProfileData(SpigotCore plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.uuid = player.getUniqueId();
        this.name = player.getName();
        ipAddress = player.getAddress().getHostString();
        prefix = "";
        loginTime = lastLevelTime = System.currentTimeMillis() / 1000;
		expCalc = new PlayerExperience();

        setChatPrefix();
    }

	/**
	 * Sets a players experience. This will override any previous values.
	 *
	 * @param argument The amount of experience the player will receive.
	 */
	public void setExperience(long argument) {
		if (argument < 1) argument = 1;

		experience = argument;
		updateUserInterfaceText();

        // Call update scoreboard event
        UpdateScoreboardEvent updateScoreboardEvent = new UpdateScoreboardEvent(player);
        Bukkit.getPluginManager().callEvent(updateScoreboardEvent);

        // Update player xp bar.
        player.setExp(expCalc.getBarPercent(experience));
        player.setLevel(playerLevel);
    }

	/**
	 * Adds experience to the players total current experience.
	 *
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
            plugin.getTitleManagerAPI().sendTitles(player, ChatColor.GREEN + "Leveled UP!", ChatColor.GOLD + "You are now level " + newLevel);

			for (double i = 0; i < 2; i++) {
				Firework fw = player.getWorld().spawn(player.getLocation().subtract(0, -1, 0), Firework.class);
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
	 * @param argument The level the player will receive.
	 */
	public void setLevel(int argument) {
		int xp;

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
    public void setChatPrefix() {
        String prefix = "";

		//Set Admin prefix
		if (isAdmin) {
			prefix = prefix.concat(UserGroup.USER_PREFIX_ADMINISTRATOR.getUserGroupPrefix());
		}

		//Set moderator prefix
		if (isModerator) {
			prefix = prefix.concat(UserGroup.USER_PREFIX_MODERATOR.getUserGroupPrefix());
		}

		//Paid rank prefix
        if (getUserGroup() == 0 && shouldShowNewPlayerTag()) {
            prefix = prefix.concat(UserGroup.USER_PREFIX_USER_GROUP_NEW.getUserGroupPrefix());
        } else if (getUserGroup() == 1) {
            prefix = prefix.concat(UserGroup.USER_PREFIX_USER_GROUP_1.getUserGroupPrefix());
        } else if (getUserGroup() == 2) {
            prefix = prefix.concat(UserGroup.USER_PREFIX_USER_GROUP_2.getUserGroupPrefix());
        } else if (getUserGroup() == 3) {
            prefix = prefix.concat(UserGroup.USER_PREFIX_USER_GROUP_3.getUserGroupPrefix());
		} else if (getUserGroup() == 4) {
			prefix = prefix.concat(UserGroup.USER_PREFIX_USER_GROUP_4.getUserGroupPrefix());
		}

		//Append the prefix to the player.
        chatPrefix = prefix + ChatColor.GRAY + player.getName();
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
	 * @param argument The new amount of premium currency the player will have.
	 */
	public void setPremiumCurrency(int argument) {
		if (argument < 0) {
			argument = 0;
		}

		premiumCurrency = argument;

        // Call update scoreboard event
        UpdateScoreboardEvent updateScoreboardEvent = new UpdateScoreboardEvent(player);
        Bukkit.getPluginManager().callEvent(updateScoreboardEvent);
    }

	/**
	 * This will add premium currency to the player's current premium currency 
	 * count.
	 *
	 * @param argument The amount of premium currency to add.
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
	 * @param argument The new amount of currency the player will have.
	 */
	public void setCurrency(int argument) {
		if (argument < 0) {
			argument = 0;
		}

		currency = argument;

        // Call update scoreboard event
        UpdateScoreboardEvent updateScoreboardEvent = new UpdateScoreboardEvent(player);
        Bukkit.getPluginManager().callEvent(updateScoreboardEvent);
    }

	/**
	 * This will add currency to the player's current currency count.
	 *
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

        // Call update scoreboard event
        UpdateScoreboardEvent updateScoreboardEvent = new UpdateScoreboardEvent(player);
        Bukkit.getPluginManager().callEvent(updateScoreboardEvent);
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
        setChatPrefix();
        setOperatorRank();
        plugin.getScoreboardManager().assignPlayer(player);
    }

	/**
	 * This will promote a user to a moderator.
	 */
	public void promoteMod() {
		setModerator(true);
        setChatPrefix();
        setOperatorRank();
        plugin.getScoreboardManager().assignPlayer(player);
    }

	/**
	 * This will promote a user to administrator.
	 */
	public void promoteAdmin() {
		setAdmin(true);
        setChatPrefix();
        setOperatorRank();
        plugin.getScoreboardManager().assignPlayer(player);
    }

	/**
	 * Demotes the player.
	 * 
	 * @return True if demotion was successful.
	 */
	public boolean demote() {
		if (userGroup - 1 >= 0) userGroup -= 1;
        setChatPrefix();
        setOperatorRank();
        plugin.getScoreboardManager().assignPlayer(player);
        return userGroup - 1 >= 0;
    }


	/**
	 * This will demote a moderator.
	 */
	public void demoteMod() {
		setModerator(false);
        setChatPrefix();
        setOperatorRank();
        plugin.getScoreboardManager().assignPlayer(player);
    }

	/**
	 * This will demote an administrator.
	 */
	public void demoteAdmin() {
		if (!player.getName().equals("unenergizer")) isAdmin = false;
        setChatPrefix();
        setOperatorRank();
        plugin.getScoreboardManager().assignPlayer(player);
    }

    /**
     * This will add an achievement to the players achievement list!
	 * 
	 * @param achievementNames The achievement to add to the list.
	 */
	public void addAchievement(String... achievementNames) {
		for (String id : achievementNames) {
			if (getAchievements().contains(id)) { return; }
			getAchievements().add(id);
		}
	}

	/**
     * Determines if the player is a new player. Currently, a new player is defined as
     * being on the server for less than 72 hours (3 Days). This method is used to put
     * a new player tag next to their name (above the head) and next to their chat name.
     * @return
     */
    public boolean shouldShowNewPlayerTag() {
        return joinDate + (TimeUnit.HOURS.toMillis(72) / 1000) > System.currentTimeMillis() / 1000;
    }
}