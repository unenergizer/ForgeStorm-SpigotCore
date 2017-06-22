package com.forgestorm.spigotcore.commands;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class Compass implements CommandExecutor {

	private final SpigotCore plugin;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

            // Set the player compass menu, just encase it gets deleted.
            player.getInventory().setItem(0, plugin.getItemGen().generateItem("main_menu", ItemTypes.MENU));

            //Reset the players compass.
            player.setCompassTarget(new Location(Bukkit.getWorlds().get(0), 0.5, 108, -24.5));
        }
        return false;
    }
}
