package com.forgestorm.spigotcore.commands;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class SafeStop implements CommandExecutor {

    private final SpigotCore plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            PlayerProfileData profile = plugin.getProfileManager().getProfile(player);

            if (profile.isAdmin()) {
                safeStopCountDown();
            }
        }
        return false;
    }

    /**
     * This is a countdown for players to show them when the server
     * will be going offline.
     */
    private void safeStopCountDown() {
        new BukkitRunnable() {
            int countDown = 30;

            @Override
            public void run() {

                if (countDown == 30) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER, 1f, 1f);
                        player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER, 1f, .4f);
                        player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER, 1f, .6f);
                        player.playSound(player.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER, 1f, .8f);
                        player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "THE SERVER IS SHUTTING DOWN!");
                    }
                } else if (countDown <= 0) {
                    Bukkit.shutdown();
                } else if (countDown == 25 || countDown == 20 || countDown == 15 || countDown == 10) {
                    sendCountDownMessage(ChatColor.YELLOW, countDown);
                } else if (countDown <= 5) {
                    sendCountDownMessage(ChatColor.RED, countDown);
                }

                countDown--;
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    /**
     * Sends a countdown message to the player.
     *
     * @param color The color of the message.
     * @param time  The time to display in the message.
     */
    private void sendCountDownMessage(ChatColor color, int time) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(color + "" + ChatColor.BOLD + "RESTARTING IN " + ChatColor.WHITE +
                    ChatColor.BOLD + time + color + ChatColor.BOLD + " SECONDS!");
        }
    }
}
