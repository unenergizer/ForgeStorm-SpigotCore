package com.forgestorm.spigotcore.world;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.FilePaths;
import com.forgestorm.spigotcore.util.display.Hologram;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manages what happens when a NPC is clicked.
 * It also puts a hologram above the NPC's head.
 */
public class BlockHolograms {

    private final static String prefix = "Holograms";
    private final SpigotCore plugin;
    private final String filePath;
    //Holograms
    private final ArrayList<HologramInfo> hologramInfos = new ArrayList<>();
    private final ArrayList<Hologram> holograms = new ArrayList<>();
    private final World world;
    private File file;
    private FileConfiguration config;
    private Location location;


    public BlockHolograms(SpigotCore plugin) {
        this.plugin = plugin;

        // Set World
        world = Bukkit.getWorlds().get(0);

        filePath = FilePaths.BLOCK_HOLOGRAMS.toString();
        file = new File(filePath);
        config = YamlConfiguration.loadConfiguration(file);
        loadHolograms();

        //Apply Citizens Configuration after server startup.
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            applyBlockHolograms();
            System.out.println("[FSCore] Applied Block Holograms.");
        }, 4 * 20L);

    }

    /**
     * This will disable this class.
     */
    public void onDisable() {
        removeHolograms();
    }

    /**
     * This will apply a Hologram over a block.
     */
    private void applyBlockHolograms() {

        for (HologramInfo hologramInfo : hologramInfos) {
            Location location = new Location(world, hologramInfo.getX(), hologramInfo.getY(), hologramInfo.getZ());
            Location hologramLocation;

            if (hologramInfo.isCenterOnBlock()) {
                hologramLocation = location.add(.5, .65, .5);
            } else {
                hologramLocation = location.add(0, .65, 0);
            }

            //Setup NPC Hologram
            Hologram hologram = new Hologram();
            hologram.createHologram(hologramInfo.getHologramText(), hologramLocation);
            holograms.add(hologram);
        }
    }

    private void removeHolograms() {
        for (Hologram hologram : holograms) {
            hologram.removeHolograms();
        }
    }

    private void loadHolograms() {
        int totalHolograms = 0;
        for (String entry : config.getConfigurationSection(prefix).getKeys(false)) {

            double x = config.getDouble(prefix + "." + entry + ".x");
            double y = config.getDouble(prefix + "." + entry + ".y");
            double z = config.getDouble(prefix + "." + entry + ".z");

            List<String> text = (List<String>) config.getList(prefix + "." + entry + ".text");

            boolean centerOnBlock = config.getBoolean(prefix + "." + entry + ".centerOnBlock");

            // Save for later
            hologramInfos.add(new HologramInfo(x, y, z, text, centerOnBlock));
            totalHolograms++;
        }
        System.out.println("[FS-CORE] Loaded " + totalHolograms + " block hologramInfos.");
    }

    /**
     * This is the HologramInfo class that holds data pertaining to block hologramInfos.
     */
    @Getter
    @Setter
    @AllArgsConstructor
    private class HologramInfo {
        private final double x;
        private final double y;
        private final double z;
        private final List<String> hologramText;
        private final boolean centerOnBlock;
    }
}
