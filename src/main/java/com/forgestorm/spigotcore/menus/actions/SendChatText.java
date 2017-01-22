package com.forgestorm.spigotcore.menus.actions;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class SendChatText implements ClickAction {

	private final String[] text;

	public SendChatText(String... text) {
		this.text = text;
	}
	
	@Override
	public void click(Player player) {
		for (String message : text) {
			player.sendMessage(color(message));
		}
	}
	
	private String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}
