package com.forgestorm.spigotcore.player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.FilePaths;
import com.forgestorm.spigotcore.util.display.AnimatedBossBarAnnouncer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BossBarAnnouncer {

	private final SpigotCore plugin;

	private String filePath;

	//Configuration file.
	private File configFile;
	private FileConfiguration gameConfig;
	private AnimatedBossBarAnnouncer bar;

	private List<String> announcements;

	public BossBarAnnouncer(SpigotCore plugin) {
		this.plugin = plugin;
		filePath = FilePaths.BOSS_BAR_ANNOUNCEMENTS.toString();
		
		//If fishing configuration does not exist, create it. Otherwise lets load the config.
		if(!(new File(filePath)).exists()){
			createConfig();
		} else {
			//lets load the configuration file.
			configFile = new File(filePath);
			gameConfig =  YamlConfiguration.loadConfiguration(configFile);
		}

		setupBossBar();
	}

	private void setupBossBar() {
		//Load messages form configuration.
		loadMessages();
		
		bar = new AnimatedBossBarAnnouncer("", plugin);
		bar.addMessages(announcements);
		bar.start();
	}

	public void disable() {
		bar.cancel();
		bar.removeAllBossBar();
	}

	/**
	 * TODO:  Load all messages from configuration file.
	 * 
	 * @return A list of all tips (messages) in the configuration file.
	 */
	private void loadMessages() {
		configFile = new File(filePath);
		gameConfig =  YamlConfiguration.loadConfiguration(configFile);
		announcements = gameConfig.getStringList("AnnounceText");
	}

	/**
	 * This creates the configuration file that has the EXP leveling requirements.
	 */
	private void createConfig() {

		configFile = new File(filePath);
		gameConfig =  YamlConfiguration.loadConfiguration(configFile);

		//Loop through and create each level for fishing.
		gameConfig.set("AnnounceText", "AnnounceText");
		gameConfig.set("AnnounceText", "This is a test message.");

		try {
			gameConfig.save(configFile);	//Save the file.
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public AnimatedBossBarAnnouncer getBar() {
		return bar;
	}

}
