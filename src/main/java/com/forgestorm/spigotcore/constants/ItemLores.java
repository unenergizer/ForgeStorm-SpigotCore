package com.forgestorm.spigotcore.constants;

import com.forgestorm.spigotcore.util.text.ColorMessage;

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
	
	private final String message;
	
	//Constructor
	ItemLores(String message) {
        this.message = ColorMessage.color(message);
    }
	
	/**
	 * Sends a string representation of the enumerator item.
	 */
	public String toString() {
		return message;
	}
}
