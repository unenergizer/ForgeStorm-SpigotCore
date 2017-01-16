package com.forgestorm.spigotcore.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AsyncPlayerChat implements Listener {

	private final SpigotCore PLUGIN;

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		PlayerProfileData profile = PLUGIN.getProfileManager().getProfile(player);
		//String prefix = ChatColor.RESET + "";
		ChatColor messageColor = ChatColor.WHITE;
		boolean isInTutorial = profile.isInTutorial();
		
		//
		if (!isInTutorial) {
			//Cancel the default message. We will show our own.
			event.setCancelled(true);
			
			//Free users will have gray text.
			if (profile.getUserGroup() == 0 && !profile.isAdmin() && !profile.isModerator()) {
				messageColor = ChatColor.GRAY;
			}
			
			//Set message format.
			StringBuilder sb = new StringBuilder();
			sb.append(player.getCustomName());
			sb.append(ChatColor.DARK_GRAY + ": ");
			sb.append(messageColor + event.getMessage());
			
			sendMessage(sb.toString());
		} else {
			//If the player is in the tutorial, prevent them from talking.
			event.setCancelled(true);
		}
	}
	
	private void sendMessage(String message) {
		for (Player players : Bukkit.getOnlinePlayers()) {
			//If the player is in a tutorial, they will not receive the message.
			if (!PLUGIN.getProfileManager().getProfile(players).isInTutorial()) {	
				players.sendMessage(message);
			}
		}
	}
}
