package com.forgestorm.spigotcore.professions.blockbreak;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ProfessionType;
import com.forgestorm.spigotcore.constants.SpigotCoreMessages;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import com.forgestorm.spigotcore.professions.LoadConfiguration;
import com.forgestorm.spigotcore.professions.Profession;
import com.forgestorm.spigotcore.util.math.RandomChance;
import com.forgestorm.spigotcore.world.BlockRegenerationManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: forgestorm-spigotcore
 * DATE: 8/6/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 ForgeStorm.com. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be 
 * reproduced, distributed, or transmitted in any form or by any means, 
 * including photocopying, recording, or other electronic or mechanical methods, 
 * without the prior written permission of the owner.
 */

public abstract class BlockBreakProfession extends Profession implements LoadConfiguration {

    final BlockRegenerationManager blockRegen = plugin.getBlockRegen();
    final Map<String, ProfessionType> blockBreakTools = new HashMap<>();

    BlockBreakProfession(SpigotCore plugin, FileConfiguration fileConfiguration, ProfessionType professionType) {
        super(plugin, fileConfiguration, professionType);
        loadConfiguration();
    }

    /**
     * Make sure the player has the right level for the tool being used.
     *
     * @param level The players profession level.
     * @param tool  The tool being used.
     * @return True if the level requirement is met.
     */
    private static boolean checkBlockBreakToolLevels(int level, Material tool) {
        switch (tool) {
            case WOOD_AXE:
            case WOOD_HOE:
            case WOOD_PICKAXE:
            case WOOD_SPADE:
                return true;
            case STONE_AXE:
            case STONE_HOE:
            case STONE_PICKAXE:
            case STONE_SPADE:
                if (level >= 20) return true;
                break;
            case IRON_AXE:
            case IRON_HOE:
            case IRON_PICKAXE:
            case IRON_SPADE:
                if (level >= 40) return true;
                break;
            case DIAMOND_AXE:
            case DIAMOND_HOE:
            case DIAMOND_PICKAXE:
            case DIAMOND_SPADE:
                if (level >= 60) return true;
                break;
            case GOLD_AXE:
            case GOLD_HOE:
            case GOLD_PICKAXE:
            case GOLD_SPADE:
                if (level >= 80) return true;
                break;
        }
        return false; //The player does not meet the requirements to use the tool.
    }

    @Override
    public void onDisable() {
        BlockBreakEvent.getHandlerList().unregister(this);
    }

    public abstract boolean hasProfession(PlayerProfileData playerProfileData);

    public abstract int getLevel(PlayerProfileData playerProfileData);

    public abstract long currentExperience(PlayerProfileData playerProfileData);

    public abstract void setExperience(PlayerProfileData playerProfileData, long expGained);

    public abstract Material getDropMaterial(String toolName, String blockName, byte blockData, Material blockType);

    public abstract void setBlockRegen(Material blockType, byte blockData, byte tempData, Location blockLocation);

    /**
     * This is used to check if a profession skill was successful.
     *
     * @param player       The player who toggled the event.
     * @param toolMaterial The tool the player was using when the event was toggled.
     * @param brokenBlock  The block the player broke during the event.
     */
    private void toggleProfession(Player player, Material toolMaterial, Block brokenBlock) {
        PlayerProfileData playerProfileData = plugin.getProfileManager().getProfile(player);
        String toolName = toolMaterial.toString();
        String blockName = brokenBlock.getType().toString();
        Material blockType = brokenBlock.getType();
        Location blockLocation = brokenBlock.getLocation();
        @SuppressWarnings("deprecation") byte blockData = brokenBlock.getData();
        long experienceGained = fileConfiguration.getLong(toolName + ".breaks." + blockName + "-" + blockData + ".exp");
        long currentExperience = currentExperience(playerProfileData);

        // If the tool exists in the map, continue. Else, stop.
        if (!blockBreakTools.containsKey(toolName) || blockName == null) return;

        // Make sure the user has learned the profession first.
        if (!hasProfession(playerProfileData)) {
            // TODO: Implement profession specific message.
            sendFailNotification(player, SpigotCoreMessages.PROFESSION_NOT_LEARNED.toString());
            return;
        }

        // Make sure the block to break is happening with the right tool.
        if (!fileConfiguration.contains(toolName + ".breaks." + blockName + "-" + blockData)) {
            // TODO: Implement profession specific message.
            sendFailNotification(player, SpigotCoreMessages.PROFESSION_WRONG_TOOL.toString());
            return;
        }

        // Test to see if the profession action roll is a success.
        if (!RandomChance.testChance(fileConfiguration.getInt(toolName + ".breaks." + blockName + "-" + blockData + ".success_rate"))) {
            // Send failed notifications.
            sendFailNotification(player, SpigotCoreMessages.PROFESSION_ACTION_FAILED.toString());
            return;
        }

        // Make sure the player has a high enough level to use this tool
        if (!checkBlockBreakToolLevels(getLevel(playerProfileData), toolMaterial)) {
            sendFailNotification(player, SpigotCoreMessages.PROFESSION_LEVEL_NOT_HIGH_ENOUGH.toString());
            return;
        }

        // Set the experience gained.
        setExperience(playerProfileData, currentExperience + experienceGained);

        // Set the block to regenerate.
        setBlockRegen(blockType, blockData, getTempData(toolName, blockName, blockData), blockLocation);

        // Drop item
        //noinspection deprecation
        player.getWorld().dropItemNaturally(blockLocation,
                new ItemStack(
                        getDropMaterial(toolName, blockName, blockData, blockType),
                        1,
                        (short) 0,
                        getDropData(toolName, blockName, blockData)
                )
        );

        // Drop Gems
        dropGems(player, blockLocation);

        // Show experience related messages.
        showExperienceMessages(player, currentExperience, experienceGained);
    }

    /**
     * Provides ItemStack texture data for our temporary block.
     *
     * @param toolName  The tool used to break the block.
     * @param blockName The block that was broken.
     * @param blockData The byte data of the broken block.
     * @return The data to use if it exists. Defaults to 0 otherwise.
     */
    private byte getTempData(String toolName, String blockName, byte blockData) {
        byte num = 0;
        if (fileConfiguration.contains(toolName + ".breaks." + blockName + "-" + blockData + ".temp_data")) {
            num = (byte) fileConfiguration.getInt(toolName + ".breaks." + blockName + "-" + blockData + ".temp_data");
        }
        return num;
    }

    /**
     * Provides ItemStack texture data for the block to drop on the ground.
     *
     * @param toolName  The tool used to break the block.
     * @param blockName The block that was broken.
     * @param blockData The byte data of the broken block.
     * @return The data to use if it exists. Defaults to 0 otherwise.
     */
    private byte getDropData(String toolName, String blockName, byte blockData) {
        byte num = 0;
        if (fileConfiguration.contains(toolName + ".breaks." + blockName + "-" + blockData + ".drop_data")) {
            num = (byte) fileConfiguration.getInt(toolName + ".breaks." + blockName + "-" + blockData + ".drop_data");
        }
        return num;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
        Player player = event.getPlayer();
        Material tool = player.getInventory().getItemInMainHand().getType();
        toggleProfession(player, tool, event.getBlock());
    }
}
