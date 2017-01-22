package com.forgestorm.spigotcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Realm implements CommandExecutor {
	
	private final SpigotCore plugin;
	
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		//item t1 rare chestplate
		
		Player player = (Player) commandSender;
		
		String title = "";

		for (String arg : args) {
			title = title.concat(arg + " ");
		}
		
		plugin.getRealmManager().setTitle(player, title);
		return true;
	}
}
