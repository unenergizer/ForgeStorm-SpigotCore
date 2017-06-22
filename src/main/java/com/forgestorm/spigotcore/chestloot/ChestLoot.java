package com.forgestorm.spigotcore.chestloot;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.FilePaths;
import com.forgestorm.spigotcore.util.math.RandomChance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: forgestorm-spigotcore
 * DATE: 5/29/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 ForgeStorm.com. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be 
 * reproduced, distributed, or transmitted in any form or by any means, 
 * including photocopying, recording, or other electronic or mechanical methods, 
 * without the prior written permission of the owner.
 */

public class ChestLoot extends BukkitRunnable {

    private final SpigotCore plugin;
    private final File file;
    private final FileConfiguration config;
    private final int maxChestPerPlayerOnline = 3;
    private final int maxChestTimeoutTime = 30;
    private final List<ChestTimeOut> timedOutLocations = new ArrayList<>();
    private final List<Location> spawnedChestLocations = new ArrayList<>();
    private final List<Location> allChestLocations = new ArrayList<>();

    public ChestLoot(SpigotCore plugin) {
        this.plugin = plugin;
        file = new File(FilePaths.CHEST_LOOT.toString());
        config = YamlConfiguration.loadConfiguration(file);

        Bukkit.getPluginManager().registerEvents(new LootEvent(this), plugin);

        loadLocations();
    }

    public void toggleChestLootActive(Player player, Location location, boolean blockbreak) {
        if (!spawnedChestLocations.contains(location)) return;

        // Set the chest to air.
        location.getBlock().setType(Material.AIR);
        player.playSound(location, Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 0.5F, 1.2F);

        // Remove from active locations.
        spawnedChestLocations.remove(location);

        // Add to chest lock out timer
        timedOutLocations.add(new ChestTimeOut(location, maxChestTimeoutTime));

        // Give the player chest loot
        List<ItemStack> items = generateChestLoot(player);

        if (blockbreak) {
            for (ItemStack itemStack : items) {
                location.getWorld().dropItem(location, itemStack);
            }
        } else {
            int slot = 0;
            Inventory inventory = Bukkit.createInventory(null, 9 * 3, "Loot Chest");
            for (ItemStack itemStack : items) {
                inventory.setItem(slot++, itemStack);
            }
            player.openInventory(inventory);
        }
    }

    private List<ItemStack> generateChestLoot(Player player) {
        List<ItemStack> items = new ArrayList<>();
        items.add(new ItemStack(Material.DIRT));
        items.add(new ItemStack(Material.COBBLESTONE));
        return items;
    }

    public void addChestLocation(Player player, Location location) {
        if (allChestLocations.contains(location)) return;
        allChestLocations.add(location);

        int x = location.getBlockX(), y = location.getBlockY(), z = location.getBlockZ();
        int size = allChestLocations.size();

        config.set("Locations." + size + ".x", x);
        config.set("Locations." + size + ".y", y);
        config.set("Locations." + size + ".z", z);
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendMessage(ChatColor.YELLOW + "LootChest added at X: " + x + "  Y: " + y + "  Z: " + z +
                " Total Locations: " + size);
    }

    private void loadLocations() {
        World world = Bukkit.getWorlds().get(0);
        ConfigurationSection section = config.getConfigurationSection("Locations");
        Iterator<String> it = section.getKeys(false).iterator();
        String i;

        while (it.hasNext()) {
            i = it.next();
            double x, y, z;

            x = config.getDouble("Locations." + i + ".x");
            y = config.getDouble("Locations." + i + ".y");
            z = config.getDouble("Locations." + i + ".z");

//            System.out.println("ChestLocations: ID " + i + " X:" + x);
//            System.out.println("ChestLocations: ID " + i + " Y:" + y);
//            System.out.println("ChestLocations: ID " + i + " Z:" + z);

            allChestLocations.add(new Location(world, x, y, z));
        }
    }

    public void onDisable() {
        // Lets remove all the loot chests
        for (Location location : spawnedChestLocations) {
            location.getBlock().setType(Material.AIR);
        }
    }

    private void spawnChest() {
        int maxLocations = allChestLocations.size() - 1;
        int randomLocation = RandomChance.randomInt(0, maxLocations);
        Location location = allChestLocations.get(randomLocation);

        // The the chest location is already in use, continue to find another location.
        if (spawnedChestLocations.contains(location)) return;

        // Make sure the chest is not in a timed out location spot.
        for (ChestTimeOut chestTimeOut : timedOutLocations) {
            if (location.equals(chestTimeOut.getLocation())) return;
        }

        // Add the chest to the spawned location map.
        spawnedChestLocations.add(location);

        // Place the chest in the world.
        location.getBlock().setType(Material.TRAPPED_CHEST);
    }

    @Override
    public void run() {
        // Spawn chests
        int totalChests = maxChestPerPlayerOnline * Bukkit.getOnlinePlayers().size();

        // If not enough loot chests exist in the world, lets spawn some.
        if (totalChests > spawnedChestLocations.size()) {
            // Make sure we only spawn a chest if we have enough locations to do so.
            if (spawnedChestLocations.size() < allChestLocations.size()) {
                spawnChest();
            }
        }

        // Adjust time left for locked out chest locations
        Iterator<ChestTimeOut> iterator = timedOutLocations.iterator();
        while (iterator.hasNext()) {
            ChestTimeOut chestTimeOut = iterator.next();
            chestTimeOut.setTimeLeft(chestTimeOut.getTimeLeft() - 1);

            if (chestTimeOut.getTimeLeft() <= 0) {
                iterator.remove();
            }
        }

        // Show chest particle effects.
        for (Location location : spawnedChestLocations) {
            location.getWorld().playEffect(location, Effect.MOBSPAWNER_FLAMES, 1);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private class ChestTimeOut {
        private final Location location;
        private int timeLeft;
    }
}
