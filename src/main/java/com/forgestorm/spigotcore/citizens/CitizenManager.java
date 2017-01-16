package com.forgestorm.spigotcore.citizens;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.CitizenType;
import com.forgestorm.spigotcore.constants.FilePaths;
import com.forgestorm.spigotcore.constants.Messages;
import com.forgestorm.spigotcore.menus.GameSelectionMenu;
import com.forgestorm.spigotcore.menus.help.LinksMenu;
import com.forgestorm.spigotcore.menus.help.TutorialMenu;
import com.forgestorm.spigotcore.menus.merchant.BartenderMenu;
import com.forgestorm.spigotcore.menus.merchant.BoatMenu;
import com.forgestorm.spigotcore.menus.profession.FarmingTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.FishingTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.LumberjackTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.MiningTrainerMenu;
import com.forgestorm.spigotcore.util.display.Hologram;
import com.forgestorm.spigotcore.util.item.NPCSkullBuilder;
import com.forgestorm.spigotcore.util.math.RandomChance;
import com.forgestorm.spigotcore.util.text.CenterChatText;

import lombok.Getter;

/**
 * This class manages what happens when a NPC is clicked.
 * It also puts a hologram above the NPC's head.
 */
public class CitizenManager extends BukkitRunnable {

	private final SpigotCore PLUGIN;

	private File file;
	private FileConfiguration config;
	private String filePath;

	private final int MAX_FRAMES = 4;
	private int frame = 0;

	//Holograms
	private ArrayList<Hologram> holograms = new ArrayList<>();
	private ArrayList<Hologram> animatedHologram = new ArrayList<>();
	private ArrayList<Hologram> animatedHologramRightClick = new ArrayList<>();
	
	@Getter
	private Map<Location, ItemStack> npcMenuLocations = new HashMap<>();

