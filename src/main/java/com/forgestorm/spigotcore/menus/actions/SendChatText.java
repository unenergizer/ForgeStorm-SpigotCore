package com.forgestorm.spigotcore.menus.actions;

import com.forgestorm.spigotcore.util.text.ColorMessage;
import org.bukkit.entity.Player;

public class SendChatText implements ClickAction {

	private final String[] text;

	public SendChatText(String... text) {
		this.text = text;
	}
	
	@Override
	public void click(Player player) {
		for (String message : text) {
            player.sendMessage(ColorMessage.color(message));
        }
	}
}
