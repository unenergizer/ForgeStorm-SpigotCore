package com.forgestorm.spigotcore.util.player;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;

public class PlayerRewards {

	private final SpigotCore PLUGIN;
	private Player player;
	private	PlayerProfileData profile;

	public PlayerRewards(SpigotCore plugin, Player player) {
		PLUGIN = plugin;
		this.player = player;
		profile = PLUGIN.getProfileManager().getProfile(player);
	}

	public void giveExp(int argument) {
		profile.addExperience(argument);
	}

	public void giveMoney(int money)  {
		profile.addCurrency(money);
	}

	public void giveItems(ItemStack ... items) {
		if (player.getInventory().firstEmpty() == -1) {
			for (ItemStack item : items) {
				Bukkit.getWorlds().get(0).dropItem(player.getLocation(), item);
				player.sendMessage(ChatColor.RED + "Your inventory was full so we dropped your stuff on the floor.");
				player.sendMessage(ChatColor.RED + "You're welcome. ;)");
			}
		} else {
			player.getInventory().addItem(items);
		}
	}
}
