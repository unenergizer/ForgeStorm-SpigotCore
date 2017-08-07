package com.forgestorm.spigotcore.professions;

import com.forgestorm.spigotcore.constants.ProfessionType;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: forgestorm-spigotcore
 * DATE: 6/21/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 ForgeStorm.com. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be 
 * reproduced, distributed, or transmitted in any form or by any means, 
 * including photocopying, recording, or other electronic or mechanical methods, 
 * without the prior written permission of the owner.
 */

public class ProfessionToggleEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    @Getter
    private final Player player;
    @Getter
    private final ProfessionType professionType;

    public ProfessionToggleEvent(Player player, ProfessionType professionType) {
        this.player = player;
        this.professionType = professionType;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
