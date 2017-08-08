package com.forgestorm.spigotcore.professions.blockbreak;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.FilePaths;
import com.forgestorm.spigotcore.constants.ProfessionType;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

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

public class WoodCutting extends BlockBreakProfession {

    public WoodCutting(SpigotCore plugin) {
        super(plugin,
                YamlConfiguration.loadConfiguration(new File(FilePaths.PROFESSION_WOOD_CUTTING.toString())),
                ProfessionType.WOOD_CUTTING);
    }

    @Override
    public void loadConfiguration() {
        ConfigurationSection woodCuttingSection = fileConfiguration.getConfigurationSection("");

        // Add wood cutting tools.
        for (String s : woodCuttingSection.getKeys(false)) {
            blockBreakTools.put(s, professionType);
        }
    }

    @Override
    public boolean hasProfession(PlayerProfileData playerProfileData) {
        return playerProfileData.isLumberjackActive();
    }

    @Override
    public int getLevel(PlayerProfileData playerProfileData) {
        return experienceCalculator.getLevel(playerProfileData.getLumberjackExperience());
    }

    @Override
    public long currentExperience(PlayerProfileData playerProfileData) {
        return playerProfileData.getLumberjackExperience();
    }

    @Override
    public void setExperience(PlayerProfileData playerProfileData, long expGained) {
        playerProfileData.setLumberjackExperience(expGained);
    }

    @Override
    public Material getDropMaterial(String toolName, String blockName, byte blockData, Material blockType) {
        return blockType;
    }

    @Override
    public void setBlockRegen(Material blockType, byte blockData, byte tempData, Location blockLocation) {
        blockRegen.setBlock(blockType, blockData, Material.STAINED_CLAY, tempData, blockLocation);
    }

    @Override
    public String getUpgrades(int rankUpgradeLevel) {
        String result = "";
        if (rankUpgradeLevel == 20) {
            result = "&aStone Pick &7and &aChop Spruce Logs";
        } else if (rankUpgradeLevel == 40) {
            result = "&aIron Pick &7and &aChop Birch Logs";
        } else if (rankUpgradeLevel == 60) {
            result = "&aDiamond Pick &7and &aChop Jungle Logs";
        } else if (rankUpgradeLevel == 80) {
            result = "&aGold Pick &7and &aChop Acacia Logs";
        } else if (rankUpgradeLevel == 100) {
            result = "&aPrestige level 1";
        }
        return color(result);
    }
}
