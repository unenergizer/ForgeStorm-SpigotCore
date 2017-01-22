package com.forgestorm.spigotcore.util.item;

import com.forgestorm.spigotcore.constants.CitizenType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;

public class NPCSkullBuilder {

	public ItemStack createPlayerSkullItem(String npcName, CitizenType type) {
		ArrayList<String> npcInfo = new ArrayList<>();

		npcInfo.add(color("&7Type: &c" + type.toString().toLowerCase()));
		npcInfo.add("");
		npcInfo.add(color("&aClick to track this NPC."));

		ItemStack playerSkull = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
		SkullMeta skullMeta = (SkullMeta) Bukkit.getItemFactory().getItemMeta(Material.SKULL_ITEM);
		//skullMeta.setOwner(npcName);
		skullMeta.setDisplayName(ChatColor.AQUA + npcName);
		skullMeta.setLore(npcInfo);
		playerSkull.setItemMeta(skullMeta);
		
		//Overwrite plain menu item
		return playerSkull;
	}
	
	private String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
