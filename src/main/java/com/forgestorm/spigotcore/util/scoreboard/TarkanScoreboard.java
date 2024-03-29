package com.forgestorm.spigotcore.util.scoreboard;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TarkanScoreboard {

    private final SpigotCore plugin;
    private final TitleManagerAPI api;

    public TarkanScoreboard(SpigotCore plugin) {
        this.plugin = plugin;
        api = this.plugin.getTitleManagerAPI();
    }

    public void giveScoreboard(Player player) {
        api.giveScoreboard(player);
        updateScoreboard(player);
    }

    public boolean hasScoreboard(Player player) {
        return api.hasScoreboard(player);
    }

    public void removeScoreboard(Player player) {
        api.removeScoreboard(player);
    }

    public void onDisable() {
        for (Player players : Bukkit.getOnlinePlayers()) {
            if (hasScoreboard(players)) {
                removeScoreboard(players);
            }
        }
    }

    public void updateScoreboard(Player player) {
        PlayerProfileData profileData = plugin.getProfileManager().getProfile(player);

        //Set Title
        api.setScoreboardTitle(player, ChatColor.YELLOW + "" + ChatColor.BOLD + "FORGESTORM");

        //Set Contents
        api.setScoreboardValue(player, 1, ChatColor.GREEN + " ");
        api.setScoreboardValue(player, 2, ChatColor.LIGHT_PURPLE + "XP: " + ChatColor.RESET + profileData.getExpPercent() + "%");
        api.setScoreboardValue(player, 3, ChatColor.AQUA + "Level: " + ChatColor.RESET + profileData.getPlayerLevel());
        api.setScoreboardValue(player, 4, ChatColor.GREEN + "  ");
        api.setScoreboardValue(player, 5, ChatColor.GREEN + "Gems: " + ChatColor.RESET + profileData.getCurrency());
        api.setScoreboardValue(player, 6, ChatColor.GREEN + "eCash: " + ChatColor.RESET + profileData.getPremiumCurrency());
    }
}
