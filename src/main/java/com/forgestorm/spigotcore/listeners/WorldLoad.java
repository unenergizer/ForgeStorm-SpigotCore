package com.forgestorm.spigotcore.listeners;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.realm.Realm;
import lombok.AllArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by unene on 2/7/2017.
 */
@AllArgsConstructor
public class WorldLoad implements Listener {

    private final SpigotCore plugin;

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        String worldName = event.getWorld().getName();

        //If the worldname length is equal to 36 we can assume it is a player realm.
        //The player loadedRealms world names are the players uuid.
        //So Player UUID = Player Realm World Name (and vise versa)
        if (worldName.length() == 36) {
            //Set the inside portal blocks for this realm.
            new BukkitRunnable() {

                @Override
                public void run() {
                    cancel();
                    Realm realm = plugin.getRealmManager().getPlayerRealm(worldName);
                    realm.setPlayerInsidePortal(false);
                    realm.setOutsideRealmPortal();
                    realm.setWorldLoaded(true);
                }

            }.runTaskLater(plugin, 20);
        }
    }
}