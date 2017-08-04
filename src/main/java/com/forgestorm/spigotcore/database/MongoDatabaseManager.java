package com.forgestorm.spigotcore.database;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.Database;
import com.forgestorm.spigotcore.experience.PlayerExperience;
import com.forgestorm.spigotcore.experience.ProfessionExperience;
import com.forgestorm.spigotcore.util.item.InventoryStringDeSerializer;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.Getter;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedDeque;

@Getter
public class MongoDatabaseManager extends BukkitRunnable {

    private final SpigotCore plugin;
    private final String host;
    private final int port;
    private final String username;
    private final String password;
    private final String databaseName;
    private final HashMap<UUID, PlayerProfileData> profiles = new HashMap<>();
    private
    @Getter
    final Queue<Player> loadingPlayers = new ConcurrentLinkedDeque<>();
    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> playerCollection;

    public MongoDatabaseManager(SpigotCore plugin, String host, int port, String username, String password, String databaseName) {
        this.plugin = plugin;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;

        connect();
    }

    private void connect() {
        ServerAddress address = new ServerAddress(host, port);
        MongoCredential credential = MongoCredential.createCredential(
                username,
                databaseName,
                password.toCharArray()
        );

        // Initialize the database.
        mongoClient = new MongoClient(address, Collections.singletonList(credential));
        database = mongoClient.getDatabase(databaseName);
        playerCollection = database.getCollection("fs_players");
    }

    /**
     * Called when the server is going to shutdown.
     */
    public void onDisable() {
        // Save data for all players currently online.
        for (Player players : Bukkit.getOnlinePlayers()) {
            unloadProfile(players);
        }

        // Close the database connection
        mongoClient.close();
        System.out.println("[FSCore] Closing MongoDB connection.");
    }

