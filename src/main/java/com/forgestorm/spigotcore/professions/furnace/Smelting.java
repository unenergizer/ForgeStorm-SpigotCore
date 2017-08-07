package com.forgestorm.spigotcore.professions.furnace;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ProfessionType;
import com.forgestorm.spigotcore.database.PlayerProfileData;

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

public class Smelting extends FurnaceProfession {

    public Smelting(SpigotCore plugin) {
        super(plugin, ProfessionType.SMELTING);
    }

    @Override
    public long getExperience(PlayerProfileData playerProfileData) {
        return playerProfileData.getSmeltingExperience();
    }

    @Override
    public void setExperience(PlayerProfileData playerProfileData, long experience) {
        playerProfileData.setSmeltingExperience(experience);
    }
}
