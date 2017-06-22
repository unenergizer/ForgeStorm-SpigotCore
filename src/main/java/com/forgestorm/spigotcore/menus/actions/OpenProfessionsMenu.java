package com.forgestorm.spigotcore.menus.actions;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.menus.profession.ProfessionMenu;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class OpenProfessionsMenu implements ClickAction {

    private final SpigotCore plugin;

    @Override
    public void click(Player player) {
        new ProfessionMenu(plugin, player).open(player);
    }
}
