package com.forgestorm.spigotcore.world.animate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.FilePaths;
import com.forgestorm.spigotcore.util.display.FloatingMessage;
import com.sk89q.worldedit.CuboidClipboard;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.world.DataException;

import lombok.Getter;

@SuppressWarnings("deprecation")
@Getter
public class WorldAnimator {

	private final SpigotCore PLUGIN;
	private final String FILE_PATH = FilePaths.WORLD_ANIMATE_ANIMATIONS.toString();

	private File file;
	private FileConfiguration config;
	private List<Long> animationTimes;
	private World world;
	private boolean allowWeatherChange;

	public WorldAnimator(SpigotCore plugin) {
		//Init variables.
		PLUGIN = plugin;
		animationTimes = new ArrayList<>();
		allowWeatherChange = false;

		//If the configuration does not exist, create the file. Otherwise lets load the file.
		if(!(new File(FILE_PATH)).exists()){
			createConfig();
		} else {
			//lets load the configuration file.
			file = new File(FILE_PATH);
			config =  YamlConfiguration.loadConfiguration(file);

			//Run setup methods.
			getAnimationTimes(config);

			//Set World
			world = Bukkit.getWorlds().get(0);

			//Set world Time
			world.setTime(0);
		}
	}

	private void getAnimationTimes(FileConfiguration config) {

		Iterator<String> it = config.getConfigurationSection("Animations").getKeys(false).iterator();

		while (it.hasNext()) {	
			animationTimes.add(Long.parseLong(it.next()));
		}
	}

	void shouldAnimate(long time) {
		if (animationTimes.contains(time)) {
			animate(time);
		}
	}

	private void animate(long time) {

		final String prefix = "Animations." + Long.toString(time);
		String suffix;
		Iterator<String> iterator;

		/////////////////////
		// BLOCKS
		/////////////////////
		suffix  = prefix + ".Blocks";
		if (config.contains(suffix)) {
			iterator = config.getConfigurationSection(suffix).getKeys(false).iterator();
			if (iterator != null) {
				while (iterator.hasNext()) {
					int i = Integer.parseInt(iterator.next());

					double x = config.getDouble(suffix + "." + i + ".x");
					double y = config.getDouble(suffix + "." + i + ".y");
					double z = config.getDouble(suffix + "." + i + ".z");
					String type = config.getString(suffix + "." + i + ".blocktype");

					//Set block
					world.getBlockAt(new Location(world, x, y, z)).setType(Material.getMaterial(type.toUpperCase()));				
				}	
			}
		}

		/////////////////////
		// TODO PARTICLES
		/////////////////////
		/*
		suffix = prefix + "Particles";
		iterator = config.getConfigurationSection(prefix).getKeys(false).iterator();
		if (iterator != null) {
			while (iterator.hasNext()) {
				int i = Integer.parseInt(iterator.next());

				double x = config.getDouble(suffix + "." + i + ".x");
				double y = config.getDouble(suffix + "." + i + ".y");
				double z = config.getDouble(suffix + "." + i + ".z");
				String type = config.getString(suffix + "." + i + ".particletype");

				// Perform particle shit
			}
		}
		 */

		/////////////////////
		// SCHEMATICS
		/////////////////////

		suffix = prefix + ".Schematics";

		if (config.contains(suffix)) {
			iterator = config.getConfigurationSection(suffix).getKeys(false).iterator();
			if (iterator != null) {
				while (iterator.hasNext()) {
					int i = Integer.parseInt(iterator.next());

					double x = config.getDouble(suffix + "." + i + ".x");
					double y = config.getDouble(suffix + "." + i + ".y");
					double z = config.getDouble(suffix + "." + i + ".z");
					String filename = config.getString(suffix + "." + i + ".filename").replace(".schematic", "");

					//String path = PLUGIN.getDataFolder().getAbsolutePath();
					//System.out.println("path " + path + "plugins\\FSHub\\animations\\schematics\\" + filename + ".schematic");


					// Perform particle shit
					try {
						loadArea(Bukkit.getWorlds().get(0), new File( 
								FilePaths.WORLD_ANIMATE_SCHEMATIC.toString() +
								filename + ".schematic"), new Vector(x, y, z));

					} catch (MaxChangedBlocksException | IOException | DataException e) {
						e.printStackTrace();
					}
				}
			}	
		}

		/////////////////////
		// TODO MOBS
		/////////////////////

		/////////////////////
		// WEATHER
		/////////////////////
		suffix = prefix + ".Weather";

		if (config.contains(suffix)) {
			String weatherType = config.getString(suffix);
			allowWeatherChange = true;

			if (weatherType != null) {

				switch(weatherType) {
				case "LIGHTSTORM":
					//System.out.println("[WORLDANIMATE] Lightstorm");

					world.setStorm(true);
					world.setThundering(false);

					world.setWeatherDuration(Integer.MAX_VALUE);
					world.setThunderDuration(0);
					break;

				case "HEAVYSTORM":
					//System.out.println("[WORLDANIMATE] Heavystorm");

					world.setStorm(true);
					world.setThundering(true);

					world.setWeatherDuration(Integer.MAX_VALUE);
					world.setThunderDuration(Integer.MAX_VALUE);
					break;

				case "NONE": //skip
					//System.out.println("[WORLDANIMATE] None");
				default:
					//System.out.println("[WORLDANIMATE] Default");

					world.setStorm(false);
					world.setThundering(false);

					world.setWeatherDuration(0);
					world.setThunderDuration(0);
					break;
				}

			}

			allowWeatherChange = false;
		}


		/////////////////////
		// CHAT TEXT
		/////////////////////
		suffix  = prefix + ".ChatText";
		if (config.contains(suffix)) {
			iterator = config.getConfigurationSection(suffix).getKeys(false).iterator();
			while (iterator.hasNext()) {
				int i = Integer.parseInt(iterator.next());

				String chat = config.getString(suffix + "." + i);

				for (Player players : Bukkit.getOnlinePlayers()) {
					players.sendMessage(ChatColor.translateAlternateColorCodes('&', chat));
				}
			}
		}

		/////////////////////
		// TITLES
		/////////////////////

		String title = ChatColor.translateAlternateColorCodes('&', config.getString(prefix + ".Titles.title"));
		String subtitle = ChatColor.translateAlternateColorCodes('&', config.getString(prefix + ".Titles.subtitle"));

		if (title != null && subtitle != null) {

			for (Player player : Bukkit.getOnlinePlayers()) {
				new FloatingMessage().sendFloatingMessage(player, title, subtitle);	
			}
		} else if (title != null) {

			for (Player player : Bukkit.getOnlinePlayers()) {
				new FloatingMessage().sendFloatingMessage(player, title, "");	
			}
		} else if (subtitle != null) {

			for (Player player : Bukkit.getOnlinePlayers()) {
				new FloatingMessage().sendFloatingMessage(player, "", subtitle);	
			}
		}

		/////////////////////
		// SOUNDS
		/////////////////////
		suffix  = prefix + ".Sounds";

		if (config.contains(suffix)) {
			iterator = config.getConfigurationSection(suffix).getKeys(false).iterator();

			if (iterator != null) {
				while (iterator.hasNext()) {
					int i = Integer.parseInt(iterator.next());

					double x = config.getDouble(suffix + "." + i + ".x");
					double y = config.getDouble(suffix + "." + i + ".y");
					double z = config.getDouble(suffix + "." + i + ".z");
					String sound = config.getString(suffix + "." + i + ".sound").toUpperCase();
					//long volume = config.getLong(suffix + "." + i + ".volume");
					long pitch = config.getLong(suffix + "." + i + ".pitch");
					double maxDistance = config.getDouble(suffix + "." + i + ".maxdistance");

					// Play sound out to the world
					//world.playSound(new Location(world, x, y, z), Sound.valueOf(sound), volume, pitch);

					//Created a new sound method to play sounds further than normal.
					playSound(Sound.valueOf(sound), pitch, new Location(world, x, y, z), maxDistance);
				}	
			}
		}
	}

