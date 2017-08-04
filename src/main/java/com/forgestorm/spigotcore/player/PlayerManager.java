package com.forgestorm.spigotcore.player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.CommonSounds;
import com.forgestorm.spigotcore.constants.SpigotCoreMessages;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
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

public class PlayerManager implements Listener {

    private final SpigotCore plugin;

    public PlayerManager(SpigotCore plugin) {
        this.plugin = plugin;

        // Register Events
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void onDisable() {
        // Unregister Events
        AsyncPlayerChatEvent.getHandlerList().unregister(this);
        PlayerDropItemEvent.getHandlerList().unregister(this);
        PlayerJoinEvent.getHandlerList().unregister(this);
        PlayerKickEvent.getHandlerList().unregister(this);
        PlayerPickupItemEvent.getHandlerList().unregister(this);
        PlayerRespawnEvent.getHandlerList().unregister(this);
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

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        // If they are in the tutorial and died, tp them back.
        if (plugin.getProfileManager().getProfile(player).isInTutorial()) {

            //TODO: plugin.getTeleports().teleportTutorial(player);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        System.out.println("Compass drop");
        if (event.getItemDrop().getItemStack().getType() == Material.COMPASS) {
            event.getPlayer().sendMessage(ChatColor.RED + "You can not drop your compass.");
            CommonSounds.ACTION_FAILED.playSound(event.getPlayer());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        ItemStack itemStack = event.getItem().getItemStack();

        if (itemStack.getType().equals(Material.EMERALD)) {
            Player player = event.getPlayer();
            PlayerProfileData playerProfileData = plugin.getProfileManager().getProfile(player);
            int amount = itemStack.getAmount();

            // Add the amount to the players profile.
            playerProfileData.addCurrency(amount);

            // Show pickup message
            player.sendMessage(SpigotCoreMessages.ITEM_PICKUP_GEMS.toString().replace("%s", Integer.toString(amount)));
            CommonSounds.ACTION_SUCCESS.playSound(player);

            // Remove the item.
            event.getItem().remove();
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        PlayerProfileData profile = plugin.getProfileManager().getProfile(event.getPlayer());
        ChatColor messageColor = ChatColor.WHITE;

        // Only show messages to players who aren't in a tutorial.
        if (!profile.isInTutorial()) {

            event.setCancelled(true); //Cancel the default message. We will show our own.

            if (profile.getUserGroup() == 0) {
                messageColor = ChatColor.GRAY;
            }   //Free users
            if (profile.getUserGroup() > 0) {
                messageColor = ChatColor.WHITE;
            }   //Paid users
            if (profile.isModerator()) {
                messageColor = ChatColor.WHITE;
            }        //Set moderator color
            if (profile.isAdmin()) {
                messageColor = ChatColor.YELLOW;
            }           //Set administrator color

            //Set message format.
            String sb = profile.getChatPrefix() + ChatColor.DARK_GRAY + ": " + messageColor + event.getMessage();

            sendMessage(sb);
        } else {
            //If the player is in the tutorial, prevent them from talking.
            event.setCancelled(true);
        }
    }

    /**
     * Sends a message to all players who aren't in a Tutorial.
     *
     * @param message The message to broadcast. The message includes the player's name and prefix.
     */
    private void sendMessage(String message) {
        Bukkit.getLogger().info(message); //Save messages in log file
        for (Player players : Bukkit.getOnlinePlayers()) {
            //If the player is in a tutorial, they will not receive the message.
            if (!plugin.getProfileManager().getProfile(players).isInTutorial()) {
                players.sendMessage(message);
            }
        }
    }
}
