package com.forgestorm.spigotcore.menus.actions;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class FeatureComingSoon implements ClickAction {

	@Override
	public void click(Player player) {
		player.sendMessage(color("&cFeature coming soon!!!!"));
	}
	
	private String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

}
