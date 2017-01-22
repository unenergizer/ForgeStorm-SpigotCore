package com.forgestorm.spigotcore.listeners;

import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@AllArgsConstructor
public class PlayerDropItem implements Listener {

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {

		ItemStack item = event.getItemDrop().getItemStack();
		Player player = event.getPlayer();

		//If player throws out an item that is account bound, delete it.
		if (item != null && item.getItemMeta() != null && item.getItemMeta().getLore() != null) {
			List<String> lore = item.getItemMeta().getLore();
			String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

			Pattern regex = Pattern.compile("soulbound");
			Matcher matcher = regex.matcher(allLore);

			if (matcher.find()) {
				//This will remove the item.
				event.getItemDrop().remove();

				//Send player a sound notification.
				player.playSound(player.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 1F, 1F);

				//Send item deletion message.
				player.sendMessage(ChatColor.RED + "That item was account bound, so it was deleted!");
			}
		}

	}
}
