package com.forgestorm.spigotcore.commands;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;
import lombok.AllArgsConstructor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class Spawn implements CommandExecutor {

	private final SpigotCore plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			PlayerProfileData profile = plugin.getProfileManager().getProfile(player);
			if (!profile.isInTutorial() && profile.isAdmin()) {
				plugin.getTeleportSpawn().teleportSpawn(player);
			}
		}
		return false;
	}
}
