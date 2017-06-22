package com.forgestorm.spigotcore.player;

import com.forgestorm.spigotcore.SpigotCore;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

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

public class DoubleJump implements Listener {

    private final SpigotCore plugin;

    public DoubleJump(SpigotCore plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void onDisable() {
        // Unregister listeners
        PlayerMoveEvent.getHandlerList().unregister(this);
        PlayerToggleFlightEvent.getHandlerList().unregister(this);
    }

    public void setupPlayer(Player player) {
        player.setAllowFlight(true);
        player.setFlying(false);
    }

    public void removePlayer(Player player) {
        player.setAllowFlight(false);
        player.setFlying(false);
    }

    /**
     * Test to see if the player is in an animated tutorial.
     *
     * @param player The player who we will test.
     * @return True if they are in a tutorial, false otherwise.
     */
    private boolean inAnimatedTutorial(Player player) {
        return plugin.getAnimatedTutorial().getActivePlayers().containsKey(player);
    }

    /**
     * Test to see if the player is in a main lobby world.
     *
     * @param player The player we will test.
     * @return True if they are in the main world, false otherwise.
     */
    private boolean inMainWorld(Player player) {
        return player.getWorld().equals(Bukkit.getWorlds().get(0));
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (inAnimatedTutorial(player)) return;
        if (!inMainWorld(player)) return;

        // Do double jump.
        if ((player.getGameMode() != GameMode.CREATIVE) && (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
            player.setAllowFlight(true);
        }
    }

    @EventHandler
    public void onPlayerFly(PlayerToggleFlightEvent event) {
        Player player = event.getPlayer();

        if (inAnimatedTutorial(player)) return;
        if (!inMainWorld(player)) return;

        // Do double jump.
        if (player.getGameMode() != GameMode.CREATIVE) {
            event.setCancelled(true);
            player.setAllowFlight(false);
            player.setFlying(false);
            player.setVelocity(player.getLocation().getDirection().multiply(2.0D).setY(0.9D));
            Bukkit.getWorlds().get(0).playSound(player.getLocation(), Sound.ENTITY_BLAZE_SHOOT, 1f, 1f);
        }
    }

    @EventHandler
    public void onEntityDamage(final EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) event.setCancelled(true);
    }
}