    private void createProfile(Player player) {
        //Prevent NPC's from getting profiles.
        if (player.hasMetadata("NPC")) return;

        String uuid = player.getUniqueId().toString();
        PlayerProfileData profile = profiles.get(player.getUniqueId());

        // DEFAULT VALUES
        int userGroup = 0;
        boolean isModerator = false;
        boolean isAdmin = false;
        boolean isBanned = false;
        int warningPoints = 0;
        int bankCurrency = 0;
        int currency = 5000;
        int lifetimeCurrency = 0;
        int premiumCurrency = 0;
        int lifetimePremiumCurrency = 0;
        long joinDate = System.currentTimeMillis() / 1000;
        long playTime = 0;
        long levelTime = 0;
        long experience = new PlayerExperience().getExperience(1);
        List<String> achievements = new ArrayList<>();
        List<String> completedTutorials = new ArrayList<>();
        List<String> collectedRecipes = new ArrayList<>();
        String serializedInventory = "";
        boolean tutorialFinished = false;

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
        ProfessionExperience professionExperience = new ProfessionExperience();
        boolean farmingActive = false;
        boolean fishingActive = false;
        boolean lumberjackActive = false;
        boolean miningActive = false;
        boolean cookingActive = false;
        boolean smeltingActive = false;
        long farmingExp = professionExperience.getExperience(1);
        long fishingExp = professionExperience.getExperience(1);
        long lumberjackExp = professionExperience.getExperience(1);
        long miningExp = professionExperience.getExperience(1);
        long cookingExp = professionExperience.getExperience(1);
        long smeltingExp = professionExperience.getExperience(1);

        //RealmCommands Default Settings
        String realmTitle = "Hi! Welcome to my realm!!";
        int realmTier = 1;
        String realmInsideLocation = "11/19/7";
        boolean hasRealm = false;

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
        profile.setJoinDate(joinDate);
        profile.setPlayTime(playTime);
        profile.setLevelTime(levelTime);
        profile.setExperience(experience);
        profile.setAchievements(achievements);
        profile.setCompletedTutorials(completedTutorials);
        profile.setCollectedRecipes(collectedRecipes);
        profile.setSerializedInventory(serializedInventory);
        profile.setTutorialFinished(tutorialFinished);

        profile.setChatFilter(chatFilter);
        profile.setFriendRequests(friendRequests);
        profile.setGuildRequests(guildRequests);
        profile.setPartyRequests(partyRequests);
        profile.setTradeRequests(tradeRequests);
        profile.setTradeRequests(toggleDebug);

        profile.setFarmingActive(farmingActive);
        profile.setFishingActive(fishingActive);
        profile.setLumberjackActive(lumberjackActive);
        profile.setMiningActive(miningActive);
        profile.setCookingActive(cookingActive);
        profile.setSmeltingActive(smeltingActive);
        profile.setFarmingExperience(farmingExp);
        profile.setFishingExperience(fishingExp);
        profile.setLumberjackExperience(lumberjackExp);
        profile.setMiningExperience(miningExp);
        profile.setCookingExperience(cookingExp);
        profile.setSmeltingExperience(smeltingExp);

        profile.setRealmTier(realmTier);
        profile.setRealmTitle(realmTitle);
        profile.setRealmInsideLocation(realmInsideLocation);
        profile.setHasRealm(hasRealm);

        ////////////////////////////////////
        //// MONGO FIRST TIME SAVE  ////////
        ////////////////////////////////////

        Document document = new Document("sc_uuid", uuid)
                //Player Info
                .append(Database.PROFILE_USER_GROUP.toString(), userGroup)
                .append(Database.PROFILE_IS_MODERATOR.toString(), isModerator)
                .append(Database.PROFILE_IS_ADMIN.toString(), isAdmin)
                .append(Database.PROFILE_IS_BANNED.toString(), isBanned)
                .append(Database.PROFILE_WARNING_POINTS.toString(), warningPoints)
                .append(Database.PROFILE_CURRENCY_BANK_GEMS.toString(), bankCurrency)
                .append(Database.PROFILE_CURRENCY_GEMS.toString(), currency)
                .append(Database.PROFILE_CURRENCY_GEMS_LIFE_TIME_.toString(), lifetimeCurrency)
                .append(Database.PROFILE_CURRENCY_PREMIUM.toString(), premiumCurrency)
                .append(Database.PROFILE_CURRENCY_PREMIUM_LIFE_TIME.toString(), lifetimePremiumCurrency)
                .append(Database.PROFILE_JOIN_DATE.toString(), joinDate)
                .append(Database.PROFILE_ONLINE_TIME.toString(), playTime)
                .append(Database.PROFILE_ONLINE_LEVEL_TIME.toString(), levelTime)
                .append(Database.PROFILE_EXPERIENCE.toString(), experience)
                .append(Database.PROFILE_ACHIEVEMENTS.toString(), achievements)
                .append(Database.PROFILE_COMPLETED_TUTORIALS.toString(), completedTutorials)
                .append(Database.PROFILE_COLLECTED_RECIPES.toString(), collectedRecipes)
                .append(Database.PROFILE_ACHIEVEMENTS.toString(), achievements)
                .append(Database.PROFILE_PLAYER_INVENTORY.toString(), serializedInventory)
                .append(Database.PROFILE_TUTORIAL_FINISHED.toString(), tutorialFinished)

                //Player Settings
                .append(Database.SETTINGS_P_CHAT_FILTER.toString(), chatFilter)
                .append(Database.SETTINGS_P_FRIEND_REQUEST.toString(), friendRequests)
                .append(Database.SETTINGS_P_GUILD_REQUEST.toString(), guildRequests)
                .append(Database.SETTINGS_P_PARTY_REQUEST.toString(), partyRequests)
                .append(Database.SETTINGS_P_TRADE_REQUEST.toString(), tradeRequests)
                .append(Database.SETTINGS_P_DEBUG_MESSAGES.toString(), toggleDebug)

                //Profession info
                .append(Database.PROFESSION_FARMING_ACTIVE.toString(), farmingActive)
                .append(Database.PROFESSION_FISHING_ACTIVE.toString(), fishingActive)
                .append(Database.PROFESSION_LUMBERJACK_ACTIVE.toString(), lumberjackActive)
                .append(Database.PROFESSION_MINING_ACTIVE.toString(), miningActive)
                .append(Database.PROFESSION_COOKING_ACTIVE.toString(), cookingActive)
                .append(Database.PROFESSION_SMELTING_ACTIVE.toString(), smeltingActive)
                .append(Database.PROFESSION_FARMING.toString(), farmingExp)
                .append(Database.PROFESSION_FISHING.toString(), fishingExp)
                .append(Database.PROFESSION_LUMBERJACK.toString(), lumberjackExp)
                .append(Database.PROFESSION_MINING.toString(), miningExp)
                .append(Database.PROFESSION_COOKING.toString(), cookingExp)
                .append(Database.PROFESSION_SMELTING.toString(), smeltingExp)

                //RealmCommands data
                .append(Database.REALM_TIER.toString(), realmTier)
                .append(Database.REALM_TITLE.toString(), realmTitle)
                .append(Database.REALM_PORTAL_INSIDE_LOCATION.toString(), realmInsideLocation)
                .append(Database.REALM_HAS_REALM.toString(), hasRealm);

        // Insert the initial document into Mongo.
        playerCollection.insertOne(document);
    }

