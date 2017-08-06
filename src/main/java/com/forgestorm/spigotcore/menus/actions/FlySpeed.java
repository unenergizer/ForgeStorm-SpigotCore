package com.forgestorm.spigotcore.menus.actions;

import com.forgestorm.spigotcore.constants.CommonSounds;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: forgestorm-spigotcore
 * DATE: 8/5/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 ForgeStorm.com. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be 
 * reproduced, distributed, or transmitted in any form or by any means, 
 * including photocopying, recording, or other electronic or mechanical methods, 
 * without the prior written permission of the owner.
 */

public class FlySpeed implements ClickAction {

    private final float flySpeed;
    private final String message;

    public FlySpeed(float flySpeed, String message) {
        this.flySpeed = flySpeed;
        this.message = message;
    }

    @Override
    public void click(Player player) {
        //Close the players menu.
        if (player.getInventory() != null) player.closeInventory();
        player.setFlySpeed(flySpeed);
        player.sendMessage(ChatColor.GREEN + message);
        CommonSounds.ACTION_SUCCESS.playSound(player);
    }
}
