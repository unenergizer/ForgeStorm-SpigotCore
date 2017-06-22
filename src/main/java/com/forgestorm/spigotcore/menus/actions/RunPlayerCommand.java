package com.forgestorm.spigotcore.menus.actions;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.CommonSounds;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class RunPlayerCommand implements ClickAction {

    private final SpigotCore plugin;
    private final String command;
    private final int cost;

    @Override
    public void click(Player player) {
        //Buy Item (remove money from account)
        boolean canPurchase = plugin.getProfileManager().getProfile(player).removeCurrency(cost);

        player.chat("/" + command);
        //plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);

        if (cost <= 0) return;
        if (canPurchase) {
            player.sendMessage(ChatColor.GREEN + "Your purchase was successful!!");
            CommonSounds.ACTION_SUCCESS.playSound(player);
        } else {
            player.sendMessage(ChatColor.RED + "You do not have enough money to make this purchase.");
            CommonSounds.ACTION_FAILED.playSound(player);
        }
    }
}
