package com.forgestorm.spigotcore.listeners;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@AllArgsConstructor
public class AsyncPlayerChat implements Listener {

	private final SpigotCore plugin;

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
