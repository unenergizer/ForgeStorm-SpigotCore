package com.forgestorm.spigotcore.commands;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.SpigotCoreMessages;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class WorldAnimate implements CommandExecutor {
	
	private final SpigotCore plugin;
	
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

		//Check if the command sender is a server Operator
		if (!(commandSender instanceof Player) || !commandSender.isOp()) {
			commandSender.sendMessage(SpigotCoreMessages.NO_PERMISSION.toString());
			return false;
		}

		Player player = (Player) commandSender;
		
		//Check command args
		if (args.length == 1) {	
			switch (args[0].toLowerCase()) {
			case "pause":				
				plugin.getServer().dispatchCommand(player, "gamerule doDaylightCycle false");
				player.sendMessage(ChatColor.RED + "The day/night cycle has been paused.");
				break;
				
			case "resume":
				plugin.getServer().dispatchCommand(player, "gamerule doDaylightCycle true");
				player.sendMessage(ChatColor.GREEN + "The day/night cycle has been resumed.");
				break;
				
			case "time":
				String time = Long.toString(Bukkit.getWorlds().get(0).getTime());
				player.sendMessage(ChatColor.YELLOW + "Time" + ChatColor.DARK_GRAY + ": " + ChatColor.WHITE + time);
				break;
			default:
				//unknown command
				player.sendMessage(ChatColor.RED + "Unknown command.");
				break;
			}		
		} else {
			//UNKNOWN OR INCOMPLETE COMMANDS
			player.sendMessage(ChatColor.RED + "Check your arguments.");
		}
		return false;
	}
}
