package com.forgestorm.spigotcore.util.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ModActions {
	
	private final SpigotCore PLUGIN;
	
	public void kickAllPlayers(String modName){
		for (Player players : Bukkit.getOnlinePlayers()){
			
			PlayerProfileData profile = PLUGIN.getProfileManager().getProfile(players);
			
			if (!profile.isAdmin() || !profile.isModerator()) {
				players.kickPlayer(modName + " has kicked all players.");
			}
		}
	}
}
