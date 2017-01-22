package com.forgestorm.spigotcore.listeners;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.world.instance.Realm;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerInteractEntity implements Listener {
	
	private final SpigotCore PLUGIN;

    private final ResetTimer resetTimer;

    public PlayerInteractEntity(SpigotCore plugin) {
        PLUGIN = plugin;
        resetTimer = new ResetTimer();
        resetTimer.runTaskTimerAsynchronously(plugin, 0, 1);
    }

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {

		Player player = event.getPlayer();

		if (!resetTimer.containsPlayer(player)) {

            resetTimer.addPlayer(player);

            if (event.getRightClicked() instanceof Player) {
                Player clickedPlayer = (Player) event.getRightClicked();

                //Check to see if entity is a Citizens NPC.
                if (clickedPlayer.hasMetadata("NPC")) {

                    PLUGIN.getCitizenManager().onCitizenInteract(player, clickedPlayer);
                } else {
                    if (player.getWorld().getName().equals(player.getUniqueId().toString())) {

                        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

                        if (itemInMainHand != null) {
                            if (player.isSneaking() && itemInMainHand.getType().equals(Material.COMPASS)) {
                                Realm realm = PLUGIN.getRealmManager().getPlayerRealm(player);

                                if (!realm.isFriend(clickedPlayer)) {
                                    realm.addFriend(clickedPlayer);
                                    player.sendMessage(ChatColor.GREEN + "You have added: " + clickedPlayer.getName () + " to your realm!");
                                    clickedPlayer.sendMessage(ChatColor.GREEN + "You have added to " + player.getName() + "'s realm!");

                                } else {
                                    realm.removeFriend(clickedPlayer);
                                    player.sendMessage(ChatColor.RED + "You have removed: " + clickedPlayer.getName () + " from your realm!");
                                    clickedPlayer.sendMessage(ChatColor.RED + "You have been removed from " + player.getName() + "'s realm!");
                                }
                            }
                        }
                    }
                }
            }
        }
	}

    private class ResetTimer extends BukkitRunnable {

        private final Map<Player, Integer> countDowns = new ConcurrentHashMap<>();

        public ResetTimer() {
            System.out.println("ResetTimer created!");
        }

        @Override
        public void run() {

            for (Player player : countDowns.keySet()) {

                int count = countDowns.get(player);

                if (count > 0) {
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