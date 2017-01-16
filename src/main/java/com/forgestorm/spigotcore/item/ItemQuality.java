package com.forgestorm.spigotcore.item;

import org.bukkit.ChatColor;

public enum ItemQuality {

	COMMON		("Common", 		1, 	ChatColor.WHITE),
	UNCOMMON	("Uncommon", 	1.2,ChatColor.GREEN),
	RARE		("Rare", 		1.5,ChatColor.BLUE),
	EPIC		("Epic",		1.8,ChatColor.DARK_PURPLE),
	LEGENDARY	("Legendary", 	2,	ChatColor.GOLD);

	private String name;
	private double multiplier;
	private ChatColor color;

	//Constructor
	ItemQuality(String name, double multiplier, ChatColor color) {
		this.name = name;
		this.multiplier = multiplier;
		this.color = color;
	}
	
	public double getMultiplier() {
		return multiplier;
	}
	
	public String colorToString() {
		return color.toString();
	}
	
	public String nameToString() {
		return color + name.toString();
	}
}
