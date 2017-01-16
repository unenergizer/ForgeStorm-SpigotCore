package com.forgestorm.spigotcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;

import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;

@AllArgsConstructor
public class Money implements CommandExecutor {

	private final SpigotCore PLUGIN; 

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			String money = Integer.toString(PLUGIN.getProfileManager().getProfile((Player) sender).getCurrency());
			String pmoney = Integer.toString(PLUGIN.getProfileManager().getProfile((Player) sender).getPremiumCurrency());
			sender.sendMessage(ChatColor.GREEN + "Money: " + ChatColor.YELLOW + money + ChatColor.GREEN + " Premium: " + ChatColor.YELLOW + pmoney);
		}
		return false;
	}
}
