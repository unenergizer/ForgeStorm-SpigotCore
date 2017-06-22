package com.forgestorm.spigotcore.commands;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import com.forgestorm.spigotcore.util.math.TimeUnit;
import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class PlayTime implements CommandExecutor {

	private final SpigotCore plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		
		Player player = (Player) sender;
		PlayerProfileData profile = plugin.getProfileManager().getProfile(player);
		long currentTime = System.currentTimeMillis() / 1000;
		long totalTime = (currentTime - profile.getLoginTime()) + profile.getPlayTime();
		long levelTime = (currentTime - profile.getLastLevelTime()) + profile.getLevelTime();

		//System.out.println("Last Saved Play Time: " + commandTime);
		//System.out.println("Login Time: " + profile.getLoginTime());
		//System.out.println("Current Time: " + commandTime);
		//System.out.println("Final Online Time: " + currentTime);

		player.sendMessage("");
		player.sendMessage(ChatColor.GREEN + "Total time played" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + TimeUnit.toString(totalTime) + ChatColor.DARK_GRAY + ".");
		player.sendMessage(ChatColor.GREEN + "Time played this level" + ChatColor.DARK_GRAY + ": "  + ChatColor.YELLOW + TimeUnit.toString(levelTime) + ChatColor.DARK_GRAY + ".");
		return true;
	}
}