	public CitizenManager(SpigotCore plugin) {
		PLUGIN = plugin;

		filePath = FilePaths.CITIZENS.toString();

		//If citizens configuration does not exist, create the file. Otherwise lets load the file.
		if(!(new File(filePath)).exists()){
			createCitizenConfig();
		} else {
			//lets load the configuration file.
			file = new File(filePath);
			config =  YamlConfiguration.loadConfiguration(file);
		}

		//Apply Citizens Configuration after server startup.
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin,  new Runnable() {
			public void run() {
				applyCitizensConfiguration();
				//System.out.println("[FSCore] Applied Citizens HP and Holograms.");
			}
		}, 5 * 20L);

	}

	/**
	 * This will disable this class.
	 */
	public void disable() {
		//Cancel the animate thread.
		cancel();

		//Removes all Holograms
		removeStaticHolograms();
		removeAnimatedHolograms();
	}

	@Override
	public void run() {
		if (frame > MAX_FRAMES) {
			frame = 0;
		}

		//Rename holograms
		for (int i = 0; i < animatedHologram.size(); i++) {
			String hologramText = "";

			if (frame == 0) {
				hologramText = CitizenType.PLAY_MINIGAMES.getName(ChatColor.RED);
			} else if (frame == 1) {
				hologramText = CitizenType.PLAY_MINIGAMES.getName(ChatColor.YELLOW);
			} else if (frame == 2) {
				hologramText = CitizenType.PLAY_MINIGAMES.getName(ChatColor.GREEN);
			} else if (frame == 3) {
				hologramText = CitizenType.PLAY_MINIGAMES.getName(ChatColor.AQUA);
			} else if (frame == 4) {
				hologramText = CitizenType.PLAY_MINIGAMES.getName(ChatColor.LIGHT_PURPLE);
			}

			//Rename the hologram.
			animatedHologram.get(i).getArmorStands().get(0).setCustomName(hologramText);
		}

		frame++;
	}

	/**
	 * This will apply HP to a NPC.
	 * The HP is displayed under the NPC's name.
	 */
	private void applyCitizensConfiguration() {

		//Get all online players.
		for (Entity npc : Bukkit.getWorlds().get(0).getEntities()) {

			//Get all Citizen NPC's.
			if (npc instanceof Player && npc.hasMetadata("NPC")) {
				String npcName = ((Player) npc).getDisplayName();
				CitizenType type = getCitizenType(npcName);

				if (type != CitizenType.NONE) {
					if (type.equals(CitizenType.PLAY_MINIGAMES)) {
						Location location = npc.getLocation().add(0, 1.65, 0);
						String name = getCitizenType(npcName).getName();
						String rightClick = ChatColor.BOLD + "RIGHT-CLICK";

						//Setup NPC Hologram
						Hologram hologram = new Hologram();
						hologram.createHologram(name, location);
						animatedHologram.add(hologram);

						//Add a static, non-flickering Right-Click hologram.
						Hologram hologramRC = new Hologram();
						hologramRC.createHologram(rightClick, new Location(location.getWorld(), location.getX(), location.getY() - .3, location.getZ()));
						animatedHologramRightClick.add(hologramRC);
					} else {					
						ArrayList<String> hologramText = new ArrayList<String>();
						hologramText.add(getCitizenType(npcName).getName());
						hologramText.add(ChatColor.BOLD + "RIGHT-CLICK");
						
						//Build NPC Head
						ItemStack skull = new NPCSkullBuilder().createPlayerSkullItem(npcName, type);
						
						//Add NPC Tracker Menu Items
						Location location = npc.getLocation().add(0, 1.65, 0);
						npcMenuLocations.put(location, skull);
						
						Location hologramLocation = npc.getLocation().add(0, 1.65, 0);

						//Setup NPC Hologram
						Hologram hologram = new Hologram();
						hologram.createHologram(hologramText, hologramLocation);
						//Add hologram to the array list.
						holograms.add(hologram);
					}
				}
			}
		}
	}

	private void removeStaticHolograms() {
		//Remove static holograms.
		for (int i = 0; i < holograms.size(); i++) {
			holograms.get(i).removeHolograms();
		}
	}

	private void removeAnimatedHolograms() {
		//Remove animated holograms.
		for (int i = 0; i < animatedHologram.size(); i++) {
			animatedHologram.get(i).removeHolograms();
		}

		//Remove animated holograms.
		for (int i = 0; i < animatedHologramRightClick.size(); i++) {
			animatedHologramRightClick.get(i).removeHolograms();
		}
	}

	/**
	 * This will trigger when an NPC is left clicked or right clicked.
	 * 
	 * @param player The player that interacted with the NPC.
	 * @param npc The Citizen (created by the Citizen plugin) that was clicked.
	 */
	@SuppressWarnings("deprecation")
	public void onCitizenInteract(Player player, Player npc) {
		String npcName = npc.getDisplayName();
		CitizenType type = getCitizenType(npcName);
		Location loc = npc.getLocation().add(0, 2, 0);
		int rand = RandomChance.randomInt(1, 100);

		//Trigger code in appropriate Java Class file.
		switch(type) {
		case AUCTIONEER:
			break;
		case BANKER:
			break;
		case BARTENDER:
			new BartenderMenu(PLUGIN).open(player);
			break;
		case DIRTY_HOBO:
			break;
		case MERCHANT:
			player.sendMessage(ChatColor.GREEN + "Merchant coming soon!");
			break;
		case MERCHANT_BOAT:
			new BoatMenu(PLUGIN).open(player);
			break;
		case NONE:
			player.sendMessage(ChatColor.GREEN + "...");
			break;
		case PLAY_MINIGAMES:
			new GameSelectionMenu(PLUGIN).open(player);
			break;
		case PROFESSION_FARMING:
			new FarmingTrainerMenu(PLUGIN).open(player);
			break;
		case PROFESSION_FISHING:
			new FishingTrainerMenu(PLUGIN).open(player);
			break;
		case PROFESSION_LUMBERJACK:
			new LumberjackTrainerMenu(PLUGIN).open(player);
			break;
		case PROFESSION_MINING:
			new MiningTrainerMenu(PLUGIN, player, true).open(player);
			break;
		case SOCIAL_MEDIA:
			new LinksMenu(PLUGIN).open(player);
			break;
		case TEAMSPEAK:
			player.sendMessage("");
			player.sendMessage("");
			player.sendMessage(Messages.BAR_TEAMSPEAK.toString());
			player.sendMessage("");
			player.sendMessage(CenterChatText.centerChatMessage(Messages.TEAMSPEAK_INFO_1.toString()));
			player.sendMessage(CenterChatText.centerChatMessage(Messages.TEAMSPEAK_INFO_2.toString()));
			player.sendMessage("");
			player.sendMessage(CenterChatText.centerChatMessage(Messages.TEAMSPEAK_INFO_3.toString()));
			player.sendMessage("");
			player.sendMessage(Messages.BAR_BOTTOM.toString());
			break;
		case TUTORIAL:
			new TutorialMenu(PLUGIN).open(player);
			break;
		case VOTE:
			break;
		}

		//Send a random saying to the player.
		String npcMessage = getNPCMessage(npcName);
		if (npcMessage != null) {
			String fullMsg = ChatColor.GRAY + npcName + ChatColor.DARK_GRAY + ": " + ChatColor.RESET + npcMessage;
			player.sendMessage(fullMsg);
		}

		//Show particle effect.
		for (int i = 0; i <= 5; i++) {
			Bukkit.getWorlds().get(0).spigot().playEffect(loc, Effect.HAPPY_VILLAGER);
		}

		//Player sound.
		if (rand < 25) {
			player.playSound(loc, Sound.ENTITY_VILLAGER_TRADING, .8f, .8f);
		} else if (rand >= 25 && rand < 50) {
			player.playSound(loc, Sound.ENTITY_VILLAGER_AMBIENT, .8f, .8f);
		} else if (rand >= 50 && rand < 75) {
			player.playSound(loc, Sound.ENTITY_VILLAGER_NO, .8f, .8f);
		} else if (rand >= 75) {
			player.playSound(loc, Sound.ENTITY_VILLAGER_YES, .8f, .8f);
		}
	}

	private String getNPCMessage(String npcName) {
		String nameFix = npcName.replace(" ", "_");
		String prefix = nameFix + ".chat.";

		if (config.contains(prefix + "0")) {
			int lines = config.getConfigurationSection(prefix).getKeys(false).size();
			int rand = RandomChance.randomInt(1,lines) - 1;
		
			return ChatColor.translateAlternateColorCodes('&', config.getString(prefix + Integer.toString(rand)));
		} else {
			return null;
		}
	}

	/**
	 * Creates a new configuration file on a players first visit to the server.
	 */
	private void createCitizenConfig() {

		file = new File(filePath);
		config =  YamlConfiguration.loadConfiguration(file);
		config.set("Tutorial", "Tutorial");

		//Types: ItemIdentifier, Merchant, Blacksmith, Alchemist, Quest, None
		config.set("Tutorial.type", "Tutorial");

		//Spawn location (used for Hologram
		config.set("Tutorial.spawn.x", 28.5);
		config.set("Tutorial.spawn.y", 79);
		config.set("Tutorial.spawn.z", -13.5);

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	private CitizenType getCitizenType(String citizenName) {
		String npc = citizenName.replace(" ", "_");
		String type = config.getString(npc + ".type");

		if (type == null) {
			//Will leave null to report any errors if necessary.
			//System.out.println("[FSCore] NPC: " + npc + " does not exist in config.");
			return CitizenType.NONE;
		} else {
			return CitizenType.valueOf(type);
		}
	}
}
