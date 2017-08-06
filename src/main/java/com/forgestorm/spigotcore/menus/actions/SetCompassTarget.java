package com.forgestorm.spigotcore.menus.actions;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.util.text.CenterChatText;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SetCompassTarget implements ClickAction {

	private final SpigotCore plugin;
	private final Location location;
	
	public SetCompassTarget(SpigotCore plugin, Location location) {
		this.plugin = plugin;
		this.location = location;
	}
	
	@Override
	public void click(Player player) {
		player.setCompassTarget(location);
		
		//Track players who we will update an action bar for.
		//This bar updates right above the players userbar.
		//Shows distance from and y cord info.
		plugin.getLocationTrackingManager().addToMap(player, location);

		//Close the players menu.
		if (player.getInventory() != null) player.closeInventory();
		
		//Show enroute message.
		player.sendMessage("");
		player.sendMessage(CenterChatText.centerChatMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Your menu compass is pointing at your target!"));
		player.sendMessage(CenterChatText.centerChatMessage(ChatColor.YELLOW + "The distance to your target is shown above your item bar."));
		player.sendMessage("");
		
		//Play sound.
		player.playSound(player.getEyeLocation(), Sound.BLOCK_NOTE_PLING, .8f, .7f);
	}
}
