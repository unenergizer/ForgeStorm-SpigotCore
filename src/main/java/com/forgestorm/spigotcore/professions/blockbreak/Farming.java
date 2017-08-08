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

public class Farming extends BlockBreakProfession {

    public Farming(SpigotCore plugin) {
        super(plugin,
                YamlConfiguration.loadConfiguration(new File(FilePaths.PROFESSION_FARMING.toString())),
                ProfessionType.FARMING);
    }

    @Override
    public void loadConfiguration() {
        ConfigurationSection farmingSection = fileConfiguration.getConfigurationSection("");

        // Add farming tools.
        for (String s : farmingSection.getKeys(false)) {
            blockBreakTools.put(s, professionType);
        }
    }

    @Override
    public boolean hasProfession(PlayerProfileData playerProfileData) {
        return playerProfileData.isFarmingActive();
    }

    @Override
    public int getLevel(PlayerProfileData playerProfileData) {
        return experienceCalculator.getLevel(playerProfileData.getFarmingExperience());
    }

    @Override
    public long currentExperience(PlayerProfileData playerProfileData) {
        return playerProfileData.getFarmingExperience();
    }

    @Override
    public void setExperience(PlayerProfileData playerProfileData, long expGained) {
        playerProfileData.setFarmingExperience(expGained);
    }

    @Override
    public Material getDropMaterial(String toolName, String blockName, byte blockData, Material blockType) {
        return Material.valueOf(fileConfiguration.getString(toolName + ".breaks." + blockName + "-" + blockData + ".drop"));
    }

    @Override
    public void setBlockRegen(Material blockType, byte blockData, byte tempData, Location blockLocation) {
        blockRegen.setBlock(blockType, (byte) 0, Material.AIR, blockLocation);
    }

    @Override
    public String getUpgrades(int rankUpgradeLevel) {
        String result = "";
        if (rankUpgradeLevel == 20) {
            result = "&aStone Hoe &7and &aCarrot Harvesting";
        } else if (rankUpgradeLevel == 40) {
            result = "&aIron Hoe &7and &aPotato Harvesting";
        } else if (rankUpgradeLevel == 60) {
            result = "&aDiamond Hoe &7and &aBeetroot Harvesting";
        } else if (rankUpgradeLevel == 80) {
            result = "&aGold Hoe &7and &aMelon Harvesting";
        } else if (rankUpgradeLevel == 100) {
            result = "&aPrestige level 1";
        }
        return color(result);
    }
}