	/**
	 * Play sounds further with this method.  The volume of the sound is
	 * adjusted per player based on the distance they are from the supplied
	 * sound location.
	 * 
	 * @param sound The sound to play.
	 * @param pitch The pitch of the sound.
	 * @param location The location the sound will play.
	 * @param maxPlayDistance The max distance the sound can be heard by a player.
	 */
	private void playSound(Sound sound, float pitch, Location location, double maxPlayDistance) {

		Collection<Entity> entities = location.getWorld().getNearbyEntities(location, maxPlayDistance, maxPlayDistance, maxPlayDistance);

		for (Entity entity : entities) {

			if (entity instanceof Player) {
				Player player = (Player) entity;
				
				//Adjust the volume of the sound played based on the players
				//distance from the sound location.
				double distance = player.getLocation().distance(location);
				float volume = (float)(distance / maxPlayDistance);
				
				//Play Sound with custom volume.
				player.playSound(location, sound, volume, pitch);
			}
		}
	}

	private void loadArea(World world, File file, Vector origin) throws IOException, MaxChangedBlocksException, com.sk89q.worldedit.world.DataException {

		EditSession es = new EditSession(new BukkitWorld(world), Integer.MAX_VALUE);
		CuboidClipboard cc = CuboidClipboard.loadSchematic(file);

		cc.paste(es, origin, false);
	}

	/**
	 * Creates a new configuration file.
	 */
	private void createConfig() {

		file = new File(FILE_PATH);
		config =  YamlConfiguration.loadConfiguration(file);

		config.set("Animations.0.blocks", "tutorial");
		config.set("Animations.0.particles", "7");
		config.set("Animations.0.schematics", "2");
		config.set("Animations.0.mobs", "Welcome!");
		config.set("Animations.0.weather", "rain");

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
