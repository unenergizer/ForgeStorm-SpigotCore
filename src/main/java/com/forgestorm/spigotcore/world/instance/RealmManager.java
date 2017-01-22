package com.forgestorm.spigotcore.world.instance;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.Messages;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class RealmManager extends BukkitRunnable {

	private final SpigotCore PLUGIN;
	
	private Map<UUID, Realm> realms = new HashMap<>();
	private Map<Player, PlayerRealmData> playerData = new HashMap<>();
	
	public RealmManager(SpigotCore plugin) {
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
		for (Entry<UUID, Realm> entry: realms.entrySet()) {
			Realm realm = entry.getValue();
			
			if (location.distanceSquared(realm.getPortalOutsideLocation()) <= 2) {
				World world = Bukkit.getWorld(entry.getKey().toString());
				PlayerRealmData pData = new PlayerRealmData(player, realm.getRealmOwner().getUniqueId(), player.getLocation());
	
				playerData.put(player, pData);

				//TP player to inside portal location
				player.teleport(realm.getPortalInsideLocation());

				if (realm.getTitle() != null) {
					player.sendMessage(realm.getTitle());
				}
			}	
		}
	}
	
	public void leaveRealm(Player player) {
		PlayerRealmData data = playerData.get(player);
		player.teleport(data.getJoinLocation());
		playerData.remove(player);
	}
	
	public boolean hasRealm(Player player) {
		return realms.containsKey(player.getUniqueId());
	}

	public void addPlayerRealm(Player player, Location blockLocation) {
		//Make sure the user doesn't already have a realm open.
		if (!realms.containsKey(player.getUniqueId()) && blockLocation.getWorld().equals(Bukkit.getWorlds().get(0))) {
			
			//Make sure the player isn't setting the realm close to another realm.
			if (!getNearbyPortals(blockLocation, 2)) {
				Realm realm = new Realm(PLUGIN, player, blockLocation);
				
				//Make sure we can actually set the portal. (Is the portal location 3 blocks high)
				if (realm.setRealmPortal()) {
					player.sendMessage(Messages.REALM_PORTAL_OPENED.toString());
					player.sendMessage(Messages.REALM_PORTAL_TITLE.toString());

					realms.put(player.getUniqueId(), realm);
					return;

				} else {
					player.sendMessage(Messages.REALM_PORTAL_PLACE_DENY_BLOCK.toString());
					return;
				}
			} else {
				player.sendMessage(Messages.REALM_PORTAL_PLACE_TOO_CLOSE.toString());
				return;
			}
		} else {
			player.sendMessage(Messages.REALM_PORTAL_DUPLICATE.toString());
			return;
		}
	}

    public Realm getPlayerRealm(Player player) {
        return realms.get(player.getUniqueId());
    }
    public Realm getPlayerRealm(String uuid) {
        return realms.get(UUID.fromString(uuid));
    }

    public void removePlayerRealmAtLocation(Player player, Location location) {
		if (hasRealm(player)) {
			UUID uuid = player.getUniqueId();
			Realm realm = realms.get(uuid);
			int x = location.getBlockX();
			int y = location.getBlockY();
			int z = location.getBlockZ();
			int realmX = realm.getOutsideBlockBottom().getLocation().getBlockX();
			int realmY = realm.getOutsideBlockBottom().getLocation().getBlockY();
			int realmZ = realm.getOutsideBlockBottom().getLocation().getBlockZ();

			if (x == realmX && z == realmZ && y >= realmY - 2 && y <= realmY + 2) {

			    if (realm.getPlayerList().size() != 0) {
                    removeRealms.put(realms.get(uuid), 60 * 5);
                } else {
                    removeRealmWorld(realm.getPlayerList(), realm);
                }
			}
		}
	}

	public void removePlayerRealm(Player player) {
		if (hasRealm(player)) {
			UUID uuid = player.getUniqueId();
            removeRealms.put(realms.get(uuid), 60 * 5);
		}
	}

	private void removeAllPlayerRealms() {
		for (Entry<UUID, Realm> entry: realms.entrySet()) {
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
			Realm realm = realms.get(player.getUniqueId());
			realm.setTitle(title);
			PLUGIN.getProfileManager().getProfile(player).setRealmTitle(title);
		}
	}

	public boolean canBuild(Player player) {
        //Player is in an instance
        String worldName = player.getWorld().getName();
        Realm realm = getPlayerRealm(worldName);
        if (player.equals(realm.getRealmOwner())) {
            return true;
        } else if (!worldName.equals(player.getUniqueId().toString())) {
            //If the player is not a friend, do not let them build.
            return realm.isFriend(player);
        }
        return false;
    }

    private Map<Realm, Integer> removeRealms = new ConcurrentHashMap<>();

	private final int closeDownTime = 60 * 5;

    @Override
    public void run() {

        for (Realm realm : removeRealms.keySet()) {

            int countDown = removeRealms.get(realm);

            List<Player> players = realm.getPlayerList();

            if (countDown % 60 == 0) {
                for (Player player : players) {
                    player.sendMessage("Closing world in " + countDown / 60 + " minutes.");
                }
            }

            if (countDown <= 10 || countDown == 30) {
                for (Player player : players) {
                    player.sendMessage("Closing world in " + countDown + " seconds.");
                }
            }

            if (countDown <= 0 || players.size() == 0) {

                players = realm.getPlayerList();
                removeRealmWorld(players, realm);

            } else {
                removeRealms.replace(realm, --countDown);
            }
        }
    }

    private void removeRealmWorld(List<Player> players, Realm realm) {
        for (Player player : players) leaveRealm(player);

        realm.removeRealm();
        realms.remove(realm.getRealmOwner().getUniqueId());
    }
}
