package com.forgestorm.spigotcore.util.player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class ModActions {

    private final SpigotCore plugin;

    public void kickAllPlayers(String modName) {
        for (Player players : Bukkit.getOnlinePlayers()) {

            PlayerProfileData profile = plugin.getProfileManager().getProfile(players);

            if (!profile.isAdmin() || !profile.isModerator()) {
                players.kickPlayer(modName + " has kicked all players.");
            }
        }
    }
}
