package com.forgestorm.spigotcore.player;

import com.forgestorm.spigotcore.SpigotCore;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class PlayerBossBar {
	
	private final SpigotCore plugin;
	private Player player;
	
	void addPlayerBar() {
		plugin.getBossBarAnnouncer().getBar().showBossBar(player);
	}
	
	public void removePlayerBar() {
		//Remove BossBar.
		plugin.getBossBarAnnouncer().getBar().removeBossBar(player);
	}
}
