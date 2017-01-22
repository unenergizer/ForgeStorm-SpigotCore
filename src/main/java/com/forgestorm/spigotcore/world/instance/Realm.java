package com.forgestorm.spigotcore.world.instance;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.FilePaths;
import com.forgestorm.spigotcore.util.display.Hologram;
import com.forgestorm.spigotcore.world.WorldDuplicator;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Realm {

	private final SpigotCore PLUGIN;
	private final Player realmOwner;
	private Hologram hologram = new Hologram();
    private Location portalOutsideLocation;
    private Location portalInsideLocation;
	private Block outsideBlockTop, outsideBlockBottom, insideBlockTop, insideBlockBottom;
	private WorldDuplicator worldDupe = new WorldDuplicator();
	private String title;

	private List<Player> friends = new ArrayList<>();
	
	//TODO:
	private boolean pvpEnabled;
	private boolean flyingEnabled;
	private int timeTillpvpRenabled;
	private int timeTillFlyingDisabled;

	Realm(SpigotCore plugin, Player realmOwner, Location location) {
		PLUGIN = plugin;
		this.realmOwner = realmOwner;
		portalOutsideLocation = location;
		title = plugin.getProfileManager().getProfile(realmOwner).getRealmTitle();
	}

	public void addFriend(Player friend) {
        friends.add(friend);
    }

    public void removeFriend(Player friend) {
	    friends.remove(friend);
    }

    public boolean isFriend(Player player) {
	    return friends.contains(player);
    }

	private Location parseLocation(String sLoc) {
	    String[] locxyz = sLoc.split("/");
	    return new Location(Bukkit.getWorld(realmOwner.getUniqueId().toString()),
                Double.valueOf(locxyz[0]),
                Double.valueOf(locxyz[1]),
                Double.valueOf(locxyz[2]));
    }

	public void setPlayerInsidePortal() {
        int x = (int) portalInsideLocation.getX();
        int y = (int) portalInsideLocation.getY();
        int z = (int) portalInsideLocation.getZ();

        insideBlockTop = portalInsideLocation.getWorld().getBlockAt(x, y + 1, z);
        insideBlockBottom = portalInsideLocation.getWorld().getBlockAt(x, y, z);

        insideBlockTop.setType(Material.PORTAL);
        insideBlockBottom.setType(Material.PORTAL);
	}

	boolean setRealmPortal() {
		boolean cancel = true;
		int x = (int) portalOutsideLocation.getX();
		int y = (int) portalOutsideLocation.getY();
		int z = (int) portalOutsideLocation.getZ();
		Material air = Material.AIR;
		
		Block hologramSpace = portalOutsideLocation.getWorld().getBlockAt(x, y + 3, z);
		outsideBlockTop = portalOutsideLocation.getWorld().getBlockAt(x, y + 2, z);
		outsideBlockBottom = portalOutsideLocation.getWorld().getBlockAt(x, y + 1, z);

		//Set portal block
		if (hologramSpace.getType().equals(air) && outsideBlockTop.getType().equals(air) && outsideBlockBottom.getType().equals(air)) {
			cancel = false;
			outsideBlockTop.setType(Material.PORTAL);
			outsideBlockBottom.setType(Material.PORTAL);

			//Set title and realm status.
			Location hologramLocation = new Location(portalOutsideLocation.getWorld(), x + .5, y + 2.1, z + .5);
			ArrayList<String> hologramText = new ArrayList<>();
			hologramText.add(realmOwner.getName());
			hologramText.add(ChatColor.RED + "Chaotic");
			hologram.createHologram(hologramText, hologramLocation);

			//Load world
			loadPlayerWorld();

			//Set inside realm portal.
            portalInsideLocation = parseLocation(PLUGIN.getProfileManager().getProfile(realmOwner).getRealmInsideLocation());
            setPlayerInsidePortal();
		}

		return !cancel;
	}

	private void createPlayerWorld() {
		String name = realmOwner.getUniqueId().toString();
		File wc = Bukkit.getServer().getWorldContainer();
		File destinationFolder = new File(wc + File.separator + name);
		File backupFolder = new File(FilePaths.WORLD_PLAYER_DEFAULT.toString());

		if (worldDupe.getWorld() == null) {
			//Copy the backup world folder.
			try {
				worldDupe.copyFolder(backupFolder, destinationFolder);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//Show success message in console.
			Bukkit.getServer().getLogger().info("[FSRPG] Created new world: " + name);

			//Load the map into memory
			worldDupe.loadWorld(name);

			//Stop natural spawning of entities.
			worldDupe.stopEntitySpawns();

			//Cleanup any map entities.
			worldDupe.clearEntities();

		} else {
			Bukkit.getServer().getLogger().info("[FSRPG] World var not null? We will not load the next world.");
		}
	}

	private void loadPlayerWorld() {
		String name = realmOwner.getUniqueId().toString();
		File wc = Bukkit.getServer().getWorldContainer();
		String path = wc + File.separator + "PlayerRealms" + File.separator + name;
		File destinationFolder = new File(wc + File.separator + name);
		File playerRealmsFolder = new File(path);

		if(!(new File(path)).exists()){
			createPlayerWorld();
		} else {

		    //Load world
			if (worldDupe.getWorld() == null) {
				//Copy the backup world folder.
				try {
					worldDupe.copyFolder(playerRealmsFolder, destinationFolder);
				} catch (IOException e) {
					e.printStackTrace();
				}

				//Show success message in console.
				Bukkit.getServer().getLogger().info("[FSRPG] Copied world: " + name);

				//Load the map into memory
				worldDupe.loadWorld(name);

				//Stop natural spawning of entities.
				worldDupe.stopEntitySpawns();

				//Cleanup any map entities.
				worldDupe.clearEntities();

			} else {
				Bukkit.getServer().getLogger().info("[FSRPG] World var not null? We will not load the next world.");
			}
		}
	}

	private void unloadPlayerWorld() {
		String name = realmOwner.getUniqueId().toString();
		File wc = Bukkit.getServer().getWorldContainer();


		File destinationFolder = new File(wc + File.separator + name);
		File playerRealmsFolder = new File(wc + File.separator + "PlayerRealms" + File.separator + name);

		//Unload the current game map from memory.
		worldDupe.unloadWorld();

		//Delete the world folder.
		try {
			worldDupe.copyFolder(destinationFolder, playerRealmsFolder);
			worldDupe.deleteFolder(destinationFolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void removeRealm() {
        //Remove Blocks
        outsideBlockTop.setType(Material.AIR);
        outsideBlockBottom.setType(Material.AIR);
        insideBlockTop.setType(Material.AIR);
        insideBlockBottom.setType(Material.AIR);

		//Remove Holograms
		hologram.removeHolograms();

		//Unload world
		unloadPlayerWorld();
	}

	List<Player> getPlayerList() {
	    return Bukkit.getWorld(realmOwner.getUniqueId().toString()).getPlayers();
    }
}
