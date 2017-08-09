package com.forgestorm.spigotcore.menus.actions;

import com.forgestorm.spigotcore.constants.CommonSounds;
import com.forgestorm.spigotcore.util.text.ColorMessage;
import org.bukkit.entity.Player;

public class FeatureComingSoon implements ClickAction {

	@Override
	public void click(Player player) {
		String message = "&cFeature coming soon!!!!";
        player.sendMessage(ColorMessage.color(message));
        CommonSounds.ACTION_FAILED.playSound(player);
	}
}
