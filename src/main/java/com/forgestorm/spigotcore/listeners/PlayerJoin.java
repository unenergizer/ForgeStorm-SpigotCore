package com.forgestorm.spigotcore.listeners;

import com.forgestorm.spigotcore.util.text.CenterChatText;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.Messages;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;
import com.forgestorm.spigotcore.player.SetupNetworkPlayer;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PlayerJoin implements Listener {

	private final SpigotCore plugin;
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		PlayerProfileData data = plugin.getProfileManager().loadProfile(player);
		new SetupNetworkPlayer(plugin, player, data).runTaskTimer(plugin, 0, 1);
		
        //Show welcome message.
        event.setJoinMessage("");
		player.sendMessage("");
        player.sendMessage(CenterChatText.centerChatMessage(Messages.PLAYER_WELCOME_1.toString().replace("%s", plugin.getDescription().getVersion())));
        player.sendMessage("");
        player.sendMessage(CenterChatText.centerChatMessage(Messages.PLAYER_WELCOME_2.toString()));
        player.sendMessage("");
        player.sendMessage(CenterChatText.centerChatMessage(Messages.PLAYER_WELCOME_3.toString()));
        player.sendMessage("");
	}
}
