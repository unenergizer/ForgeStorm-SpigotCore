package com.forgestorm.spigotcore.menus.actions;

import com.forgestorm.spigotcore.constants.CommonSounds;
import com.forgestorm.spigotcore.constants.SpigotCoreMessages;
import org.bukkit.entity.Player;

public class ShowTrackingDeviceMessage implements ClickAction {

    @Override
    public void click(Player player) {
        player.sendMessage("");
        player.sendMessage(SpigotCoreMessages.HELP_PROFESSION_TRACKING_01.toString());
        player.sendMessage(SpigotCoreMessages.HELP_PROFESSION_TRACKING_02.toString());
        player.sendMessage(SpigotCoreMessages.HELP_PROFESSION_TRACKING_03.toString());
        player.sendMessage(SpigotCoreMessages.HELP_PROFESSION_TRACKING_04.toString());
        player.sendMessage(SpigotCoreMessages.HELP_PROFESSION_TRACKING_05.toString());
        player.sendMessage(SpigotCoreMessages.HELP_PROFESSION_TRACKING_06.toString());
        CommonSounds.ACTION_FAILED.playSound(player);
    }
}
