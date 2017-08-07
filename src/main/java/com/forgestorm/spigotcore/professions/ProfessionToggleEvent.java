package com.forgestorm.spigotcore.professions;

import com.forgestorm.spigotcore.constants.ProfessionType;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
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
@SuppressWarnings("WeakerAccess")
@Getter
public class ProfessionToggleEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final ProfessionType professionType;
    @Setter
    private boolean cancelled;

    public ProfessionToggleEvent(Player player, ProfessionType professionType) {
        this.player = player;
        this.professionType = professionType;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
