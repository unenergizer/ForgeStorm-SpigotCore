package com.forgestorm.spigotcore.constants;

import net.md_5.bungee.api.ChatColor;

public enum ItemLores {
	
	//Items:
	ARCADE_TITLE("&eMixed Arcade Games"),
	ARCADE_LORE("&7Join and play several mixed arcade games!"),
	CREATIVE_TITLE("&aCreative Server"),
	CREATIVE_LORE("&7This is the Official Creative Server!"),
	
	//Item Connect Message
	CONNECT_FRAME_1("&a> Click to Connect!"),
	CONNECT_FRAME_2("&a  Click to Connect!"),
	
	//Item Players Online
	PLAYERS_ONLINE("&7%s players online!");
	
	private String message;
	
	//Constructor
	ItemLores(String message) {
		this.message = color(message);
	}
	
	/**
	 * Sends a string representation of the enumerator item.
	 */
	public String toString() {
		return message;
	}
	
	/**
	 * Converts special characters in text into Minecraft client color codes.
	 * <p>
	 * This will give the messages color.
	 * @param msg The message that needs to have its color codes converted.
	 * @return Returns a colored message!
	 */
	public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
