package com.forgestorm.spigotcore.menus.actions;

import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ProfessionType;
import com.forgestorm.spigotcore.menus.profession.FarmingTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.FishingTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.LumberjackTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.MiningTrainerMenu;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;

import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;

@AllArgsConstructor
public class PurchaseProfession implements ClickAction {
	
	private final SpigotCore PLUGIN;
	private final ProfessionType PROFESSION;
	private final int COST;

	@Override
	public void click(Player player) {
		//Buy Item (remove money from account)
		PlayerProfileData profile = PLUGIN.getProfileManager().getProfile(player);
		boolean canPurchase = profile.removeCurrency(COST);
		
		
		if (canPurchase) {
			//Give Player Item
			player.sendMessage(ChatColor.GREEN + "Your purchase was successful!!");
			switch (PROFESSION) {
			case FARMING:
				profile.setFarmingExperience(0);
				new FarmingTrainerMenu(PLUGIN).open(player);
				break;
			case FISHING:
				profile.setFishingExperience(0);
				new FishingTrainerMenu(PLUGIN).open(player);
				break;
			case LUMBERJACK:
				profile.setLumberjackExperience(0);
				new LumberjackTrainerMenu(PLUGIN).open(player);
				break;
			case MINING:
				profile.setMiningExperience(0);
				new MiningTrainerMenu(PLUGIN, player, true).open(player);
				break;
			}
		} else {
			player.sendMessage(ChatColor.RED + "You do not have enough money to make this purchase.");
		}
	}
}
