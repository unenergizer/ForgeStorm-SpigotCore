package com.forgestorm.spigotcore.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.menus.CraftingMenu;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InventoryClose implements Listener {
	
	private final SpigotCore PLUGIN;
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();
		PlayerProfileData profile = PLUGIN.getProfileManager().getProfile(player);
		Menu menu = profile.getCurrentMenu();
		
		if (menu != null) {
			if (menu instanceof CraftingMenu) {
				((CraftingMenu) menu).onClose(player);
			}	
		}
		
		profile.setCurrentMenu(null);
	}
}
