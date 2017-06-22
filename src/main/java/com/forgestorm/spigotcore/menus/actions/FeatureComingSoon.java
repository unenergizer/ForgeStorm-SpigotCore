package com.forgestorm.spigotcore.menus.actions;

import com.forgestorm.spigotcore.constants.CommonSounds;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class FeatureComingSoon implements ClickAction {

	@Override
	public void click(Player player) {
		String message = "&cFeature coming soon!!!!";
		player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
		CommonSounds.ACTION_FAILED.playSound(player);
	}
}
