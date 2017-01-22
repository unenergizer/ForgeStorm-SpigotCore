package com.forgestorm.spigotcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.profile.monster.MonsterLoader;
import com.forgestorm.spigotcore.profile.monster.MonsterSaver;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Monster implements CommandExecutor {

	private final SpigotCore plugin;
	
	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		//monster save monsterType name level health
		
		Player player = (Player) commandSender;
		
		if (args.length == 5) {
			new MonsterSaver().entitySaver(args[1], args[2], Integer.parseInt(args[3]), Integer.parseInt(args[4]), "owner", "loot");
			new MonsterLoader(plugin).getConfig(args[2], player.getLocation());
		}
		return false;
	}
}