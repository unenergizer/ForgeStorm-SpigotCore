package com.forgestorm.spigotcore.listeners;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.menus.MainMenu;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class PlayerInteract implements Listener {

    private final SpigotCore plugin;

    public PlayerInteract(SpigotCore plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

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
}
