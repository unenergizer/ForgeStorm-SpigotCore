package com.forgestorm.spigotcore.util;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.FilePaths;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class Configuration {

    private final File SETTINGS_FILE;
    private final YamlConfiguration SETTINGS_CONFIG;

    public Configuration(SpigotCore plugin) {
        this.SETTINGS_FILE = new File(FilePaths.SETTINGS.toString());
        this.SETTINGS_CONFIG = YamlConfiguration.loadConfiguration(SETTINGS_FILE);

        if (!SETTINGS_CONFIG.isConfigurationSection("Database")) {
            SETTINGS_CONFIG.set("Database.Address", "localhost:3306");
            SETTINGS_CONFIG.set("Database.Schema", "example");
            SETTINGS_CONFIG.set("Database.Username", "root");
            SETTINGS_CONFIG.set("Database.Password", "root");
            saveSettingsConfig();
        }
    }

    public YamlConfiguration getSettingsConfig() {
        return SETTINGS_CONFIG;
    }

    public void saveSettingsConfig() {
        try {
            SETTINGS_CONFIG.save(SETTINGS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Location getSpawn() {
        String[] parts = SETTINGS_CONFIG.getString("Spawn").split(" ");
        return new Location(Bukkit.getWorlds().get(0), Double.valueOf(parts[0]), Double.valueOf(parts[1]),
                Double.valueOf(parts[2]), Float.valueOf(parts[3]), Float.valueOf(parts[4]));
    }

    public void setSpawn(Player player) {
        SETTINGS_CONFIG.set("Spawn", serializeLocation(player.getLocation()));
    }

    public String serializeLocation(Location location) {
        return location.getX() + " " + location.getY() + " " + location.getZ() + " " + location.getYaw() + " " + location.getPitch();
    }

}
