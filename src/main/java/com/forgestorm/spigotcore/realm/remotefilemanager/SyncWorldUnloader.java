package com.forgestorm.spigotcore.realm.remotefilemanager;

import com.forgestorm.spigotcore.SpigotCore;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by unene on 2/6/2017.
 */
public class SyncWorldUnloader extends BukkitRunnable {

    private final SpigotCore plugin;
    private final Queue<String> worldsToUnload = new ConcurrentLinkedQueue<>();//<String> worldName/playerUUID
    private String currentUuid;
    private boolean stillProcessing = false;

    public SyncWorldUnloader(SpigotCore plugin) {
        this.plugin = plugin;
    }

    /**
     * This will add an entry to the world loading queue.
     *
     * @param worldNameToAdd The name of the world we want to load.
     * @return True if world loading was a success. False otherwise.
     */
    public void unloadWorld(String worldNameToAdd, boolean asyncFTP) {
        if (asyncFTP) {
            System.out.println("[REALM] World added to SyncWorldUnloader: " + worldNameToAdd);
            worldsToUnload.add(worldNameToAdd);
        } else {
            int i = 0;
            while (!Bukkit.unloadWorld(worldNameToAdd, true)) {
                i++;
            }
            System.out.println("[REALM] ForceClosing! Iterations waited: " + i);
            plugin.getAsyncRealmUploadFTP().uploadWorld(worldNameToAdd, false);
        }
    }

    @Override
    public void run() {

        if (stillProcessing) {
            if (Bukkit.unloadWorld(currentUuid, true)) {
                stillProcessing = false;

                //Save to FTP
                plugin.getAsyncRealmUploadFTP().uploadWorld(currentUuid, true);
            }
        } else {
            // Grab the next world name (player uuid) to begin the world unloading
            // process.
            if (worldsToUnload.isEmpty()) return;
            currentUuid = worldsToUnload.remove();
            stillProcessing = true;
        }
    }
}
