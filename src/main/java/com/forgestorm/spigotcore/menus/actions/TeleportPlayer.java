package com.forgestorm.spigotcore.menus.actions;

import com.forgestorm.spigotcore.constants.CommonSounds;
import org.bukkit.ChatColor;
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

@SuppressWarnings("unused")
public class TeleportPlayer implements ClickAction {

    private final Player targetPlayer;

    public TeleportPlayer(Player player) {
        this.targetPlayer = player;
    }

    @Override
    public void click(Player player) {
        //Close the players menu.
        if (player.getInventory() != null) player.closeInventory();
        player.teleport(targetPlayer.getLocation());
        player.sendMessage(ChatColor.GREEN + "Teleported you to " + ChatColor.YELLOW + targetPlayer.getDisplayName() + ChatColor.GREEN + ".");
        CommonSounds.ACTION_SUCCESS.playSound(player);
    }
}
