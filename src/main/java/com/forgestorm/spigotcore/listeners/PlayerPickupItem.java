package com.forgestorm.spigotcore.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.forgestorm.spigotcore.constants.Messages;

public class PlayerPickupItem implements Listener {

	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		
		if (event.getItem().getItemStack().getType().equals(Material.EMERALD)) {
			Player player = event.getPlayer();
			player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,  .8f, .8f);
			player.sendMessage(Messages.ITEM_PICKUP_GEMS.toString().replace("%s", Integer.toString(event.getItem().getItemStack().getAmount())));
		}
	}
}
