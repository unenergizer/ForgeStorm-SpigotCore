package com.forgestorm.spigotcore.help;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.util.text.CenterChatText;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class LocationTrackingManager extends BukkitRunnable {

	private final SpigotCore plugin;
	private final Map<UUID, Location> targetLocations = new ConcurrentHashMap<>();
	private final DecimalFormat decimalFormat = new DecimalFormat("#.##");
	private boolean disable = false;

	public LocationTrackingManager(SpigotCore plugin) {
	    this.plugin = plugin;
    }
	
	public void addToMap(Player player, Location location) {
		if (targetLocations.containsKey(player.getUniqueId())) targetLocations.remove(player.getUniqueId());
		targetLocations.put(player.getUniqueId(), location);
	}

	public void removePlayer(Player player) {
		if (targetLocations.containsKey(player.getUniqueId())) {
			targetLocations.remove(player.getUniqueId());
		}
	}
	
	public void disable() {
		disable = true;
	}

	@Override
	public void run() {
		
		if (disable) cancel();
		
		//UPDATE THE PLAYERS ACTION BARS.
		for (Map.Entry<UUID, Location> entry : targetLocations.entrySet()) {
			UUID uuid = entry.getKey();
			Player player = Bukkit.getPlayer(uuid);
			double offset = 4; //Blocks away from the target.
			Location playerLocation = player.getLocation();
			Location target = entry.getValue();
			double distance = playerLocation.distance(target);
			double yDistance = Math.abs(playerLocation.getY() - target.getY());

			//Remove them from the map if they are close to the target.
			if (distance < offset) {
				targetLocations.remove(player.getUniqueId());
				
				player.sendMessage("");
				player.sendMessage(CenterChatText.centerChatMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "You have arrived at your destination!"));
				player.sendMessage(CenterChatText.centerChatMessage(ChatColor.GRAY + "The compass now points back to spawn."));
				player.sendMessage("");
				
				//Send blank message to clear!
				plugin.getTitleManagerAPI().sendActionbar(player, " ");
				
				//Reset the players compassTarget to the spawn location.
				player.setCompassTarget(new Location(Bukkit.getWorlds().get(0), 0.5, 108, -24.5));
				
				//Play Sound.
				player.playSound(player.getEyeLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, .7f);
				
			} else { //Show them a message on the action bar.	
				String xzString = ChatColor.BOLD + "Distance: " + ChatColor.YELLOW + decimalFormat.format(distance - offset);
				String yString = "";
				
			
				//Show the player a message letting them know if they need to go up or down.
				if (playerLocation.getY() > target.getY() + 4) {
					//Go down
					yString = ChatColor.RESET + "    " + ChatColor.BOLD + "Go Down: " + ChatColor.YELLOW + decimalFormat.format(yDistance) + " blocks";
				} else if (playerLocation.getY() < target.getY() - 4) {
					//Go up
					yString = ChatColor.RESET + "    " + ChatColor.BOLD + "Go Up: " + ChatColor.YELLOW + decimalFormat.format(yDistance) + " blocks";
				}

                plugin.getTitleManagerAPI().sendActionbar(player, xzString + yString);
			}
		}
	}


}
