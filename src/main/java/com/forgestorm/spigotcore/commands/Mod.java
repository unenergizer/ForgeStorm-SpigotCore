package com.forgestorm.spigotcore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.Messages;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;
import com.forgestorm.spigotcore.util.player.ModActions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Mod implements CommandExecutor {

	private final SpigotCore plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		//Check if the command sender is a server Operator
		if (sender instanceof Player) {

			Player commandSender = (Player) sender;
			PlayerProfileData commandSenderProfile = plugin.getProfileManager().getProfile(commandSender);

			if (!commandSenderProfile.isModerator() || !commandSenderProfile.isAdmin()) {
				sender.sendMessage(Messages.NO_PERMISSION.toString());
				return false;
			}

			if (args.length == 1) {
				switch (args[0].toLowerCase()) {
				case "kickall":
					new ModActions(plugin).kickAllPlayers(sender.getName());
					break;
				case "status":
					//TODO: Later
					break;

				default:
					//TODO: Send message
					break;
				} 
			} else if (args.length == 2) {

				if (Bukkit.getPlayer(args[1]) == null) {
					commandSender.sendMessage(Messages.COMMAND_ADMIN_PLAYER_NOT_ONLINE.toString());
					return false;
				}

				Player player = Bukkit.getPlayer(args[1]);
				PlayerProfileData profile = plugin.getProfileManager().getProfile(player);

				switch (args[0].toLowerCase()){
				case "ban":
					profile.setBanned(true);
					break;
				case "kick":
					player.kickPlayer(commandSender.getName() + " kicked you from the server.");
					break;
				case "mute":
					//TODO: Include in database
					break;
				case "spectate":
					commandSender.setSpectatorTarget(player);
					break;
				case "warn":
					profile.setWarningPoints(profile.getWarningPoints() + 1);
					break;

				default:
					//TODO: Send message
					break;
				}

			} else if (args.length == 3) {

				if (Bukkit.getPlayer(args[2]) == null){
					commandSender.sendMessage(Messages.COMMAND_ADMIN_PLAYER_NOT_ONLINE.toString());
					return false;
				}

				Player player = Bukkit.getPlayer(args[2]);
				PlayerProfileData profile = plugin.getProfileManager().getProfile(player);

				switch (args[0].toLowerCase()){
				case "get":
					switch (args[1].toLowerCase()){
					case "bans":
						//TODO
						break;
					case "warningpoint":
						commandSender.sendMessage(player.getName() + " has " + Integer.toString(profile.getWarningPoints()) + " warning points.");
						break;
					default:
						break;
					}
					break;
				default:
					break;
				}
			} else if (args.length > 2) {
				
				if (Bukkit.getPlayer(args[1]) == null) {
					commandSender.sendMessage(Messages.COMMAND_ADMIN_PLAYER_NOT_ONLINE.toString());
					return false;
				}

				Player player = Bukkit.getPlayer(args[1]);
				PlayerProfileData profile = plugin.getProfileManager().getProfile(player);
				
				switch (args[0].toLowerCase()) {
				case "ban":
					//Lets ban a player and enter a ban reason.
					
					//Ban Player
					profile.setBanned(true);
					
					//Get Ban reason.
					String banMessage = "";
					for (int i = 2; i < args.length; i++) {
						banMessage = banMessage.concat(args[i]);
						
						//Add a space if we need one.
						if (i != args.length - 1) {
							banMessage = banMessage.concat(" ");
						}
					}
					
					//TODO: Set who was banned, the mod banner, and the reason into the database.
					
					break;
				default:
					break;
				}
			}

		}
		return false;
	}
}	