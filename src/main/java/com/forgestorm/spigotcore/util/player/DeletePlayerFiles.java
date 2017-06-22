package com.forgestorm.spigotcore.util.player;

import com.forgestorm.spigotcore.SpigotCore;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

@AllArgsConstructor
public class DeletePlayerFiles {

    private final SpigotCore plugin;
    private final boolean debugText = false;

    /**
     * Deletes the specified player's save files. This does not delete all files
     * in the directory.
     *
     * @param player The player that we will delete world files for.
     */
    public void deleteSaveFiles(Player player) {
        String name = player.getName();
        String uuid = player.getUniqueId().toString();

        new BukkitRunnable() {

            @Override
            public void run() {
                //Delete player data in all worlds.
                for (World worlds : Bukkit.getWorlds()) {

                    showDebugText(" ->> Player Logging out. Removing player world data...");
                    showDebugText(" - Player: " + name);
                    showDebugText(" - uuid: " + uuid);

                    String folderPath = Bukkit.getWorld(worlds.getName()).getWorldFolder().getAbsolutePath();

                    //Delete Player Data Files
                    if (plugin.getConfig().getBoolean("PlayerFiles.deleteData")) {
                        String filePathData = folderPath + "/playerdata";

                        showDebugText(filePathData + "/" + uuid + ".dat");

                        deleteFile(new File(filePathData + "/" + uuid + ".dat"));
                    }

                    //Delete Player Stat Files
                    if (plugin.getConfig().getBoolean("PlayerFiles.deleteStats")) {
                        String filePathStats = folderPath + "/stats";

                        showDebugText(filePathStats + "/" + uuid + ".json");

                        deleteFile(new File(filePathStats + "/" + uuid + ".json"));
                    }
                    showDebugText(" - Done");
                }
            }
        }.runTaskLaterAsynchronously(plugin, 0);
    }

    /**
     * This will delete all the player world data files found on the server.
     */
    public void deleteSaveDirectory() {
        new BukkitRunnable() {

            @Override
            public void run() {
                //Delete player data in all worlds.
                for (World worlds : Bukkit.getWorlds()) {

                    showDebugText("Shutting Down.....");
                    showDebugText("Removing Player Data Files.....");

                    String folderPath = Bukkit.getWorld(worlds.getName()).getWorldFolder().getAbsolutePath();
                    String filePathData = folderPath + "/playerdata";
                    String filePathStats = folderPath + "/stats";

                    //Delete Player Data and Stats
                    deleteDir(new File(filePathData));
                    deleteDir(new File(filePathStats));

                    showDebugText("Files deleted.");

                    new File(filePathData).mkdir();
                    new File(filePathStats).mkdir();

                    showDebugText("Folders restored.");
                }
            }
        }.runTaskLaterAsynchronously(plugin, 0);
    }

    private void deleteFile(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    private void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();
    }

    private void showDebugText(String message) {
        if (debugText) System.out.println(message);
    }
}
