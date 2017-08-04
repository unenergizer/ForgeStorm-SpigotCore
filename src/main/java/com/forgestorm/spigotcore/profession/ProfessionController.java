package com.forgestorm.spigotcore.profession;

import com.forgestorm.spigotcore.SpigotCore;
import lombok.Getter;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: forgestorm-spigotcore
 * DATE: 6/24/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 ForgeStorm.com. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be 
 * reproduced, distributed, or transmitted in any form or by any means, 
 * including photocopying, recording, or other electronic or mechanical methods, 
 * without the prior written permission of the owner.
 */
@Getter
public class ProfessionController {

    private final SpigotCore plugin;
    private Profession profession;
    private PrivateFurnace privateFurnaces;
    private boolean professionsEnabled = false;

    public ProfessionController(SpigotCore plugin) {
        this.plugin = plugin;
    }

    /**
     * Enables player professions.
     */
    public void enableProfessions() {
        profession = new Profession(plugin);
        privateFurnaces = new PrivateFurnace(plugin);

        // Start Bukkit Tasks
        privateFurnaces.runTaskTimer(plugin, 0, 20);

        professionsEnabled = true;
    }

    /**
     * Disable player professions.
     */
    public void disableProfessions() {
        profession.onDisable();
        privateFurnaces.onDisable();
        professionsEnabled = false;
    }
}
