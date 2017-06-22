package com.forgestorm.spigotcore.help;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.CommonSounds;
import com.forgestorm.spigotcore.constants.FilePaths;
import com.forgestorm.spigotcore.constants.SpigotCoreMessages;
import com.forgestorm.spigotcore.util.player.PlayerRewards;
import com.forgestorm.spigotcore.util.text.CenterChatText;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Getter
public class AnimatedTutorial implements Listener {

	private final SpigotCore plugin;
	private final int EMPTY_LINES;
	private final String filePath;
	private final Map<Player, String> activePlayers = new HashMap<>();
	private final Map<UUID, Location> playerStartLocations;
	private File file;
	private FileConfiguration config;


	public AnimatedTutorial(SpigotCore plugin) {
		this.plugin = plugin;
		EMPTY_LINES = 5;
		playerStartLocations = new HashMap<>();
		filePath = FilePaths.HELP_TUTORIAL.toString();

		//If the configuration does not exist, create the file. Otherwise lets load the file.
		if (!(new File(filePath)).exists()) {
			createConfig();
		} else {
			//lets load the configuration file.
			file = new File(filePath);
			config =  YamlConfiguration.loadConfiguration(file);
		}
	}

	public void onDisable() {
		// Unregister Events
		PlayerMoveEvent.getHandlerList().unregister(this);
	}

