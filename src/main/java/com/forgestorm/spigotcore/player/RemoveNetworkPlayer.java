package com.forgestorm.spigotcore.player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.database.MongoDatabaseManager;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import com.forgestorm.spigotcore.help.AnimatedTutorial;
import com.forgestorm.spigotcore.help.LocationTrackingManager;
import com.forgestorm.spigotcore.realm.RealmManager;
import org.bukkit.entity.Player;


public class RemoveNetworkPlayer {

    public RemoveNetworkPlayer(SpigotCore plugin, Player player, boolean isServerShuttingDown) {

        MongoDatabaseManager profileManager = plugin.getProfileManager();
        PlayerProfileData profile = plugin.getProfileManager().getProfile(player);
        AnimatedTutorial animatedTutorial = plugin.getAnimatedTutorial();
        LocationTrackingManager tracker = plugin.getLocationTrackingManager();
        RealmManager realmManager = plugin.getRealmManager();

        //Remove the player from the active animatedTutorial.
        if (animatedTutorial.getActivePlayers().containsKey(player)) {
            animatedTutorial.endTutorial(player, true);
        }

        //Remove Scoreboard
        plugin.getScoreboardManager().removePlayer(player);

        //Remove player from tracking list.
        tracker.removePlayer(player);

        if (profile == null) return;

        //Close profile
        profile.setCurrentMenu(null);

        // If the server is shutting down, we will NOT run the following code.
        if (isServerShuttingDown) return;

        profileManager.unloadProfile(player);

        //Remove them from a players realm.
        realmManager.getPlayerRealmData().remove(player);

        //Remove/unload player realm
        realmManager.removeLogoutPlayerRealm(player);
    }
}
