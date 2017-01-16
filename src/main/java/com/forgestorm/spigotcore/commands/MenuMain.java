package com.forgestorm.spigotcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.menus.MainMenu;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MenuMain implements CommandExecutor {

	private final SpigotCore PLUGIN; 

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			new MainMenu(PLUGIN).open(player);
		}
		return false;
	}
}
