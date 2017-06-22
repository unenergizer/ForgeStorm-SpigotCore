package com.forgestorm.spigotcore.menus.actions;

import com.forgestorm.spigotcore.SpigotCore;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class Exit implements ClickAction {

    private final SpigotCore plugin;

    @Override
    public void click(Player player) {
        player.closeInventory();
        plugin.getProfileManager().getProfile(player).setCurrentMenu(null);
    }
}
