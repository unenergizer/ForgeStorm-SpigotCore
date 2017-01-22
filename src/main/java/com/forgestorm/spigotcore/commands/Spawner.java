package com.forgestorm.spigotcore.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.Messages;
import com.forgestorm.spigotcore.entity.EntitySpawnerManager;
import com.forgestorm.spigotcore.entity.spawner.SaveEntitySpawner;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Spawner implements CommandExecutor {

	private final SpigotCore plugin;
	
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		//spawner save spawnRadius runRadius maxMobCount respawnTime
		Player player = (Player) commandSender;
		
		if (args.length == 2) {
			switch(args[0].toLowerCase()) {
			case "tp":
			case "teleport":
				EntitySpawnerManager msm = plugin.getEntitySpawnerManager();
				int size = plugin.getEntitySpawnerManager().getSpawners().size();
				int id = Integer.parseInt(args[1]);
				
				if (id < size && id >= 0) {
					Location location = msm.getSpawnerData(id).getLocation();
					player.teleport(location);
					player.sendMessage(Messages.SPAWNER_TP_SUCCESS.toString().replace("%s", args[1]));
				} else {
					player.sendMessage(Messages.SPAWNER_TP_FAIL.toString().replace("%s", Integer.toString(size - 1)));
				}
				break;
			}
		}
		
		if (args.length == 5) {
			int spawnRadius = Integer.parseInt(args[1]);
			int runRadius = Integer.parseInt(args[2]);
			int maxMobCount = Integer.parseInt(args[3]);
			int respawnTime = Integer.parseInt(args[4]);
			
			new SaveEntitySpawner().entitySaver(player.getLocation(), spawnRadius, runRadius, maxMobCount, respawnTime);
		}
		return false;
	}
}