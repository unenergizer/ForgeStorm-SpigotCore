package com.forgestorm.spigotcore.listeners;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.menus.MainMenu;
import com.forgestorm.spigotcore.realm.Realm;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerInteract implements Listener {

    private final SpigotCore plugin;
    private final ResetTimer resetTimer;

    public PlayerInteract(SpigotCore plugin) {
        this.plugin = plugin;
        resetTimer = new ResetTimer();
        resetTimer.runTaskTimerAsynchronously(plugin, 0, 1);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        Block block = event.getClickedBlock();

        //Use reset timer to prevent accidental double click.
        if (!resetTimer.containsPlayer(player)) {
            resetTimer.addPlayer(player);

            //Player to place realm portal (only if they are in the main world).
            if (player.isSneaking() && player.getWorld().equals(Bukkit.getWorlds().get(0))) {
                if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    if (event.getItem() != null && event.getItem().getType().equals(Material.COMPASS)) {
                        plugin.getRealmManager().loadRealm(player, block.getLocation());
                        return;
                    }
                }
            }

            //INSIDE REALM ACTIONS
            if (player.getWorld().getName().equals(player.getUniqueId().toString())) {

                if (event.getAction().equals(Action.RIGHT_CLICK_AIR) ||
                        event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    ItemStack itemStack = player.getInventory().getItemInMainHand();
                    int amount = itemStack.getAmount();

                    //Orb of peace
                    if (event.getItem() != null && event.getItem().getType().equals(Material.ENDER_PEARL)) {

                        //Consume used item.
                        if (amount > 1) {
                            itemStack.setAmount(amount - 1);
                        } else {
                            player.getInventory().setItemInMainHand(null);
                        }
                        plugin.getRealmManager().getPlayerRealm(uuid).toggleOrbOfPeace(player);
                        return;
                    }

                    //Orb of flight
                    if (event.getItem() != null && event.getItem().getType().equals(Material.SLIME_BALL)) {
                        //Consume used item.
                        if (amount > 1) {
                            itemStack.setAmount(amount - 1);
                        } else {
                            player.getInventory().setItemInMainHand(null);
                        }
                        plugin.getRealmManager().getPlayerRealm(uuid).toggleOrbOfFlight(player);
                        return;
                    }
                }

                //Check for realm portal move.
                if (player.isSneaking() && event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    if (block.getType() != Material.PORTAL) {
                        if (event.getItem() != null && event.getItem().getType().equals(Material.COMPASS)) {
                            Realm realm = plugin.getRealmManager().getPlayerRealm(player);

                            //Remove Old Realm inside Portal
                            realm.getInsideBlockTop().setType(Material.AIR);
                            realm.getInsideBlockBottom().setType(Material.AIR);

                            //Set New Realm inside Portal
                            realm.setPortalInsideLocation(block.getLocation().add(0, 1, 0));
                            realm.setPlayerInsidePortal(true);

                            //Save location to player profile.
                            double x = realm.getPortalInsideLocation().getX();
                            double y = realm.getPortalInsideLocation().getY();
                            double z = realm.getPortalInsideLocation().getZ();
                            plugin.getProfileManager().getProfile(player).setRealmInsideLocation(x + "/" + y + "/" + z);
                            return;
                        }
                    }
                }
            }
        }

        //Pressure plate actions.
        if (event.getAction().equals(Action.PHYSICAL)) {
            //The player triggered a physical interaction event

            //Cancel the sound of walking over this stone plate.
            if (block.getType().equals(Material.WOOD_PLATE)
                    || block.getType().equals(Material.STONE_PLATE)) {
                event.setCancelled(true);
            }

            //Launch pad
            if (block.getType().equals(Material.GOLD_PLATE)) {
                player.setVelocity(player.getLocation().getDirection().multiply(100));
                player.setVelocity(new Vector(player.getVelocity().getX(), 10.0D, player.getVelocity().getZ()));
            }
        }

        //Dragon Egg Teleport game.
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && block.getType().equals(Material.DRAGON_EGG)
                || event.getAction().equals(Action.LEFT_CLICK_BLOCK) && block.getType().equals(Material.DRAGON_EGG)) {

            //Cancel the default egg respawn.
            event.setCancelled(true);

            //Toggle the egg click.
            plugin.getDragonEggTP().toggleEggClick(player, event.getClickedBlock().getLocation());
        }

        //Armor and Weapon Actions
        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK) ||
                event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (event.getItem() != null) {

                switch (event.getItem().getType()) {

                    ///////////////
                    // Craftable //
                    ///////////////
                    case ANVIL:
                        //Disable item renaming.
                        event.setCancelled(true);
                        break;
                    case COMPASS:
                        //Open players main menu.
                        new MainMenu(plugin).open(player);
                        break;
                }
            }
        }
    }

    //Use reset timer to prevent accidental double click.
    private class ResetTimer extends BukkitRunnable {

        private final Map<Player, Integer> countDowns = new ConcurrentHashMap<>();

        @Override
        public void run() {

            for (Player player : countDowns.keySet()) {

                int count = countDowns.get(player);

                if (count <= 0) {
                    countDowns.remove(player);
                } else {
                    countDowns.replace(player, --count);
                }
            }
        }

        void addPlayer(Player player) {
            countDowns.put(player, 2);
        }

        boolean containsPlayer(Player player) {
            return countDowns.containsKey(player);
        }
    }
}
