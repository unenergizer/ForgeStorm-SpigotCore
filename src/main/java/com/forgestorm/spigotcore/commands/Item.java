package com.forgestorm.spigotcore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.item.ItemQuality;
import com.forgestorm.spigotcore.item.armor.T1Boots;
import com.forgestorm.spigotcore.item.armor.T1Chestplate;
import com.forgestorm.spigotcore.item.armor.T1Helmet;
import com.forgestorm.spigotcore.item.armor.T1Leggings;
import com.forgestorm.spigotcore.item.armor.T2Boots;
import com.forgestorm.spigotcore.item.armor.T2Chestplate;
import com.forgestorm.spigotcore.item.armor.T2Helmet;
import com.forgestorm.spigotcore.item.armor.T2Leggings;
import com.forgestorm.spigotcore.item.armor.T3Boots;
import com.forgestorm.spigotcore.item.armor.T3Chestplate;
import com.forgestorm.spigotcore.item.armor.T3Helmet;
import com.forgestorm.spigotcore.item.armor.T3Leggings;
import com.forgestorm.spigotcore.item.armor.T4Boots;
import com.forgestorm.spigotcore.item.armor.T4Chestplate;
import com.forgestorm.spigotcore.item.armor.T4Helmet;
import com.forgestorm.spigotcore.item.armor.T4Leggings;
import com.forgestorm.spigotcore.item.armor.T5Boots;
import com.forgestorm.spigotcore.item.armor.T5Chestplate;
import com.forgestorm.spigotcore.item.armor.T5Helmet;
import com.forgestorm.spigotcore.item.armor.T5Leggings;
import com.forgestorm.spigotcore.item.weapon.T1Sword;
import com.forgestorm.spigotcore.item.weapon.T2Sword;
import com.forgestorm.spigotcore.item.weapon.T3Sword;
import com.forgestorm.spigotcore.item.weapon.T4Sword;
import com.forgestorm.spigotcore.item.weapon.T5Sword;

public class Item implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
		
		//item t1 rare chestplate
		
		Player player = (Player) commandSender;
		
		if (args.length == 1) {
			switch (args[0].toLowerCase()) {
			case "t1":
				new T1Helmet(ItemQuality.COMMON).givePlayerItem(player);
				new T1Chestplate(ItemQuality.COMMON).givePlayerItem(player);
				new T1Leggings(ItemQuality.COMMON).givePlayerItem(player);
				new T1Boots(ItemQuality.COMMON).givePlayerItem(player);
				new T1Sword(ItemQuality.COMMON).givePlayerItem(player);
				break;
				
			case "t2":
				new T2Helmet(ItemQuality.COMMON).givePlayerItem(player);
				new T2Chestplate(ItemQuality.COMMON).givePlayerItem(player);
				new T2Leggings(ItemQuality.COMMON).givePlayerItem(player);
				new T2Boots(ItemQuality.COMMON).givePlayerItem(player);
				new T2Sword(ItemQuality.COMMON).givePlayerItem(player);
				break;
				
			case "t3":
				new T3Helmet(ItemQuality.COMMON).givePlayerItem(player);
				new T3Chestplate(ItemQuality.COMMON).givePlayerItem(player);
				new T3Leggings(ItemQuality.COMMON).givePlayerItem(player);
				new T3Boots(ItemQuality.COMMON).givePlayerItem(player);
				new T3Sword(ItemQuality.COMMON).givePlayerItem(player);				
				break;
				
			case "t4":
				new T4Helmet(ItemQuality.COMMON).givePlayerItem(player);
				new T4Chestplate(ItemQuality.COMMON).givePlayerItem(player);
				new T4Leggings(ItemQuality.COMMON).givePlayerItem(player);
				new T4Boots(ItemQuality.COMMON).givePlayerItem(player);
				new T4Sword(ItemQuality.COMMON).givePlayerItem(player);				
				break;
				
			case "t5":
				new T5Helmet(ItemQuality.COMMON).givePlayerItem(player);
				new T5Chestplate(ItemQuality.COMMON).givePlayerItem(player);
				new T5Leggings(ItemQuality.COMMON).givePlayerItem(player);
				new T5Boots(ItemQuality.COMMON).givePlayerItem(player);
				new T5Sword(ItemQuality.COMMON).givePlayerItem(player);				
				break;
				
			default:
				break;
			} 
		}
		return false;
	}
}
