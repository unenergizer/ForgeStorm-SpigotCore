package com.forgestorm.spigotcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.Messages;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;
import com.forgestorm.spigotcore.redis.SetupNetworkPlayer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerJoin implements Listener {

	private final SpigotCore PLUGIN;
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		PlayerProfileData data = PLUGIN.getProfileManager().loadProfile(player);
		new SetupNetworkPlayer(PLUGIN, player, data).runTaskTimer(PLUGIN, 0, 1);
		PLUGIN.getPlayerManager().addPlayerProfile(player, data);
		
        //Show welcome message.
        event.setJoinMessage("");
        player.sendMessage(Messages.PLAYER_WELCOME.toString().replace("%s", PLUGIN.getDescription().getVersion()));
	}
}
