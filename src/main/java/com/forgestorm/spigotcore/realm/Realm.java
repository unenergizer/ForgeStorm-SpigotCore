package com.forgestorm.spigotcore.realm;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import com.forgestorm.spigotcore.util.display.BossBarAnnouncer;
import com.forgestorm.spigotcore.util.display.Hologram;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Realm {

    private final SpigotCore plugin;
    private final Player realmOwner;
    private BossBarAnnouncer bossBar = new BossBarAnnouncer("");
    private Hologram hologram = new Hologram();
    private Location portalOutsideLocation;
    private Location portalInsideLocation;
    private Block outsideBlockTop, outsideBlockBottom, insideBlockTop, insideBlockBottom;
    private String title;
    private List<Player> realmFriends = new ArrayList<>();
    private int timeTillpvpRenabled = -1;
    private int maxTimeTillPvpRenabled = 0;
    private int timeTillFlyingDisabled = -1;
    private int idleTime = 0;
    private int maxIdleTime = 60 * 5;
    private int tier = 1;
    private boolean isWorldLoaded;

    Realm(SpigotCore plugin, Player realmOwner, Location location) {
        this.plugin = plugin;
        this.realmOwner = realmOwner;
        this.isWorldLoaded = false;
        portalOutsideLocation = location;
        title = plugin.getProfileManager().getProfile(realmOwner).getRealmTitle();
    }

    /**
     * If toggled by the owner, the realm will become peaceful for a certain amount of time.
     *
     * @param owner The owner of the realm.
     */
    public void toggleOrbOfPeace(Player owner) {
        int tempTime = timeTillpvpRenabled;
        timeTillpvpRenabled += 60 * 30;
        maxTimeTillPvpRenabled += 60 * 30;
        owner.sendMessage("");
        if (tempTime < 0) {
            owner.sendMessage(ChatColor.GREEN + "Your realm is now peaceful!");
        } else {
            owner.sendMessage(ChatColor.GREEN + "You have added 30 minutes of peaceful time to your loadedRealms.");
        }

        //Change the hologram text outside of the realm
        hologram.getArmorStands().get(1).setCustomName(ChatColor.GREEN + "Peaceful");

        //Show all players the boss bar.
        for (Player player : getPlayerList()) {
            if (!bossBar.getBossBarViewers().contains(player)) bossBar.showBossBar(player);
        }
    }

    /**
     * If toggled by the realm owner, the owner will be able to fly. This is primarily used
     * for building complex structures inside the realm world.
     *
     * @param owner The owner of the realm.
     */
    public void toggleOrbOfFlight(Player owner) {
        int tempTime = timeTillFlyingDisabled;
        timeTillFlyingDisabled += 60 * 30;
        owner.sendMessage("");
        if (tempTime < 0) {
            owner.sendMessage(ChatColor.GREEN + "Flying is now enabled in your realm!");
        } else {
            owner.sendMessage(ChatColor.GREEN + "You have added 30 minutes of flying time to your realm.");
        }
        owner.setAllowFlight(true);
        owner.setFlying(true);
    }

    /**
     * This will add a friend to the owners realm. Doing so
     * will let them build inside the realm world.
     *
     * @param friend The person to add to the realm world.
     */
    public void addFriend(Player friend) {
        realmFriends.add(friend);
        realmOwner.sendMessage(ChatColor.GREEN + "You have added: " + friend.getName() + " to your realm!");
        friend.sendMessage(ChatColor.GREEN + "You have added to " + realmOwner.getName() + "'s realm!");
    }

    /**
     * This will remove a friend from the realm world. Doing so
     * will prevent them from building inside the realm.
     *
     * @param friend The person to remove from the realm world.
     */
    public void removeFriend(Player friend) {
        realmFriends.remove(friend);
        realmOwner.sendMessage(ChatColor.RED + "You have removed: " + friend.getName() + " from your realm!");
        friend.sendMessage(ChatColor.RED + "You have been removed from " + realmOwner.getName() + "'s realm!");
    }

    /**
     * Checks to see if the player is a friend inside the owners realm.
     *
     * @param player The player who we want to check.
     * @return True if the player is a friend, false otherwise.
     */
    public boolean isFriend(Player player) {
        return realmFriends.contains(player);
    }

    /**
     * Gets a list of players currently inside this realm.
     *
     * @return A list of players inside this realm.
     */
    List<Player> getPlayerList() {
        World world = Bukkit.getWorld(realmOwner.getUniqueId().toString());
        return world == null ? new ArrayList<>() : world.getPlayers();
    }

    /**
     * This will parse a location provided by the database.
     *
     * @param stringLocation A Minecraft world location in a string format.
     * @return A Minecraft world location.
     */
    private Location parseLocation(String stringLocation) {
        String[] locationXYZ = stringLocation.split("/");
        return new Location(Bukkit.getWorld(realmOwner.getUniqueId().toString()),
                Double.valueOf(locationXYZ[0]),
                Double.valueOf(locationXYZ[1]),
                Double.valueOf(locationXYZ[2]));
    }

    /**
     * This will open the players realm. Once finished the player will be able to enter
     * the realm for building or combat.
     *
     * @param player The owner of the realm we just opened.
     */
    void openRealm(Player player) {
        int x = (int) portalOutsideLocation.getX();
        int y = (int) portalOutsideLocation.getY();
        int z = (int) portalOutsideLocation.getZ();
        Material air = Material.AIR;

        Block hologramSpace = portalOutsideLocation.getWorld().getBlockAt(x, y + 3, z);
        outsideBlockTop = portalOutsideLocation.getWorld().getBlockAt(x, y + 2, z);
        outsideBlockBottom = portalOutsideLocation.getWorld().getBlockAt(x, y + 1, z);

        //Set portal block
        if (hologramSpace.getType().equals(air) && outsideBlockTop.getType().equals(air) && outsideBlockBottom.getType().equals(air)) {

            PlayerProfileData profileData = plugin.getProfileManager().getProfile(player);

            // Check to see if the player has a realm.
            if (profileData.isHasRealm()) {
                //Load world
                loadPlayerWorldViaFTP();
            } else {
                // The player does not have a realm. Let's give them the default one.
                //Copy default world from folder to main directory.
                new BukkitRunnable() {

                    @Override
                    public void run() {

                        //Copy the empty world directory.
                        try {
                            File emptyWorldDir = new File(plugin.getDataFolder(), "world/emptyworld");
                            FileUtils.copyDirectoryToDirectory(emptyWorldDir, Bukkit.getWorldContainer());

                            String worldName = player.getUniqueId().toString();
                            new File("emptyworld").renameTo(new File(worldName));

                            //Prepare for world load.
                            plugin.getSyncWorldLoader().addWorldName(worldName);

                            //Update profile
                            profileData.setHasRealm(true);

                        } catch (IOException exe) {
                            exe.printStackTrace();
                        }
                    }
                }.runTaskAsynchronously(plugin);
            }

            //Set realm tier.
            tier = profileData.getRealmTier();
        }
    }

    /**
     * This will begin the process of removing the player world. We remove the portal
     * blocks on the outside and inside of the realm. Then we remove the holograms.
     * Afterwards we being the world unloading and then the FTP uploading.
     *
     * @param asyncUnload True if we want to upload the realm using async code.
     *                    We want to use async code to upload the realm while people
     *                    are playing on the server. However, if the server is shutting
     *                    down, we want to upload the realm using sync code to ensure
     *                    that the realm has been uploaded properly.
     */
    void closeRealm(boolean asyncUnload) {
        if (!worldExists()) return;

        // Kick realm players
        for (Player player : getPlayerList()) {
            plugin.getRealmManager().playerExitRealm(player);
        }

        //Remove Blocks
        outsideBlockTop.setType(Material.AIR);
        outsideBlockBottom.setType(Material.AIR);
        insideBlockTop.setType(Material.AIR);
        insideBlockBottom.setType(Material.AIR);

        //Remove Holograms
        hologram.removeHolograms();

        // Save and unload the world
        plugin.getSyncWorldUnloader().unloadWorld(realmOwner.getUniqueId().toString(), asyncUnload);
    }

    /**
     * Gets if the world is loaded or not.
     *
     * @return True if the world is loaded, false otherwise.
     */
    private boolean worldExists() {
        for (World world : Bukkit.getWorlds()) {
            if (world.getName().equals(realmOwner.getUniqueId().toString())) return true;
        }
        return false;
    }

    /**
     * This will toggle the downloading of the players realm from the FTP.
     */
    public void loadPlayerWorldViaFTP() {
        String name = realmOwner.getUniqueId().toString();
        plugin.getAsyncRealmDownloadFTP().downloadWorld(name);
    }

    /**
     * This will set the portal inside the players realm.  This is mainly called
     * when the player relocates their realm portal location.
     *
     * @param newLocation The new location to set the inside portal.
     */
    public void setPlayerInsidePortal(boolean newLocation) {
        if (!newLocation)
            portalInsideLocation = parseLocation(plugin.getProfileManager().getProfile(realmOwner).getRealmInsideLocation());
        int x = (int) portalInsideLocation.getX();
        int y = (int) portalInsideLocation.getY();
        int z = (int) portalInsideLocation.getZ();

        insideBlockTop = portalInsideLocation.getWorld().getBlockAt(x, y + 1, z);
        insideBlockBottom = portalInsideLocation.getWorld().getBlockAt(x, y, z);

        insideBlockTop.setType(Material.PORTAL);
        insideBlockBottom.setType(Material.PORTAL);
    }

    /**
     * This sets the portal in the main world. Used by players to enter this realm.
     */
    public void setOutsideRealmPortal() {
        int x = (int) portalOutsideLocation.getX();
        int y = (int) portalOutsideLocation.getY();
        int z = (int) portalOutsideLocation.getZ();
        Material air = Material.AIR;

        Block hologramSpace = portalOutsideLocation.getWorld().getBlockAt(x, y + 3, z);
        outsideBlockTop = portalOutsideLocation.getWorld().getBlockAt(x, y + 2, z);
        outsideBlockBottom = portalOutsideLocation.getWorld().getBlockAt(x, y + 1, z);

        //Set portal block
        if (hologramSpace.getType().equals(air) && outsideBlockTop.getType().equals(air) && outsideBlockBottom.getType().equals(air)) {
            outsideBlockTop.setType(Material.PORTAL);
            outsideBlockBottom.setType(Material.PORTAL);

            //Set title and realm status.
            Location hologramLocation = new Location(portalOutsideLocation.getWorld(), x + .5, y + 2.1, z + .5);
            ArrayList<String> hologramText = new ArrayList<>();
            hologramText.add(realmOwner.getName());
            hologramText.add(ChatColor.RED + "Hostile");
            hologram.createHologram(hologramText, hologramLocation);
        }
    }
}
