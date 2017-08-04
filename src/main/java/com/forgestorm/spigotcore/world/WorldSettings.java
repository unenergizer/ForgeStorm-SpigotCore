package com.forgestorm.spigotcore.world;

import com.forgestorm.spigotcore.SpigotCore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.ChunkLoadEvent;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: forgestorm-spigotcore
 * DATE: 6/23/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 ForgeStorm.com. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be 
 * reproduced, distributed, or transmitted in any form or by any means, 
 * including photocopying, recording, or other electronic or mechanical methods, 
 * without the prior written permission of the owner.
 */

public class WorldSettings implements Listener {

    private final SpigotCore plugin;

    public WorldSettings(SpigotCore plugin) {
        this.plugin = plugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void onDisable() {
        // Unregister Events
        BlockIgniteEvent.getHandlerList().unregister(this);
        ChunkLoadEvent.getHandlerList().unregister(this);
        EntityChangeBlockEvent.getHandlerList().unregister(this);
        WeatherChangeEvent.getHandlerList().unregister(this);
    }

    /**
     * Stop fire from being ignited, unless its a player.
     *
     * @param event Block ignite event.
     */
    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (event.getPlayer() == null) event.setCancelled(true);
    }

    /**
     * Prevent new chunks.
     *
     * @param event Chunk load event.
     */
    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        if (event.isNewChunk()) event.getChunk().unload(false);
    }

    /**
     * Prevent entities from changing blocks. For instance, this would
     * prevent Endermen from moving/removing blocks.
     *
     * @param event Entity change block event.
     */
    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        event.setCancelled(true);
    }

    /**
     * Prevent living entities from catching on fire.
     *
     * @param event Entity combust event.
     */
    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
        if ((event.getEntity() instanceof Player)) return;
        event.setCancelled(true);
    }

    /**
     * Prevent weather changes.
     *
     * @param event Weather change event.
     */
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}
