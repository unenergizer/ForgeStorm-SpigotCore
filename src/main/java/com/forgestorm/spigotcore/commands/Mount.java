package com.forgestorm.spigotcore.commands;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.entity.MountTimer;
import com.forgestorm.spigotcore.entity.mount.EntityMountDonkey;
import com.forgestorm.spigotcore.entity.mount.EntityMountHorse;
import com.forgestorm.spigotcore.entity.mount.EntityMountMule;
import com.forgestorm.spigotcore.entity.mount.EntityMountPig;
import com.forgestorm.spigotcore.entity.mount.EntityMountSkeletonHorse;
import com.forgestorm.spigotcore.entity.mount.EntityMountZombieHorse;
import com.forgestorm.spigotcore.entity.mount.MountEntity;
import com.forgestorm.spigotcore.profile.monster.MonsterProfileData;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class Mount implements CommandExecutor {

	private final SpigotCore plugin;

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		//item t1 rare chestplate
		
		Player player = (Player) commandSender;
		PlayerProfileData playerProfile = plugin.getPlayerManager().getPlayerProfile(player);
		
		if (playerProfile.isInCombat()) {
			player.sendMessage(ChatColor.GOLD + "Cannot summon mount while in combat!");
		} else if (args.length == 1 && !playerProfile.isInCombat()) {
			MonsterProfileData monsterProfile = new MonsterProfileData();
			
			switch (args[0].toLowerCase()) {
			case "donkey":
				MountEntity donkey = new EntityMountDonkey(ChatColor.GREEN + player.getName() + " mount", 100 , player.getLocation(), monsterProfile);
				new MountTimer(plugin, player, donkey).runTaskTimer(plugin, 0, 20);
				break;
			case "horse":
				MountEntity horse = new EntityMountHorse(ChatColor.GREEN + player.getName() + " mount", 100, player.getLocation(), monsterProfile);
				new MountTimer(plugin, player, horse).runTaskTimer(plugin, 0, 20);
				break;
			case "mule":
				MountEntity mule = new EntityMountMule(ChatColor.GREEN + player.getName() + " mount", 100, player.getLocation(), monsterProfile);
				new MountTimer(plugin, player, mule).runTaskTimer(plugin, 0, 20);
				break;
			case "pig":
				MountEntity pig = new EntityMountPig(ChatColor.GREEN + player.getName() + " mount", 100, player.getLocation(), monsterProfile);
				new MountTimer(plugin, player, pig).runTaskTimer(plugin, 0, 20);
				break;
			case "skeletonhorse":
				MountEntity skeletonHorse = new EntityMountSkeletonHorse(ChatColor.GREEN + player.getName() + " mount", 100, player.getLocation(), monsterProfile);
				new MountTimer(plugin, player, skeletonHorse).runTaskTimer(plugin, 0, 20);
				break;
			case "zombiehorse":
				MountEntity zombieHorse = new EntityMountZombieHorse(ChatColor.GREEN + player.getName() + " mount", 100, player.getLocation(), monsterProfile);
				new MountTimer(plugin, player, zombieHorse).runTaskTimer(plugin, 0, 20);
				break;
			default:
				break;
			} 
		}
		return false;
	}
}
