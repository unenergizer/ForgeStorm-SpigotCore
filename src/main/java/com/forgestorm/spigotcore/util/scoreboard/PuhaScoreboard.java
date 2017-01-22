package com.forgestorm.spigotcore.util.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;

import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;

public class PuhaScoreboard {
	
	private final SpigotCore plugin;
	private final TitleManagerAPI api;
	
	public PuhaScoreboard(SpigotCore plugin) {
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
	
	public void disable() {
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (hasScoreboard(players)) {
				removeScoreboard(players);
			}
		}
	}
	
	private void updateScoreboard(Player player) {
		//Set Title
		api.setScoreboardTitle(player, "ForgeStorm");
		
		//Set Contents
		api.setScoreboardValue(player, 1, "Test Value" + player.getName());
	}
}
