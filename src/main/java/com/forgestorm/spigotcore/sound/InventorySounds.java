package com.forgestorm.spigotcore.sound;

import com.forgestorm.spigotcore.SpigotCore;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

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

public class InventorySounds implements Listener {

    public final SpigotCore plugin;

    public InventorySounds(SpigotCore plugin) {
        this.plugin = plugin;

        // Register Listeners
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void onDisable() {
        // Unregister Listeners
        InventoryClickEvent.getHandlerList().unregister(this);
        PlayerItemHeldEvent.getHandlerList().unregister(this);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {

            Player player = (Player) event.getWhoClicked();
            InventoryType.SlotType slotType = event.getSlotType();

            //Check armor slots.
            if (slotType.equals(InventoryType.SlotType.ARMOR)) {
                //Armor was moved into our out of this slot.
                //Lets update the players attributes.
                playArmorEquipSound(player, event.getCurrentItem().getType());
            }

            //Check the armor slot if the player has shift clicked.
            //This will make check if the player has shift clicked
            //armor into the armor slot.
            if (event.isShiftClick()) {
                playArmorEquipSound(player, event.getCurrentItem().getType());
            }
        }
    }

    //It seems that the client responds better if we give it time to
    //set the armor, and then read the contents of the armor slots.
    private void playArmorEquipSound(final Player player, final Material material) {
        //Play Sound
        if (material.equals(Material.LEATHER_BOOTS) ||
                material.equals(Material.LEATHER_CHESTPLATE) ||
                material.equals(Material.LEATHER_HELMET) ||
                material.equals(Material.LEATHER_LEGGINGS)) {
            player.playSound(player.getEyeLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 1, 1);
        }
        if (material.equals(Material.CHAINMAIL_BOOTS) ||
                material.equals(Material.CHAINMAIL_CHESTPLATE) ||
                material.equals(Material.CHAINMAIL_HELMET) ||
                material.equals(Material.CHAINMAIL_LEGGINGS)) {
            player.playSound(player.getEyeLocation(), Sound.ITEM_ARMOR_EQUIP_CHAIN, 1, 1);
        }
        if (material.equals(Material.IRON_BOOTS) ||
                material.equals(Material.IRON_CHESTPLATE) ||
                material.equals(Material.IRON_HELMET) ||
                material.equals(Material.IRON_LEGGINGS)) {
            player.playSound(player.getEyeLocation(), Sound.ITEM_ARMOR_EQUIP_IRON, 1, 1);
        }
        if (material.equals(Material.DIAMOND_BOOTS) ||
                material.equals(Material.DIAMOND_CHESTPLATE) ||
                material.equals(Material.DIAMOND_HELMET) ||
                material.equals(Material.DIAMOND_LEGGINGS)) {
            player.playSound(player.getEyeLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1, 1);
        }
        if (material.equals(Material.GOLD_BOOTS) ||
                material.equals(Material.GOLD_CHESTPLATE) ||
                material.equals(Material.GOLD_HELMET) ||
                material.equals(Material.GOLD_LEGGINGS)) {
            player.playSound(player.getEyeLocation(), Sound.ITEM_ARMOR_EQUIP_DIAMOND, 1, 1);
        }
    }

    @EventHandler
    public void onPlayerSwitchItem(PlayerItemHeldEvent event) {
        final Player player = event.getPlayer();

        // Play sounds for certain item equipping
        if (player.getInventory().getItem(event.getNewSlot()) != null) {
            ItemStack itemStack = player.getInventory().getItem(event.getNewSlot());

            if (itemStack.getType() == Material.BOW) {

                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.4F);
            }

            if (itemStack.getType() == Material.WOOD_SWORD || itemStack.getType() == Material.STONE_SWORD || itemStack.getType() == Material.IRON_SWORD
                    || itemStack.getType() == Material.DIAMOND_SWORD || itemStack.getType() == Material.GOLD_SWORD) {

                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.4F);
            }

            if (itemStack.getType() == Material.WOOD_AXE || itemStack.getType() == Material.STONE_AXE
                    || itemStack.getType() == Material.IRON_AXE || itemStack.getType() == Material.DIAMOND_AXE
                    || itemStack.getType() == Material.GOLD_AXE) {

                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.4F);
            }

            if (itemStack.getType() == Material.WOOD_SPADE || itemStack.getType() == Material.STONE_SPADE
                    || itemStack.getType() == Material.IRON_SPADE || itemStack.getType() == Material.DIAMOND_SPADE
                    || itemStack.getType() == Material.GOLD_SPADE) {

                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.4F);
            }

            if (itemStack.getType() == Material.WOOD_HOE || itemStack.getType() == Material.STONE_HOE
                    || itemStack.getType() == Material.IRON_HOE || itemStack.getType() == Material.DIAMOND_HOE
                    || itemStack.getType() == Material.GOLD_HOE) {

                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.4F);
            }

            if (itemStack.getType() == Material.WOOD_PICKAXE || itemStack.getType() == Material.STONE_PICKAXE
                    || itemStack.getType() == Material.IRON_PICKAXE || itemStack.getType() == Material.DIAMOND_PICKAXE
                    || itemStack.getType() == Material.GOLD_PICKAXE) {

                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.4F);
            }
        }
    }
}
