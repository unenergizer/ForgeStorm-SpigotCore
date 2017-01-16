package com.forgestorm.spigotcore.util.item;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class PlayerSkullBuilder {
	
	private ItemStack playerSkull;
	
	
	public ItemStack createPlayerSkullItem(String playerName) {
		ArrayList<String> playerInfo = new ArrayList<String>();

		playerInfo.add(ChatColor.GRAY + "Various information about");
		playerInfo.add(ChatColor.GRAY + " your character.");
		playerInfo.add("");
		playerInfo.add(ChatColor.GRAY + "Faction: " + ChatColor.RED + "null");
		playerInfo.add(ChatColor.GRAY + "Level: " + ChatColor.RED + "null");
		playerInfo.add(ChatColor.GRAY + "Guild: " + ChatColor.RED + "null");
		playerInfo.add("");
		playerInfo.add(ChatColor.GRAY + "Money: " + ChatColor.RED + "null");
		playerInfo.add(ChatColor.GRAY + "eCash: " + ChatColor.RED + "null");

		playerSkull = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
		SkullMeta skullMeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
		skullMeta.setOwner(playerName);
		skullMeta.setDisplayName(ChatColor.AQUA + "Player Info");
		skullMeta.setLore(playerInfo);
		playerSkull.setItemMeta(skullMeta);
		
		//Overwrite plain menu item
		return playerSkull;
	}
}
