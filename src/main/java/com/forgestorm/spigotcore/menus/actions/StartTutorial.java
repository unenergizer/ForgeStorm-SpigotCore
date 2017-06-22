package com.forgestorm.spigotcore.menus.actions;

import com.forgestorm.spigotcore.SpigotCore;
import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class StartTutorial implements ClickAction {

    private final SpigotCore plugin;
    private final String tutorial;

    @Override
    public void click(Player player) {
        //Start the tutorial.
        if (plugin.getProfileManager().getProfile(player).isTutorialFinished()) {
            plugin.getAnimatedTutorial().startTutorial(player, tutorial);
        } else {
            player.sendMessage(ChatColor.RED + "You can not start this tutorial until you leave Tutorial Island.");
        }
    }
}
