package com.forgestorm.spigotcore.constants;

import org.bukkit.ChatColor;

public enum CitizenType {

	AUCTIONEER("Auctioneer"),
	BANKER("Banker"),
	BARTENDER("Bartender"),
	DIRTY_HOBO("Dirty Old Hobo"),
	MERCHANT("Item Merchant"),
	MERCHANT_BOAT("Boat Merchant"),
	NONE(""),
	PLAY_MINIGAMES("Play Minigames!"),
	PROFESSION_FARMING("Farming Trainer"),
	PROFESSION_FISHING("Fishing Trainer"),
	PROFESSION_LUMBERJACK("Lumberjack Trainer"),
	PROFESSION_MINING("Mining Trainer"),
	SOCIAL_MEDIA("Social Media"),
	TEAMSPEAK("TeamSpeak"),
	TUTORIAL("Server Tutorial"),
	VOTE("Daily Rewards");

	private final String name;

	CitizenType(String s) {
		this.name = s;
	}

	public String getName() {
		return ChatColor.YELLOW + "" + ChatColor.BOLD + name;
	}
	
	public String getName(ChatColor color) {
		return color + "" + ChatColor.BOLD + name;
	}
}
