package com.forgestorm.spigotcore.player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.constants.SpigotCoreMessages;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import com.forgestorm.spigotcore.database.ProfileLoadedEvent;
import com.forgestorm.spigotcore.experience.PlayerExperience;
import com.forgestorm.spigotcore.util.item.InventoryStringDeSerializer;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

        // Trigger profile loaded event
        ProfileLoadedEvent profileLoadedEvent = new ProfileLoadedEvent(player);
        Bukkit.getPluginManager().callEvent(profileLoadedEvent);

        //Additional profileData setup.
        profileData.setOperatorRank();
        profileData.setChatPrefix();
        profileData.setPlayerLevel(expCalc.getLevel(profileData.getExperience()));

        //Setup usergroup prefixes.
        plugin.getScoreboardManager().assignPlayer(player);

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

        //Reset the players compass.
        player.setCompassTarget(new Location(Bukkit.getWorlds().get(0), 0.5, 108, -24.5));

        //Put the player in adventure mode.
        player.setGameMode(GameMode.SURVIVAL);

        //Set player health and food level.
        player.setHealth(20);
        player.setFoodLevel(20);

        //Give player starting items.
        if (profileData.getSerializedInventory().equals("")) {
            giveStartingItems(player);
        }
    }

    private void giveStartingItems(final Player player) {
        ItemGenerator itemGen = plugin.getItemGen();
        List<ItemStack> beginnerItems = new ArrayList<>();
        int timePerItem = 1;
        int delayTime = 3;

        //Generate Items
        beginnerItems.add(itemGen.generateItem("main_menu", ItemTypes.MENU));
        beginnerItems.add(itemGen.generateItem("beginner_boots", ItemTypes.ARMOR));
        beginnerItems.add(itemGen.generateItem("beginner_leggings", ItemTypes.ARMOR));
        beginnerItems.add(itemGen.generateItem("beginner_chestplate", ItemTypes.ARMOR));
        beginnerItems.add(itemGen.generateItem("beginner_helmet", ItemTypes.ARMOR));
        beginnerItems.add(itemGen.generateItem("beginner_sword", ItemTypes.WEAPON));
        beginnerItems.add(itemGen.generateItem("beginner_bread", ItemTypes.FOOD, 6));

        //Set Items
        new BukkitRunnable() {

            private int i;

            @Override
            public void run() {
                Material material = beginnerItems.get(i).getType();
                player.getInventory().setItem(i, beginnerItems.get(i));

                if (material == Material.BREAD) {
                    player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_EAT, 1, .6f);
                } else {
                    player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 1, .6f);
                }

                i++;
                if (i == beginnerItems.size()) cancel();
            }
        }.runTaskTimer(plugin, 20 * delayTime, 20 * timePerItem);
    }
}
