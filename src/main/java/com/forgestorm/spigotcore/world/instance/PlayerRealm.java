package com.forgestorm.spigotcore.world.instance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.FilePaths;
import com.forgestorm.spigotcore.util.display.Hologram;
import com.forgestorm.spigotcore.world.WorldDuplicator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerRealm {

	private final SpigotCore PLUGIN;
	private final Player PLAYER;
	private Hologram hologram = new Hologram();
	private Location portalLocation;
	private Block blockTop;
	private Block blockBottom;
	private WorldDuplicator worldDupe = new WorldDuplicator();
	private String title;
	
	//TODO:
	private boolean pvpEnabled;
	private boolean flyingEnabled;
	private int timeTillpvpRenabled;
	private int timeTillFlyingDisabled;

	PlayerRealm(SpigotCore plugin, Player player, Location location) {
		PLUGIN = plugin;
		PLAYER = player;
		portalLocation = location;
	}

	boolean setRealmPortal() {
		boolean cancel = true;
		int x = (int) portalLocation.getX();
		int y = (int) portalLocation.getY();
		int z = (int) portalLocation.getZ();
		Material air = Material.AIR;
		
		Block hologramSpace = portalLocation.getWorld().getBlockAt(x, y + 3, z);
		blockTop = portalLocation.getWorld().getBlockAt(x, y + 2, z);
		blockBottom = portalLocation.getWorld().getBlockAt(x, y + 1, z);

		//Set portal block
		if (hologramSpace.getType().equals(air) && blockTop.getType().equals(air) && blockBottom.getType().equals(air)) {
			cancel = false;
			blockTop.setType(Material.PORTAL);
			blockBottom.setType(Material.PORTAL);

			//Set title and realm status.
			Location hologramLocation = new Location(portalLocation.getWorld(), x + .5, y + 2.1, z + .5);
			ArrayList<String> hologramText = new ArrayList<>();
			hologramText.add(PLAYER.getName());
			hologramText.add(ChatColor.RED + "Chaotic");
			hologram.createHologram(hologramText, hologramLocation);

			//Load world
			loadPlayerWorld();
		}

		if (cancel) {
			return false;
		} else {
			return true;
		}
	}

	private void createPlayerWorld() {
		String name = PLAYER.getUniqueId().toString();
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
		String name = PLAYER.getUniqueId().toString();
		File wc = Bukkit.getServer().getWorldContainer();
		String path = wc + File.separator + "PlayerRealms" + File.separator + name;
		File destinationFolder = new File(wc + File.separator + name);
		File playerRealmsFolder = new File(path);

		if(!(new File(path)).exists()){
			createPlayerWorld();
		} else {

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
		String name = PLAYER.getUniqueId().toString();
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
		blockTop.setType(Material.AIR);
		blockBottom.setType(Material.AIR);

		//Remove Holograms
		hologram.removeHolograms();

		//Unload world
		unloadPlayerWorld();
	}
}
