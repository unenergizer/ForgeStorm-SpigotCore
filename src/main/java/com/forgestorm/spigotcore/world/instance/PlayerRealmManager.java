package com.forgestorm.spigotcore.world.instance;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.Messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerRealmManager {

	private final SpigotCore PLUGIN;
	
	private Map<UUID, PlayerRealm> realms = new HashMap<>();
	private Map<Player, PlayerRealmData> playerData = new HashMap<>();
	
	public PlayerRealmManager(SpigotCore plugin) {
		PLUGIN = plugin;
		runJoinTimeCountdowns();
	}

	public void disable() {
		removeAllPlayerRealms();
	}
	
	private void runJoinTimeCountdowns() {
		new BukkitRunnable() {

			@Override
			public void run() {
				for (Entry<Player, PlayerRealmData> entry: playerData.entrySet()) {
					PlayerRealmData pData = entry.getValue();
					pData.setTimeTillJoin(pData.getTimeTillJoin() - 1);
				}
			}
			
		}.runTaskTimerAsynchronously(PLUGIN, 0, 20);
	}
	
	public void joinRealm(Player player, Location location) {
		for (Entry<UUID, PlayerRealm> entry: realms.entrySet()) {
			PlayerRealm playerRealm = entry.getValue();
			
			if (location.distanceSquared(playerRealm.getPortalLocation()) <= 2) {
				World world = Bukkit.getWorld(entry.getKey().toString());
				PlayerRealmData pData = new PlayerRealmData(player, playerRealm.getPLAYER().getUniqueId(), player.getLocation());
	
				playerData.put(player, pData);
				
				player.teleport(new Location(world, 8, 95, 8));
				
				if (playerRealm.getTitle() != null) {
					player.sendMessage(playerRealm.getTitle());
				}
			}	
		}
	}
	
	public void leaveRealm(Player player) {
		PlayerRealmData data = playerData.get(player.getUniqueId());
		player.teleport(data.getJoinLocation());
		playerData.remove(player.getUniqueId());
	}
	
	public boolean hasRealm(Player player) {
		return realms.containsKey(player.getUniqueId());
	}

	public boolean addPlayerRealm(Player player, Location blockLocation) {
		//Make sure the user doesn't already have a realm open.
		if (!realms.containsKey(player.getUniqueId()) && blockLocation.getWorld().equals(Bukkit.getWorlds().get(0))) {
			
			//Make sure the player isn't setting the realm close to another realm.
			if (!getNearbyPortals(blockLocation, 2)) {
				PlayerRealm playerRealm = new PlayerRealm(PLUGIN, player, blockLocation);
				
				//Make sure we can actually set the portal. (Is the portal location 3 blocks high)
				if (playerRealm.setRealmPortal()) {
					player.sendMessage(Messages.REALM_PORTAL_OPENED.toString());
					player.sendMessage(Messages.REALM_PORTAL_TITLE.toString());

					realms.put(player.getUniqueId(), playerRealm);
					return true;

				} else {
					player.sendMessage(Messages.REALM_PORTAL_PLACE_DENY_BLOCK.toString());
					return false;
				}
			} else {
				player.sendMessage(Messages.REALM_PORTAL_PLACE_TOO_CLOSE.toString());
				return false;
			}
		} else {
			player.sendMessage(Messages.REALM_PORTAL_DUPLICATE.toString());
			return false;
		}
	}

	public PlayerRealm getPlayerRealm(Player player) {
		return realms.get(player.getUniqueId());
	}

	public void removePlayerRealmAtLocation(Player player, Location location) {
		if (hasRealm(player)) {
			UUID uuid = player.getUniqueId();
			PlayerRealm pr = realms.get(uuid);
			int x = location.getBlockX();
			int y = location.getBlockY();
			int z = location.getBlockZ();
			int realmX = pr.getBlockBottom().getLocation().getBlockX();
			int realmY = pr.getBlockBottom().getLocation().getBlockY();
			int realmZ = pr.getBlockBottom().getLocation().getBlockZ();

			if (x == realmX && z == realmZ && y >= realmY - 2 && y <= realmY + 2) {
				pr.removeRealm();
				realms.remove(uuid);
			}
		}
	}

	public void removePlayerRealm(Player player) {
		if (hasRealm(player)) {
			UUID uuid = player.getUniqueId();
			realms.get(uuid).removeRealm();
			realms.remove(uuid);
		}
	}

	private void removeAllPlayerRealms() {
		for (Entry<UUID, PlayerRealm> entry: realms.entrySet()) {
			entry.getValue().removeRealm();
		}
		realms.clear();
	}
	
	private boolean getNearbyPortals(Location location, int radius) {
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                   if (location.getWorld().getBlockAt(x, y, z).getType().equals(Material.PORTAL)) {
                	   return true;
                   }
                }
            }
        }
        return false;
    }
	
	public void setTitle(Player player, String title) {
		if (hasRealm(player)) {
			PlayerRealm realm = realms.get(player.getUniqueId());
			realm.setTitle(title);
		}
	}
}