    /**
     * Remove a player from the list of players that are currently requesting to have
     * their profile loaded in and load the profile for that player
     */
    @Override
    public void run() {
        // If the queue is not empty, load the player. Return otherwise.
        if (loadingPlayers.isEmpty()) return;

        Player player = loadingPlayers.remove();
        loadProfile(player);
    }

    /**
     * Called when a player log's into the server.
     *
     * @param player The player who joined the server.
     */
    public void loadProfile(Player player) {
        //Prevent NPC's from getting profiles.
        if (player.hasMetadata("NPC")) return;

        PlayerProfileData profile = new PlayerProfileData(plugin, player);
        String uuid = player.getUniqueId().toString();

        //Save the profile for access later.
        profiles.put(player.getUniqueId(), profile);

        Document document = playerCollection.find(Filters.eq("sc_uuid", uuid)).first();

        // Player was not found in the database.
        // Create a new database entry.
        if (document == null) {
            createProfile(player);
        } else {

            //Player Info
            profile.setUserGroup(document.getInteger(Database.PROFILE_USER_GROUP.toString()));
            profile.setModerator(document.getBoolean(Database.PROFILE_IS_MODERATOR.toString()));
            profile.setAdmin(document.getBoolean(Database.PROFILE_IS_ADMIN.toString()));
            profile.setBanned(document.getBoolean(Database.PROFILE_IS_BANNED.toString()));
            profile.setWarningPoints(document.getInteger(Database.PROFILE_WARNING_POINTS.toString()));
            profile.setBankCurrency(document.getInteger(Database.PROFILE_CURRENCY_BANK_GEMS.toString()));
            profile.setCurrency(document.getInteger(Database.PROFILE_CURRENCY_GEMS.toString()));
            profile.setLifetimeCurrency(document.getInteger(Database.PROFILE_CURRENCY_GEMS_LIFE_TIME_.toString()));
            profile.setPremiumCurrency(document.getInteger(Database.PROFILE_CURRENCY_PREMIUM.toString()));
            profile.setLifetimePremiumCurrency(document.getInteger(Database.PROFILE_CURRENCY_PREMIUM_LIFE_TIME.toString()));
            profile.setJoinDate(document.getLong(Database.PROFILE_JOIN_DATE.toString()));
            profile.setPlayTime(document.getLong(Database.PROFILE_ONLINE_TIME.toString()));
            profile.setLevelTime(document.getLong(Database.PROFILE_ONLINE_LEVEL_TIME.toString()));
            profile.setExperience(document.getLong(Database.PROFILE_EXPERIENCE.toString()));
            profile.setSerializedInventory(document.getString(Database.PROFILE_PLAYER_INVENTORY.toString()));
            profile.setTutorialFinished(document.getBoolean(Database.PROFILE_TUTORIAL_FINISHED.toString()));
            profile.setAchievements((List<String>) document.get(Database.PROFILE_ACHIEVEMENTS.toString()));
            profile.setCompletedTutorials((List<String>) document.get(Database.PROFILE_COMPLETED_TUTORIALS.toString()));
            profile.setCollectedRecipes((List<String>) document.get(Database.PROFILE_COLLECTED_RECIPES.toString()));


            //Player Settings
            profile.setChatFilter(document.getBoolean(Database.SETTINGS_P_CHAT_FILTER.toString()));
            profile.setFriendRequests(document.getBoolean(Database.SETTINGS_P_FRIEND_REQUEST.toString()));
            profile.setGuildRequests(document.getBoolean(Database.SETTINGS_P_GUILD_REQUEST.toString()));
            profile.setPartyRequests(document.getBoolean(Database.SETTINGS_P_PARTY_REQUEST.toString()));
            profile.setTradeRequests(document.getBoolean(Database.SETTINGS_P_TRADE_REQUEST.toString()));
            profile.setToggleDebug(document.getBoolean(Database.SETTINGS_P_DEBUG_MESSAGES.toString()));

            //Player profession
            profile.setFarmingActive(document.getBoolean(Database.PROFESSION_FARMING_ACTIVE.toString()));
            profile.setFishingActive(document.getBoolean(Database.PROFESSION_FISHING_ACTIVE.toString()));
            profile.setLumberjackActive(document.getBoolean(Database.PROFESSION_LUMBERJACK_ACTIVE.toString()));
            profile.setMiningActive(document.getBoolean(Database.PROFESSION_MINING_ACTIVE.toString()));
            profile.setCookingActive(document.getBoolean(Database.PROFESSION_COOKING_ACTIVE.toString()));
            profile.setSmeltingActive(document.getBoolean(Database.PROFESSION_SMELTING_ACTIVE.toString()));

            profile.setFarmingExperience(document.getLong(Database.PROFESSION_FARMING.toString()));
            profile.setFishingExperience(document.getLong(Database.PROFESSION_FISHING.toString()));
            profile.setLumberjackExperience(document.getLong(Database.PROFESSION_LUMBERJACK.toString()));
            profile.setMiningExperience(document.getLong(Database.PROFESSION_MINING.toString()));
            profile.setCookingExperience(document.getLong(Database.PROFESSION_COOKING.toString()));
            profile.setSmeltingExperience(document.getLong(Database.PROFESSION_SMELTING.toString()));

            //RealmCommands Data
            profile.setRealmTier(document.getInteger(Database.REALM_TIER.toString()));
            profile.setRealmTitle(document.getString(Database.REALM_TITLE.toString()));
            profile.setRealmInsideLocation(document.getString(Database.REALM_PORTAL_INSIDE_LOCATION.toString()));
            profile.setHasRealm(document.getBoolean(Database.REALM_HAS_REALM.toString()));

        }

        //Profile has been loaded.
        plugin.getSetupNetworkPlayer().getLoadingPlayers().add(profile);
    }