	/**
	 * This starts the tutorial.
	 * @param player The player who is viewing the tutorial.
	 * @param tutorialID The name of the tutorial the player will watch.
	 */
	public void startTutorial(Player player, String tutorialID) {
		//Cancel if the player is already in the tutorial.
		//This is good to prevent double clicks.
		if (activePlayers.containsKey(player)) {
			return;
		}
		
		//Set them into the tutorial.
		plugin.getProfileManager().getProfile(player).setInTutorial(true);
		
		//Save the tutorial start location (to tp them back where they started it).
		playerStartLocations.put(player.getUniqueId(), player.getLocation());

		activePlayers.put(player, tutorialID);
		ConfigurationSection cs = config.getConfigurationSection("Tutorial." + tutorialID + ".frames");
		Iterator<String> it = cs.getKeys(false).iterator();

		int frameTime = config.getInt("Tutorial." + tutorialID + ".frametime");

		//Setup the player
		player.setAllowFlight(true);
		player.setFlying(true);

		//Hide other players.
		for (Player otherPlayers : Bukkit.getOnlinePlayers()) {
			if (!otherPlayers.hasMetadata("NPC")) {
				player.hidePlayer(otherPlayers);
				otherPlayers.hidePlayer(player);
			}
		}

		//Start a repeating task.
		new BukkitRunnable() {

			boolean teleported = false, largeMessage = false, chatEnabled = false, chatFinished = false;
			int frame = 0, messageCount = 0;

			@Override
			public void run() {
				String path = "Tutorial." + tutorialID + ".frames." + frame + ".";
				//String path = "Tutorial." + tutorialID + ".frames." + frame + ".";

				//Teleport player
				if (!teleported) {
					
					if (config.getBoolean(path + ".showInTutorialMenu")) {
						//TODO: Prevent hidden tutorials from showing up in the menu.
						//Hidden tutorials are usually ran at special times (first join, boss intro, etc).
					}
					
					if (config.contains(path + "teleport.x") 
							&& config.contains(path + "teleport.y")
							&& config.contains(path + "teleport.z")
							&& config.contains(path + "teleport.yaw")
							&& config.contains(path + "teleport.pitch")) {
						double x, y, z;
						float yaw, pitch;
						x = config.getDouble(path + "teleport.x");
						y = config.getDouble(path + "teleport.y");
						z = config.getDouble(path + "teleport.z");
						yaw = (float) config.getDouble(path + "teleport.yaw");
						pitch = (float) config.getDouble(path + "teleport.pitch");

						Location loc = new Location(player.getWorld(), x, y, z, yaw, pitch);
						player.teleport(loc);

						teleported = true;
						//System.out.println("[Tutorial] Teleported!");
					}

					//Display Large Message
					if (!largeMessage) {
						if (config.contains(path + "title") 
								|| config.contains(path + "subtitle")) {
							String title, subtitle;

							if (config.contains(path + "title")) {
								title = config.getString(path + "title");
							} else {
								title = "";
							}

							if (config.contains(path + "subtitle")) {
								subtitle = config.getString(path + "subtitle");
							} else {
								subtitle = "";
							}

							plugin.getTitleManagerAPI().sendTitles(player, color(title), color(subtitle));
							//System.out.println("[Tutorial] Floating message displayed!");
						}

						largeMessage = true;
					}

					//Display Chat Message
					if (config.contains(path + "chat")) {
						chatEnabled = true;
						List<String> list = config.getStringList(path + "chat");

						//System.out.println("[Tutorial] messageCount: " + messageCount);
						//System.out.println("[Tutorial] Entries: " + list.size());

						// the void of emptiness
						if (messageCount != list.size()) {

							//Show top bar.
							player.sendMessage("");
							player.sendMessage("");
							player.sendMessage(SpigotCoreMessages.BAR_TUTORIAL.toString());
							player.sendMessage("");

							for (int i = 0; i <= messageCount; i++) {
								player.sendMessage(color(list.get(i)));

								//Play a sound
								if (!list.get(messageCount).isEmpty()) {
									player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1, .5f);
								}
							}

							//Add any blank lines we may need.
							int leftOverLines = EMPTY_LINES - messageCount;


							//System.out.println("[Tutorial] LeftOverLines: " + leftOverLines);

							for (int j = 0; j <= leftOverLines; j++) {
								player.sendMessage("");
							}

							//Show bottom bar.
							player.sendMessage(SpigotCoreMessages.BAR_BOTTOM.toString());

							messageCount++;
						} else {
							chatFinished = true;
							//System.out.println("[Tutorial] Chat messaged shown!");
						}

					}

					if (!chatEnabled || chatFinished) {
						frame++;

						if (!it.hasNext()) {
							cancel();

							//Play sound
							CommonSounds.ACTION_FAILED.playSound(player);

							endTutorial(player, false);
						} else {
							//reset vars
							teleported = largeMessage = chatEnabled = chatFinished = false;
							messageCount = 0;

							it.next();
						}
					}
				}
			}
		}.runTaskTimer(plugin, 0, 20 * frameTime);
	}

	/**
	 * This will end the tutorial and teleport the player back to the spawn.
	 * @param player The player who is leaving the tutorial.
	 */
	public void endTutorial(Player player, boolean playerQuit) {
		if (Bukkit.getOnlinePlayers().contains(player)) {

			//Set them into the tutorial.
			plugin.getProfileManager().getProfile(player).setInTutorial(false);
			
			//Teleport them to last spawn point.
			player.teleport(playerStartLocations.get(player.getUniqueId()));
			playerStartLocations.remove(player.getUniqueId());

			//Setup Player
			player.setAllowFlight(false);
			player.setFlying(false);

			//Unhide other players.
			for (Player otherPlayers : Bukkit.getOnlinePlayers()) {
				if (!otherPlayers.hasMetadata("NPC")) {
					player.showPlayer(otherPlayers);
					otherPlayers.showPlayer(player);
				}
			}			

			String tutorialID = activePlayers.get(player);
			String rewardText;
			String rewardText2 = "";
			
			
			//Give reward.
			List<String> completedTutorials = plugin.getProfileManager().getProfile(player).getCompletedTutorials();
			if (!completedTutorials.contains(tutorialID) && !playerQuit) {
				int exp = config.getInt("Tutorial." + tutorialID + ".rewards.experience");
				int money = config.getInt("Tutorial." + tutorialID + ".rewards.money");

				PlayerRewards reward = new PlayerRewards(plugin, player);
				reward.giveExp(exp);
				reward.giveMoney(money);
				rewardText = CenterChatText.centerChatMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "EXP: " + exp 
						+ ChatColor.YELLOW + "" + ChatColor.BOLD + " Money: " + money);

				completedTutorials.add(tutorialID);
			} else {
				rewardText = CenterChatText.centerChatMessage(ChatColor.GRAY + "" + ChatColor.BOLD + "You have already completed this tutorial.");
				rewardText2 = CenterChatText.centerChatMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "(no rewards will be given)");
			}

			//Show top bar.
			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage(CenterChatText.centerChatMessage(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "THE END!!"));
			player.sendMessage(CenterChatText.centerChatMessage(ChatColor.WHITE + "" + ChatColor.BOLD + "Thanks for viewing this tutorial!"));
			player.sendMessage(rewardText);
			player.sendMessage(rewardText2);
			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage("");
			
			//Play Sound
			CommonSounds.ACTION_SUCCESS.playSound(player);
		}

		//Remove them from the list.
		activePlayers.remove(player);
	}	

	private String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	/**
	 * Creates a new configuration file.
	 */
	private void createConfig() {

		file = new File(filePath);
		config =  YamlConfiguration.loadConfiguration(file);

		config.set("tutorial.name", "tutorial");
		config.set("tutorial.frametime", "7");
		config.set("tutorial.frames", "2");
		config.set("tutorial.0.title", "Welcome!");
		config.set("tutorial.0.subtitle", "This is a test tutorial!");
		config.set("tutorial.0.x", 1);
		config.set("tutorial.0.y", 120);
		config.set("tutorial.0.z", 3);
		config.set("tutorial.1.title", "Blah!");
		config.set("tutorial.1.subtitle", "Blah blah!!");
		config.set("tutorial.1.x", 10);
		config.set("tutorial.1.y", 120);
		config.set("tutorial.1.z", 10);


		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();

		// Prevent players in the animatedTutorial from moving.
		if (getActivePlayers().containsKey(player)) {
			double moveX = event.getFrom().getX();
			double moveToX = event.getTo().getX();

			double moveY = event.getFrom().getY();
			double moveToY = event.getTo().getY();

			double moveZ = event.getFrom().getZ();
			double moveToZ = event.getTo().getZ();

			//If the animatedTutorial has started, then let the player look around, but not jump, fly, walk, or run.
			if ((moveX != moveToX || moveY != moveToY || moveZ != moveToZ)) {
				//Stop them from moving.
				event.setCancelled(true);
				player.setAllowFlight(true);
				player.setFlying(true);
			}
		}

		// For double jump.
		if (!getActivePlayers().containsKey(player)) {
			if (player.getWorld().getName().equals("world")) {
				if ((player.getGameMode() != GameMode.CREATIVE) && (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
					player.setAllowFlight(true);
				}
			}
		}
	}
}
