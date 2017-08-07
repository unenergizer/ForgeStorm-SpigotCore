package com.forgestorm.spigotcore.professions;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.professions.blockbreak.Farming;
import com.forgestorm.spigotcore.professions.blockbreak.Mining;
import com.forgestorm.spigotcore.professions.blockbreak.WoodCutting;
import com.forgestorm.spigotcore.professions.fishing.FishingProfession;
import com.forgestorm.spigotcore.professions.furnace.Cooking;
import com.forgestorm.spigotcore.professions.furnace.PrivateFurnace;
import com.forgestorm.spigotcore.professions.furnace.Smelting;
import com.forgestorm.spigotcore.util.logger.ColorLogger;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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

@SuppressWarnings("unused")
public class ProfessionToggle {

    private final SpigotCore plugin;
    private final List<Profession> professions = new ArrayList<>();
    private PrivateFurnace privateFurnaces;
    @Getter
    private boolean professionsEnabled = false;

    public ProfessionToggle(SpigotCore plugin) {
        this.plugin = plugin;

        initProfessions();
    }

    /**
     * This will add all professions to the professions list.
     */
    private void initProfessions() {
        // Add BlockBreakProfessions
        professions.add(new Farming(plugin));
        professions.add(new Mining(plugin));
        professions.add(new WoodCutting(plugin));

        // Add Fishing Profession
        professions.add(new FishingProfession(plugin));

        // Add Furnace Professions
        professions.add(new Cooking(plugin));
        professions.add(new Smelting(plugin));
    }

    /**
     * This will enable all professions.
     */
    public void enableProfessions() {
        professionsEnabled = true;
        for (Profession profession : professions) {
            profession.onEnable();
            ColorLogger.DEBUG.printLog(profession.getClass().getSimpleName() + " enabled");
        }

        // Startup private furnace manager.
        privateFurnaces = new PrivateFurnace(plugin);
        privateFurnaces.runTaskTimer(plugin, 0, 20);
    }

    /**
     * This will disable all professions.
     */
    public void disableProfessions() {
        professionsEnabled = false;
        for (Profession profession : professions) {
            profession.onDisable();
            ColorLogger.DEBUG.printLog(profession.getClass().getSimpleName() + " disabled");
        }

        // Disable the private furnaces.
        privateFurnaces.onDisable();
    }
}
