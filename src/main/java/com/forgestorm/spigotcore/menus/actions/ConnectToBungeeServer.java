package com.forgestorm.spigotcore.menus.actions;

import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConnectToBungeeServer implements ClickAction {

	private final SpigotCore PLUGIN;
	private final String SERVER;
	
	@Override
	public void click(Player player) {
		PLUGIN.getBungeecord().connectToBungeeServer(player, SERVER);
	}
}
