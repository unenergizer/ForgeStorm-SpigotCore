package com.forgestorm.spigotcore.menus.actions;

import com.forgestorm.spigotcore.SpigotCore;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class ConnectToBungeeServer implements ClickAction {

    private final SpigotCore plugin;
    private final String server;

    @Override
    public void click(Player player) {
        plugin.getBungeecord().connectToBungeeServer(player, server);
    }
}
