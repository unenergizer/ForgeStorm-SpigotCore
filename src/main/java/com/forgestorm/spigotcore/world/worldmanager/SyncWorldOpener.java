package com.forgestorm.spigotcore.world.worldmanager;

import com.forgestorm.spigotcore.SpigotCore;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SyncWorldOpener extends BukkitRunnable {

    private final SpigotCore plugin;
    private final int capacity = 20;
    private BlockingQueue<String> worldLoadQueue; //world names

    public SyncWorldOpener(SpigotCore plugin) {
        this.plugin = plugin;
        worldLoadQueue = new ArrayBlockingQueue<>(capacity);
    }

    /**
     * This will add a world to the queue to be loaded.
     *
     * @param worldName The name of the world to load.
     * @return True if we added a world to the queue. False if queue is full.
     */
    public boolean addWorld(String worldName) {
        if (worldLoadQueue.size() < capacity) {
            worldLoadQueue.add(worldName);
            return true;
        }
        return false; //queue full
    }

    /**
     * Here we are checking if entries exist in our queue.
     * If we have entries exist, we will load them one at a time.
     */
    public void run() {

        //Load any pending worlds.
        if (!worldLoadQueue.isEmpty()) {

            //This will load the world and remove the
            //entry from the blocking queue.
            Bukkit.getWorld(worldLoadQueue.remove());
        }
    }
}

