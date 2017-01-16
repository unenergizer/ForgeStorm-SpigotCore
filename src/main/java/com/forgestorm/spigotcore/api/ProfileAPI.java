package com.forgestorm.spigotcore.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.Messages;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;

public class ProfileAPI {
	
	private final SpigotCore PLUGIN = (SpigotCore) Bukkit.getPluginManager().getPlugin(Messages.DEFAULT_PLUGIN_NAME.toString());
	private Player player;

	public ProfileAPI(Player player) {
		this.player = player;
	}
	
	public PlayerProfileData getProfile() {
		return PLUGIN.getProfileManager().getProfile(player);
	}
}