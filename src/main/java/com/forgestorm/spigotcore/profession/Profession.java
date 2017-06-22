package com.forgestorm.spigotcore.profession;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.CommonSounds;
import com.forgestorm.spigotcore.constants.FilePaths;
import com.forgestorm.spigotcore.constants.ProfessionType;
import com.forgestorm.spigotcore.constants.SpigotCoreMessages;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import com.forgestorm.spigotcore.experience.Experience;
import com.forgestorm.spigotcore.experience.ProfessionExperience;
import com.forgestorm.spigotcore.util.math.RandomChance;
import com.forgestorm.spigotcore.util.text.ProgressBarString;
import com.forgestorm.spigotcore.world.BlockRegenerationManager;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profession implements Listener {

    private final SpigotCore plugin;
    private final Map<String, ProfessionType> blockBreakTools = new HashMap<>();
    private final FileConfiguration farmingConfig, fishingConfig, furnaceConfig, miningConfig, woodCuttingConfig;
    private final Experience experienceCalculator = new ProfessionExperience();
    private final int expOffSet = experienceCalculator.getExpOffSet();
    private final BlockRegenerationManager blockRegen;
    private final List<Material> furnaceMaterials = new ArrayList<>();

    public Profession(SpigotCore plugin) {
        this.plugin = plugin;
        blockRegen = plugin.getBlockRegen();

        farmingConfig = YamlConfiguration.loadConfiguration(new File(FilePaths.PROFESSION_FARMING.toString()));
        fishingConfig = YamlConfiguration.loadConfiguration(new File(FilePaths.PROFESSION_FISHING.toString()));
        furnaceConfig = YamlConfiguration.loadConfiguration(new File(FilePaths.PROFESSION_COOKING_AND_SMELTING.toString()));
        miningConfig = YamlConfiguration.loadConfiguration(new File(FilePaths.PROFESSION_MINING.toString()));
        woodCuttingConfig = YamlConfiguration.loadConfiguration(new File(FilePaths.PROFESSION_WOOD_CUTTING.toString()));

        loadConfigs();

        // Register Events
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * This is mainly used by profession menu's to get the players stat rank.
     *
     * @param level The profession level.
     * @return A rank message.
     */
    public static String getProfessionRank(int level) {
        if (level < 20) {
            return "&7Rank: &fNovice";
        } else if (level >= 20 && level < 40) {
            return "&7Rank: &aIntermediate";
        } else if (level >= 40 && level < 60) {
            return "&7Rank: &9Proficient";
        } else if (level >= 60 && level < 80) {
            return "&7Rank: &5Expert";
        } else if (level >= 80) {
            return "&7Rank: &6Grand Master";
        }
        return null;
    }

    /**
     * Make sure the player has the right level for the tool being used.
     *
     * @param level The players profession level.
     * @param tool  The tool being used.
     * @return True if the level requirement is met.
     */
    public static boolean checkBlockBreakToolLevels(int level, Material tool) {
        switch (tool) {
            case WOOD_PICKAXE:
            case WOOD_AXE:
            case WOOD_HOE:
                return true;
            case STONE_PICKAXE:
            case STONE_AXE:
            case STONE_HOE:
                if (level >= 20) return true;
                break;
            case IRON_PICKAXE:
            case IRON_AXE:
            case IRON_HOE:
                if (level >= 40) return true;
                break;
            case DIAMOND_PICKAXE:
            case DIAMOND_AXE:
            case DIAMOND_HOE:
                if (level >= 60) return true;
                break;
            case GOLD_PICKAXE:
            case GOLD_AXE:
            case GOLD_HOE:
                if (level >= 80) return true;
                break;
        }
        return false; //The player does not meet the requirements to use the tool.
    }

    /**
     * Called when professions need to be disabled.
     */
    public void onDisable() {

        // Unregister Events
        BlockBreakEvent.getHandlerList().unregister(this);
        PlayerFishEvent.getHandlerList().unregister(this);
        FurnaceExtractEvent.getHandlerList().unregister(this);
    }

    /**
     * Some professions will have a chance to drop gems.
     *
     * @param player The player who we might drop gems next to.
     */
    private void dropGems(Player player, Location location) {
        int chance = RandomChance.randomInt(1, 100) - 1;
        if (chance >= 96) {
            int amount = RandomChance.randomInt(1, 3) - 1;
            ItemStack gems = new ItemStack(Material.EMERALD);
            gems.setAmount(amount);
            player.getWorld().dropItemNaturally(location, gems);
        }
    }

    /**
     * This is used to check if a profession skill was successful.
     *
     * @param player The player who toggled the event.
     * @param tool   The tool the player was using when the event was toggled.
     * @param block  The block the player broke during the event.
     */
    public void toggleBlockBreakProfession(Player player, Material tool, Block block) {
        PlayerProfileData playerProfileData = plugin.getProfileManager().getProfile(player);
        String toolName = tool.toString();
        String blockName = block.getType().toString();
        Material blockType = block.getType();
        byte blockData = block.getData();
        byte tempData = 0;
        ProfessionType professionType;
        FileConfiguration config = null;
        boolean hasProfession = false;
        long experienceGained = 0;
        long currentExperience = 0;
        int level = 0;

        // If the tool exists in the map, continue. Else, stop.
        if (!blockBreakTools.containsKey(toolName) || blockName == null) {
            //sendFailNotification(player, "DEBUG: Tool not found or BlockName null!!");
            return;
        }

        professionType = blockBreakTools.get(toolName);

        // Switch on the profession to choose the correct config file.
        switch (professionType) {
            case FARMING:
                config = farmingConfig;
                hasProfession = playerProfileData.isFarmingActive();
                break;
            case MINING:
                config = miningConfig;
                hasProfession = playerProfileData.isMiningActive();
                break;
            case WOOD_CUTTING:
                config = woodCuttingConfig;
                hasProfession = playerProfileData.isLumberjackActive();
                break;
        }

        // Make sure the user has learned the profession first.
        if (!hasProfession) {
            sendFailNotification(player, SpigotCoreMessages.PROFESSION_NOT_LEARNED.toString());
            return;
        }

        int chance = 0;
        boolean canBreakBlock = false;
        boolean useDropData = false;
        Material materialToDrop = null;

        // Get if the player can break the block and the break chance.
        switch (professionType) {
            case FARMING:
                chance = config.getInt(toolName + ".breaks." + blockName + "-" + blockData + ".success_rate");
                canBreakBlock = config.contains(toolName + ".breaks." + blockName + "-" + blockData);
                experienceGained = config.getLong(toolName + ".breaks." + blockName + "-" + blockData + ".exp");
                level = experienceCalculator.getLevel(playerProfileData.getFarmingExperience());
                break;
            case MINING:
                chance = config.getInt(toolName + ".breaks." + blockName + ".success_rate");
                canBreakBlock = config.contains(toolName + ".breaks." + blockName);
                experienceGained = config.getLong(toolName + ".breaks." + blockName + ".exp");
                level = experienceCalculator.getLevel(playerProfileData.getMiningExperience());
                break;
            case WOOD_CUTTING:
                chance = config.getInt(toolName + ".breaks." + blockName + "-" + blockData + ".success_rate");
                canBreakBlock = config.contains(toolName + ".breaks." + blockName + "-" + blockData);
                tempData = (byte) config.getInt(toolName + ".breaks." + blockName + "-" + blockData + ".temp_data");
                experienceGained = config.getLong(toolName + ".breaks." + blockName + "-" + blockData + ".exp");
                level = experienceCalculator.getLevel(playerProfileData.getLumberjackExperience());
                break;
        }

        // Make sure the block to break is happening with the right tool.
        if (!canBreakBlock) {
            sendFailNotification(player, SpigotCoreMessages.PROFESSION_WRONG_TOOL.toString());
            return;
        }

        // Test to see if the profession action roll is a success.
        if (!RandomChance.testChance(chance)) {
            // Send failed notifications.
            sendFailNotification(player, SpigotCoreMessages.PROFESSION_ACTION_FAILED.toString());
            return;
        }

        // Make sure the player has a high enough level to use this tool
        if (!checkBlockBreakToolLevels(level, tool)) {
            sendFailNotification(player, SpigotCoreMessages.PROFESSION_LEVEL_NOT_HIGH_ENOUGH.toString());
            return;
        }

        byte dropData = 0;

        // Get the current experience and set the new experience.
        // Then replace the broken block with a temp block.
        switch (professionType) {
            case FARMING:
                materialToDrop = Material.valueOf(config.getString(toolName + ".breaks." + blockName + "-" + blockData + ".drop"));
                currentExperience = playerProfileData.getFarmingExperience();
                playerProfileData.setFarmingExperience(currentExperience + experienceGained);
                //Here we set the block data to 0 for replanting
                blockRegen.setBlock(blockType, (byte) 0, Material.AIR, block.getLocation());
                break;
            case MINING:
                materialToDrop = blockType;
                currentExperience = playerProfileData.getMiningExperience();
                playerProfileData.setMiningExperience(currentExperience + experienceGained);
                blockRegen.setBlock(blockType, blockData, Material.STONE, block.getLocation());
                break;
            case WOOD_CUTTING:
                useDropData = true;
                materialToDrop = blockType;
                dropData = (byte) config.getInt(toolName + ".breaks." + blockName + "-" + blockData + ".drop_data");
                currentExperience = playerProfileData.getLumberjackExperience();
                playerProfileData.setLumberjackExperience(currentExperience + experienceGained);
                blockRegen.setBlock(blockType, blockData, Material.STAINED_CLAY, tempData, block.getLocation());
                break;
        }

        // Drop item
        ItemStack drop = null;
        if (useDropData) {
            drop = new ItemStack(materialToDrop, 1, (short) 0, dropData);
        } else {
            drop = new ItemStack(materialToDrop);
        }

        player.getWorld().dropItemNaturally(block.getLocation(), drop);

        // Drop Gems
        dropGems(player, block.getLocation());

        // Show experience related messages.
        showExperienceMessages(player, currentExperience, experienceGained);
    }

    /**
     * This will be toggled when a successful fishing event has happened.
     *
     * @param player The player who has successfully fished.
     */
    public void toggleFishingProfession(Player player) {
        PlayerProfileData playerProfileData = plugin.getProfileManager().getProfile(player);
        long currentExperience = playerProfileData.getFishingExperience();
        long currentLevel = experienceCalculator.getLevel(currentExperience);
        long experienceGained = 0;
        int tier = 0;

        // Make sure the user has learned the profession first.
        if (!playerProfileData.isFishingActive()) {
            sendFailNotification(player, SpigotCoreMessages.PROFESSION_NOT_LEARNED.toString());
            return;
        }

        if (currentLevel < 20) {
            //Tier 1 Fishing
            tier = 1;
            experienceGained = fishingConfig.getLong(0 + ".exp");
        } else if (currentLevel >= 20 && currentLevel < 40) {
            //Tier 2 Fishing
            tier = 2;
            experienceGained = fishingConfig.getLong(20 + ".exp");
        } else if (currentLevel >= 40 && currentLevel < 60) {
            //Tier 3 Fishing
            tier = 3;
            experienceGained = fishingConfig.getLong(40 + ".exp");
        } else if (currentLevel >= 60 && currentLevel < 80) {
            //Tier 4 Fishing
            tier = 4;
            experienceGained = fishingConfig.getLong(60 + ".exp");
        } else if (currentLevel > 80) {
            //Tier 5 Fishing
            tier = 5;
            experienceGained = fishingConfig.getLong(80 + ".exp");
        }

        World world = player.getWorld();

        // Fish drops
        int fishType = RandomChance.randomInt(1, tier);
        switch (fishType) {
            case 1:
                world.dropItem(player.getLocation(), new ItemStack(Material.RAW_FISH));
                break;
            case 2:
                world.dropItem(player.getLocation(), new ItemStack(Material.RAW_FISH, 1, (short) 0, (byte) 1));
                break;
            case 3:
                world.dropItem(player.getLocation(), new ItemStack(Material.RAW_FISH, 1, (short) 0, (byte) 2));
                break;
            case 4:
                world.dropItem(player.getLocation(), new ItemStack(Material.RAW_FISH, 1, (short) 0, (byte) 3));
                break;
            case 5:
                world.dropItem(player.getLocation(), new ItemStack(Material.COOKED_FISH));
                break;

        }

        // Drop Gems
        dropGems(player, player.getLocation());

        // Set the experience
        playerProfileData.setFishingExperience(currentExperience + experienceGained);

        // Show experience related messages.
        showExperienceMessages(player, currentExperience, experienceGained);
    }

    /**
     * This is the furnace profession. This includes cooking and smelting.
     *
     * @param player   The player who used the furnace.
     * @param amount   The amount of items cooked or smelted.
     * @param material The material that was cooked or smelted.
     */
    public void toggleFurnaceProfession(Player player, int amount, Material material) {
        String item = material.toString();

        if (!furnaceMaterials.contains(material)) {
            player.sendMessage(ChatColor.RED + "Smelting this item does not reward experience.");
            CommonSounds.ACTION_FAILED.playSound(player);
            return;
        }

        PlayerProfileData playerProfileData = plugin.getProfileManager().getProfile(player);
        int experienceGained = furnaceConfig.getInt(item + ".exp") * amount;
        long currentExperience = 0;
        String profession = furnaceConfig.getString(item + ".profession");

        if (!playerProfileData.isCookingActive() || !playerProfileData.isSmeltingActive()) {
            player.sendMessage(ChatColor.RED + "You have not learned this profession. No experience will be rewarded.");
            CommonSounds.ACTION_FAILED.playSound(player);
            return;
        }

        if (profession.equals("COOKING")) {
            currentExperience = playerProfileData.getCookingExperience();
            playerProfileData.setCookingExperience(currentExperience + experienceGained);
        } else if (profession.equals("SMELTING")) {
            currentExperience = playerProfileData.getSmeltingExperience();
            playerProfileData.setSmeltingExperience(currentExperience + experienceGained);
        }

        // Show experience related messages.
        showExperienceMessages(player, currentExperience, experienceGained);
    }

    /**
     * This will show the experience gained to the player and show leveling notifications.
     *
     * @param player            The player who is performing a profession action.
     * @param currentExperience The professions current experience prior to the profession action.
     * @param experienceGained  The experience gained after the profession action.
     */
    private void showExperienceMessages(Player player, long currentExperience, long experienceGained) {
        int oldLevel = experienceCalculator.getLevel(currentExperience);
        int level = experienceCalculator.getLevel(currentExperience + experienceGained);
        long exp = currentExperience + experienceGained;

        // Send ActionBar title message.
        plugin.getTitleManagerAPI().sendActionbar(player, generateActionBarMessage(experienceGained, exp, level));

        //Level up check
        if (oldLevel != level) {

            // Show Leveling message!
            player.sendMessage("");
            player.sendMessage(ChatColor.GOLD + "Congratulations! Your profession skill has leveled up!");

            if (level == 20 || level == 40 || level == 60 || level == 80) {
                player.sendMessage(ChatColor.AQUA + "You can now upgrade your profession tool!");
                player.sendMessage(ChatColor.AQUA + "Visit your profession trainer for more details!");
            }

            // Send success message
            player.sendMessage(ChatColor.GREEN + "Your profession is now level " + ChatColor.GOLD + level + ChatColor.GREEN + ".");

            // Play success sound
            CommonSounds.ACTION_SUCCESS.playSound(player);

            // Show Fireworks for leveling!
            for (double i = 0; i < 2; i++) {
                Firework fw = player.getWorld().spawn(player.getLocation().subtract(0, -1, 0), Firework.class);
                FireworkMeta fm = fw.getFireworkMeta();
                fm.addEffect(FireworkEffect.builder()
                        .flicker(false)
                        .trail(false)
                        .with(FireworkEffect.Type.STAR)
                        .withColor(Color.YELLOW)
                        .withFade(Color.YELLOW)
                        .build());
                fw.setFireworkMeta(fm);
            }
        }
    }

    /**
     * This will generate a message to show the player for using their profession item.
     *
     * @param expGain   The amount of exp they have gained this block break.
     * @param itemEXP   The amount of exp they have in total.
     * @param itemLevel The current level of their profession.
     * @return Returns a detailed and formatted message to show to the player.
     */
    private String generateActionBarMessage(long expGain, long itemEXP, int itemLevel) {
        double expPercent = experienceCalculator.getPercentToLevel(itemEXP);
        int expGoal = experienceCalculator.getExperience(itemLevel + 1) - expOffSet;
        int friendlyExpShow = (int) itemEXP - expOffSet;
        String bar = ProgressBarString.buildBar(expPercent);
        return ChatColor.GRAY + "" + ChatColor.BOLD +
                "EXP: " +
                bar +
                ChatColor.GRAY + ChatColor.BOLD + " " + expPercent + "%" +
                ChatColor.RESET + ChatColor.GRAY + " [" +
                ChatColor.BLUE + friendlyExpShow + " / " + expGoal +
                ChatColor.RESET + ChatColor.GRAY + "] "
                + ChatColor.GREEN + "+" + ChatColor.GRAY + expGain + " EXP";
    }

    /**
     * Generic fail notification to show the user if the profession action failed.
     *
     * @param player The player we will send the message to.
     */
    public void sendFailNotification(Player player, String message) {
        // Send failed notifications.
        player.sendMessage(message);
        CommonSounds.ACTION_FAILED.playSound(player);
    }

    /**
     * This loads all block breaking related configurations.
     */
    private void loadConfigs() {
        ConfigurationSection farmingSection = farmingConfig.getConfigurationSection("");
        ConfigurationSection miningSection = miningConfig.getConfigurationSection("");
        ConfigurationSection woodCuttingSection = woodCuttingConfig.getConfigurationSection("");
        ConfigurationSection smeltingSection = furnaceConfig.getConfigurationSection("");

        // Add farming tools.
        for (String s : farmingSection.getKeys(false)) {
            blockBreakTools.put(s, ProfessionType.FARMING);
        }

        // Add mining tools.
        for (String s : miningSection.getKeys(false)) {
            blockBreakTools.put(s, ProfessionType.MINING);
        }

        // Add wood cutting tools.
        for (String s : woodCuttingSection.getKeys(false)) {
            blockBreakTools.put(s, ProfessionType.WOOD_CUTTING);
        }

        // Add smelting materials
        for (String s : smeltingSection.getKeys(false)) {
            furnaceMaterials.add(Material.valueOf(s));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onProfessionToggle(BlockBreakEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        Material tool = player.getInventory().getItemInMainHand().getType();
        toggleBlockBreakProfession(player, tool, event.getBlock());
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        event.setExpToDrop(0); // Prevent player from getting exp

        switch (event.getState()) {
            case FISHING:
                break;
            case CAUGHT_FISH:
                toggleFishingProfession(event.getPlayer());
                event.getCaught().remove();
                break;
            case CAUGHT_ENTITY:
                toggleFishingProfession(event.getPlayer());
                event.getCaught().remove();
                break;
            case IN_GROUND:
                break;
            case FAILED_ATTEMPT:
                break;
            case BITE:
                break;
        }
    }

    @EventHandler
    public void onFurnaceExtract(FurnaceExtractEvent event) {
        event.setExpToDrop(0); // Do not give default Minecraft experience.
        toggleFurnaceProfession(event.getPlayer(), event.getItemAmount(), event.getItemType());
    }
}
