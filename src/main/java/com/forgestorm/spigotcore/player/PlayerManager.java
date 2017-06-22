package com.forgestorm.spigotcore.player;

import com.forgestorm.spigotcore.SpigotCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: forgestorm-spigotcore
 * DATE: 6/13/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 ForgeStorm.com. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be 
 * reproduced, distributed, or transmitted in any form or by any means, 
 * including photocopying, recording, or other electronic or mechanical methods, 
 * without the prior written permission of the owner.
 */

public class PlayerManager implements Listener {

    private final SpigotCore plugin;

    public PlayerManager(SpigotCore plugin) {
        this.plugin = plugin;

        // Register Events
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void onDisable() {
        // Unregister Events
        PlayerJoinEvent.getHandlerList().unregister(this);
        PlayerKickEvent.getHandlerList().unregister(this);
        PlayerQuitEvent.getHandlerList().unregister(this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Load in the player data from MongoDB.
        plugin.getProfileManager().getLoadingPlayers().add(player);
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        Player player = event.getPlayer();

        //Remove the player
        new RemoveNetworkPlayer(plugin, player, false);

        //Do not show logout message.
        event.setLeaveMessage("");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        //Remove the player
        new RemoveNetworkPlayer(plugin, player, false);

        //Do not show logout message.
        event.setQuitMessage("");
    }
}
