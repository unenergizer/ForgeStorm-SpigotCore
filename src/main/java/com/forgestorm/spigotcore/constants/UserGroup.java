package com.forgestorm.spigotcore.constants;

import org.bukkit.ChatColor;

public enum UserGroup {
    // ENUM -> Scoreboard Team Name -> Text next to players name
    USER_PREFIX_USER_GROUP_NEW("newPlayer", "&7[&aNew&7] "), //New user &7[&aNew Player&7]
    USER_PREFIX_USER_GROUP_0("free", ""),//Free user
    USER_PREFIX_USER_GROUP_1("paid1", "&aVIP "),
    USER_PREFIX_USER_GROUP_2("paid2", "&aVIP+ "),
    USER_PREFIX_USER_GROUP_3("paid3", "&bMVP "),
    USER_PREFIX_USER_GROUP_4("paid4", "&bMVP+ "),
    USER_PREFIX_MODERATOR("mod", "&9&lMOD "),
    USER_PREFIX_ADMINISTRATOR("admin", "&c&lADMIN "),
    USER_PREFIX_NPC("npc", "&7[&9NPC&7] ");

	private final String teamName;
	private final String prefix;
	
	//Constructor
	UserGroup(String teamName, String prefix) {
		this.teamName = teamName;
		this.prefix = color(prefix);
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

    public String getTeamName() {
        return teamName;
    }

    public String getUserGroupPrefix() {
        return prefix + ChatColor.RESET;
    }
}
