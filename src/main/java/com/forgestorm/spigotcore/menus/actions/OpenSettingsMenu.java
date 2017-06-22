package com.forgestorm.spigotcore.menus.actions;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.menus.SettingsMenu;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class OpenSettingsMenu implements ClickAction {

    private final SpigotCore plugin;

    @Override
    public void click(Player player) {
        new SettingsMenu(plugin, player).open(player);
    }
}
