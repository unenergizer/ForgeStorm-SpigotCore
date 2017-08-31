package com.forgestorm.spigotcore.player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.constants.SpigotCoreMessages;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import com.forgestorm.spigotcore.database.ProfileLoadedEvent;
import com.forgestorm.spigotcore.experience.PlayerExperience;
import com.forgestorm.spigotcore.util.item.InventoryStringDeSerializer;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

public class SetupNetworkPlayer extends BukkitRunnable {

    private final SpigotCore plugin;
    @Getter
    private final Queue<PlayerProfileData> loadingPlayers = new ConcurrentLinkedDeque<>();
    private final PlayerExperience expCalc = new PlayerExperience();

    public SetupNetworkPlayer(SpigotCore plugin) {
        this.plugin = plugin;
    }

    public void run() {
        // If the queue is not empty, load the player. Return otherwise.
        if (loadingPlayers.isEmpty()) return;

        // Setup the player.
        setupPlayer(loadingPlayers.remove());
    }

    /**
     * This will setup a player for the network.
     *
     * @param profileData Takes the profile data generated for this user.
     */
    private void setupPlayer(PlayerProfileData profileData) {
        Player player = profileData.getPlayer();

        //Additional profileData setup.
        profileData.setOperatorRank();
        profileData.setChatPrefix();
        profileData.setPlayerLevel(expCalc.getLevel(profileData.getExperience()));

        //Send the player a formatted tab menu.
        String header = SpigotCoreMessages.DISPLAY_TAB_HEADER.toString().replace("%s", player.getName());
        String footer = SpigotCoreMessages.DISPLAY_TAB_FOOTER.toString();
        plugin.getTitleManagerAPI().setHeaderAndFooter(player, header, footer);

        //If the player is on a mount. Dismount them, and remove the mount.
        if (player.isInsideVehicle()) {
            Entity mount = player.getVehicle();
            mount.eject();
            mount.remove();
        }

        //Deserialize player inventory.
        player.getInventory().clear();
        if (profileData.getSerializedInventory() != null && !profileData.getSerializedInventory().equals("")) {
            try {
                //System.out.println("LOAD InvData: " + profileData.getSerializedInventory());
                ItemStack[] inventory = InventoryStringDeSerializer.stacksFromBase64(profileData.getSerializedInventory());
                player.getInventory().setContents(inventory);
            } catch (IllegalArgumentException | IOException e) {
                e.printStackTrace();
            }
        }

        // Set the player compass menu, just encase it gets deleted.
        player.getInventory().setItem(0, plugin.getItemGen().generateItem("main_menu", ItemTypes.MENU));

        // Trigger profile loaded event
        ProfileLoadedEvent profileLoadedEvent = new ProfileLoadedEvent(player);
        Bukkit.getPluginManager().callEvent(profileLoadedEvent);
    }
}
