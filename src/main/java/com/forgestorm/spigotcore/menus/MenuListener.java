package com.forgestorm.spigotcore.menus;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.CommonSounds;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import com.forgestorm.spigotcore.util.math.RandomChance;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

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

public class MenuListener implements Listener {

    private final SpigotCore plugin;

    public MenuListener(SpigotCore plugin) {
        this.plugin = plugin;

        // Register Events
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void onDisable() {
        // Unregister Events
        InventoryClickEvent.getHandlerList().unregister(this);
        InventoryCloseEvent.getHandlerList().unregister(this);
        InventoryDragEvent.getHandlerList().unregister(this);
        InventoryOpenEvent.getHandlerList().unregister(this);
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        PlayerProfileData playerProfileData = plugin.getProfileManager().getProfile(player);

        if (playerProfileData == null) return;
        if (event.getClickedInventory() == null) return;

        if (event.getCurrentItem().getType().equals(Material.COMPASS)) event.setCancelled(true);

        Menu menu = playerProfileData.getCurrentMenu();

        if (menu == null) return;

        //Is the inventory clicked the menu?
        if (event.getClickedInventory().getType().equals(InventoryType.CHEST)) {
            menu.performAction(player, event.getSlot());

            if (!menu.isMovableSlot(event.getRawSlot())) event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        PlayerProfileData profile = plugin.getProfileManager().getProfile(player);

        // Let players move things in their furnace.
        if (event.getInventory().getType().equals(InventoryType.FURNACE)) return;

        if (profile == null) return;

        if (profile.getCurrentMenu() != null) {
            Menu menu = profile.getCurrentMenu();

            if (menu instanceof CraftingMenu) {
                ((CraftingMenu) menu).onClose(player);
            }
        }

        profile.setCurrentMenu(null);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();

        // Let players move things in their furnace.
        if (event.getInventory().getType().equals(InventoryType.FURNACE)) return;

        // Toggle menu related code.
        if (plugin.getProfileManager().getProfile(player).getCurrentMenu() != null) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            Location playerLocation = player.getLocation();
            Inventory inventory = event.getInventory();
            InventoryType inventoryType = inventory.getType();

            switch (inventoryType) {
                case ANVIL:
                    // OPEN MENU
                    if (player.getWorld().equals(Bukkit.getWorlds().get(0))) {
                        event.setCancelled(true);
                        new RecipeOptionsMenu(plugin).open(player);
                        int rand = RandomChance.randomInt(1, 100);

                        // Player sound.
                        if (rand < 25) {
                            player.playSound(playerLocation, Sound.BLOCK_ANVIL_USE, .8f, .8f);
                        } else if (rand >= 25 && rand < 50) {
                            player.playSound(playerLocation, Sound.BLOCK_ANVIL_HIT, .8f, .8f);
                        } else if (rand >= 50 && rand < 75) {
                            player.playSound(playerLocation, Sound.BLOCK_ANVIL_PLACE, .8f, .8f);
                        } else if (rand >= 75) {
                            player.playSound(playerLocation, Sound.BLOCK_ANVIL_DESTROY, .8f, .8f);
                        }
                    }
                    break;
                case ENCHANTING:
                    CommonSounds.ACTION_FAILED.playSound(player);
                    event.setCancelled(true);
                    break;
                case MERCHANT:
                    event.setCancelled(true);
                    break;
                case HOPPER:
                    if (player.getWorld().equals(Bukkit.getWorlds().get(0))) event.setCancelled(true);
                    break;
            }
        }
    }
}
