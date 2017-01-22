package com.forgestorm.spigotcore.games.dragoneggtp;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.FilePaths;
import com.forgestorm.spigotcore.util.display.Hologram;
import com.forgestorm.spigotcore.util.math.RandomChance;
import com.forgestorm.spigotcore.util.player.PlayerRewards;
import com.forgestorm.spigotcore.util.text.CenterChatText;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DragonEggTeleport {
	
	private final SpigotCore PLUGIN;
	
	private final FileConfiguration config;
	private final List<Location> locations;
	private final Hologram hologram;
	private Location eggLocation;

	public DragonEggTeleport(SpigotCore plugin) {
		PLUGIN = plugin;
		config = YamlConfiguration.loadConfiguration(
				new File(FilePaths.GAMES_DRAGONEGG.toString()));
		locations = new ArrayList<>();
		hologram = new Hologram();
		loadLocations();

		//Spawn the egg after server startup.
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, this::spawnEgg, 5 * 20L);
	}

	public void disable() {
		//Despawn the block
		eggLocation.getBlock().setType(Material.AIR);

		//Remove the hologram.
		hologram.removeHolograms();
	}

	public void toggleEggClick(Player player, Location clickLocation) {

		//Make sure the player clicks the official dragon egg.
		//This will stop them from clicking an egg on a "build."
		if (clickLocation.equals(eggLocation)) {
			//TODO: Play Animation
			for (double i = 0; i < 2; i++) {
				Firework fw = player.getWorld().spawn(clickLocation.subtract(0, -2, 0), Firework.class);
				FireworkMeta fm = fw.getFireworkMeta();
				fm.addEffect(FireworkEffect.builder()
						.flicker(false)
						.trail(false)
						.with(Type.BURST)
						.withColor(Color.PURPLE)
						.withFade(Color.BLACK)
						.build());
				fw.setFireworkMeta(fm);
			}
			
			//Play sound
			Bukkit.getWorlds().get(0).playSound(eggLocation, Sound.ENTITY_SHULKER_BULLET_HURT, 1, .7f);

			//Despawn the block
			eggLocation.getBlock().setType(Material.AIR);

			//Remove the hologram.
			hologram.removeHolograms();
			
			//Reward Message
			long exp = 100;
			int money = 100;
			
			//Send player text
			String rewardText = ChatColor.GREEN + "" + ChatColor.BOLD + "EXP: +" + exp 
					+ ChatColor.YELLOW + "" + ChatColor.BOLD + " Money: +" + money;

			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage(CenterChatText.centerChatMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "YOU FOUND THE HIDDEN ENDER EGG!!"));
			player.sendMessage("");
			player.sendMessage(CenterChatText.centerChatMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "THE EGG HAS DISAPPEARED!"));
			player.sendMessage(CenterChatText.centerChatMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "CAN YOU FIND IT AGAIN?"));
			player.sendMessage("");
			player.sendMessage(CenterChatText.centerChatMessage(rewardText));
			player.sendMessage("");
			player.sendMessage("");
			
			//Give Reward
			PlayerRewards reward = new PlayerRewards(PLUGIN, player);
			reward.giveExp(100);
			reward.giveMoney(100);
			
			//Play Sound
			player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, .8f);
			
			//Spawn new egg!
			spawnEgg();
		}
	}

	public void teleportToEgg(Player player) {
		player.teleport(eggLocation);
	}

	private void spawnEgg() {

		//Set the Block
		int index = RandomChance.randomInt(0, locations.size() - 1);
		eggLocation = locations.get(index); //Set new egg location
		Block block = Bukkit.getWorlds().get(0).getBlockAt(eggLocation);
		block.setType(Material.DRAGON_EGG);

		//Play sound
		Bukkit.getWorlds().get(0).playSound(eggLocation, Sound.ENTITY_EGG_THROW, 1, .7f);
		
		//Spawn the hologram.		
		ArrayList<String> hologramText = new ArrayList<>();
		hologramText.add("&5&lTP EGG GAME");
		hologramText.add(ChatColor.BOLD + "RIGHT-CLICK");
		
		double x, y, z;
		x = eggLocation.getX() + .5;
		y = eggLocation.getY() + .3;
		z = eggLocation.getZ() + .5;

		Location hologramLoc = new Location(eggLocation.getWorld(), x, y, z);
		hologram.createHologram(hologramText, hologramLoc);
	}

	private void loadLocations() {
		World world = Bukkit.getWorlds().get(0);
		ConfigurationSection section = config.getConfigurationSection("Locations");
		Iterator<String> it = section.getKeys(false).iterator();
		String i;

		while (it.hasNext()) {
			i = it.next();
			double x, y, z;
			//System.out.println("Locations." + i + ".x");
			//System.out.println("Locations." + i + ".y");
			//System.out.println("Locations." + i + ".z");

			x = config.getDouble("Locations." + i + ".x");
			y = config.getDouble("Locations." + i + ".y");
			z = config.getDouble("Locations." + i + ".z");
			locations.add(new Location(world, x, y, z));
		}
	}
}