    /**
     * Called when a player logs out.
     *
     * @param player The player who logged out.
     */
    public void unloadProfile(Player player) {
        //Prevent NPC's from unloading profiles.
        if (player.hasMetadata("NPC")) {
            return;
        }

        String uuid = player.getUniqueId().toString();
        PlayerProfileData profile = profiles.get(player.getUniqueId());
        SimpleDateFormat formatter = new SimpleDateFormat("M-dd-yyyy hh:mm:ss");
        String date = formatter.format(new Date());
        long currentTime = System.currentTimeMillis() / 1000;
        long totalTime = (currentTime - profile.getLoginTime()) + profile.getPlayTime();
        long levelTime = (currentTime - profile.getLastLevelTime()) + profile.getLevelTime();

        Document document = new Document("sc_uuid", uuid)

                .append(Database.PROFILE_ID.toString(), profile.getUserGroup())
                .append(Database.PROFILE_USER_GROUP.toString(), profile.getUserGroup())
                .append(Database.PROFILE_IS_MODERATOR.toString(), profile.isModerator())
                .append(Database.PROFILE_IS_ADMIN.toString(), profile.isAdmin())
                .append(Database.PROFILE_IS_BANNED.toString(), profile.isBanned())
                .append(Database.PROFILE_WARNING_POINTS.toString(), profile.getWarningPoints())
                .append(Database.PROFILE_CURRENCY_BANK_GEMS.toString(), profile.getBankCurrency())
                .append(Database.PROFILE_CURRENCY_GEMS.toString(), profile.getCurrency())
                .append(Database.PROFILE_CURRENCY_GEMS_LIFE_TIME_.toString(), profile.getLifetimeCurrency())
                .append(Database.PROFILE_CURRENCY_PREMIUM.toString(), profile.getPremiumCurrency())
                .append(Database.PROFILE_CURRENCY_PREMIUM_LIFE_TIME.toString(), profile.getLifetimePremiumCurrency())
                .append(Database.PROFILE_LAST_ACTIVITY.toString(), date)
                .append(Database.PROFILE_JOIN_DATE.toString(), profile.getJoinDate())
                .append(Database.PROFILE_ONLINE_TIME.toString(), totalTime)
                .append(Database.PROFILE_ONLINE_LEVEL_TIME.toString(), levelTime)
                .append(Database.PROFILE_EXPERIENCE.toString(), profile.getExperience())
                //.append(Database.PROFILE_PLAYER_INVENTORY.toString(), profile.getSerializedInventory())
                .append(Database.PROFILE_PLAYER_INVENTORY.toString(), InventoryStringDeSerializer.toBase64(player.getInventory().getContents()))
                .append(Database.PROFILE_TUTORIAL_FINISHED.toString(), profile.isTutorialFinished())
                .append(Database.PROFILE_ACHIEVEMENTS.toString(), profile.getAchievements())
                .append(Database.PROFILE_COMPLETED_TUTORIALS.toString(), profile.getCompletedTutorials())
                .append(Database.PROFILE_COLLECTED_RECIPES.toString(), profile.getCollectedRecipes())


                //Player Settings
                .append(Database.SETTINGS_P_CHAT_FILTER.toString(), profile.isChatFilter())
                .append(Database.SETTINGS_P_FRIEND_REQUEST.toString(), profile.isFriendRequests())
                .append(Database.SETTINGS_P_GUILD_REQUEST.toString(), profile.isGuildRequests())
                .append(Database.SETTINGS_P_PARTY_REQUEST.toString(), profile.isPartyRequests())
                .append(Database.SETTINGS_P_TRADE_REQUEST.toString(), profile.isTradeRequests())
                .append(Database.SETTINGS_P_DEBUG_MESSAGES.toString(), profile.isToggleDebug())

                //Professions
                .append(Database.PROFESSION_FARMING_ACTIVE.toString(), profile.isFarmingActive())
                .append(Database.PROFESSION_FISHING_ACTIVE.toString(), profile.isFishingActive())
                .append(Database.PROFESSION_LUMBERJACK_ACTIVE.toString(), profile.isLumberjackActive())
                .append(Database.PROFESSION_MINING_ACTIVE.toString(), profile.isMiningActive())
                .append(Database.PROFESSION_COOKING_ACTIVE.toString(), profile.isCookingActive())
                .append(Database.PROFESSION_SMELTING_ACTIVE.toString(), profile.isSmeltingActive())

                .append(Database.PROFESSION_FARMING.toString(), profile.getFarmingExperience())
                .append(Database.PROFESSION_FISHING.toString(), profile.getFishingExperience())
                .append(Database.PROFESSION_LUMBERJACK.toString(), profile.getLumberjackExperience())
                .append(Database.PROFESSION_MINING.toString(), profile.getMiningExperience())
                .append(Database.PROFESSION_COOKING.toString(), profile.getCookingExperience())
                .append(Database.PROFESSION_SMELTING.toString(), profile.getSmeltingExperience())

                //RealmCommands Info
                .append(Database.REALM_TIER.toString(), profile.getRealmTier())
                .append(Database.REALM_TITLE.toString(), profile.getRealmTitle())
                .append(Database.REALM_PORTAL_INSIDE_LOCATION.toString(), profile.getRealmInsideLocation())
                .append(Database.REALM_HAS_REALM.toString(), profile.isHasRealm());

        // Insert the initial document into Mongo.
        playerCollection.replaceOne(Filters.eq("sc_uuid", uuid), document);

        //END
        profiles.remove(player.getUniqueId());
    }

    public PlayerProfileData getProfile(Player player) {
        return profiles.get(player.getUniqueId());
    }
}