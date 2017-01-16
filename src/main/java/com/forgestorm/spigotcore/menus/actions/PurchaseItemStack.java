package com.forgestorm.spigotcore.menus.actions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;

import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;

@AllArgsConstructor
public class PurchaseItemStack implements ClickAction {
	
	private final SpigotCore PLUGIN;
	private final ItemStack ITEM;
	private final int COST;

	@Override
	public void click(Player player) {
		//Buy Item (remove money from account)
		boolean canPurchase = PLUGIN.getProfileManager().getProfile(player).removeCurrency(COST);
		
		if (canPurchase) {
			//Give Player Item
			player.getInventory().addItem(ITEM);
			player.sendMessage(ChatColor.GREEN + "Your purchase was successful!!");
		} else {
			player.sendMessage(ChatColor.RED + "You do not have enouh money to make this purchase.");
		}
	}
}
