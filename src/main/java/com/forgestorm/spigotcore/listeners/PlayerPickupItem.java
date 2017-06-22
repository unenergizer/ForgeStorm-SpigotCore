package com.forgestorm.spigotcore.listeners;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.CommonSounds;
import com.forgestorm.spigotcore.constants.SpigotCoreMessages;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import lombok.AllArgsConstructor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class PlayerPickupItem implements Listener {

    private final SpigotCore plugin;

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        ItemStack itemStack = event.getItem().getItemStack();

        if (itemStack.getType().equals(Material.EMERALD)) {
            Player player = event.getPlayer();
            PlayerProfileData playerProfileData = plugin.getProfileManager().getProfile(player);
            int amount = itemStack.getAmount();

            // Add the amount to the players profile.
            playerProfileData.addCurrency(amount);

            // Show pickup message
            player.sendMessage(SpigotCoreMessages.ITEM_PICKUP_GEMS.toString().replace("%s", Integer.toString(amount)));
            CommonSounds.ACTION_SUCCESS.playSound(player);

            // Remove the item.
            event.getItem().remove();
            event.setCancelled(true);
        }
    }
}
