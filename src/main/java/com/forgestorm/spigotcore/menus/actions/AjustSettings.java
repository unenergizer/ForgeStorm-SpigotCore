package com.forgestorm.spigotcore.menus.actions;

import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.SettingTypes;
import com.forgestorm.spigotcore.menus.SettingsMenu;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;

import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;

@AllArgsConstructor
public class AjustSettings implements ClickAction {

	private final SpigotCore PLUGIN;
	private final SettingTypes TYPE;
	
	@Override
	public void click(Player player) {
		PlayerProfileData profile = PLUGIN.getProfileManager().getProfile(player);
		SettingsMenu menu = (SettingsMenu) profile.getCurrentMenu();
		
		boolean tof;
		switch (TYPE) {
		case CHAT_FILTER:
			tof = !profile.isChatFilter();
			profile.setChatFilter(tof);
			player.sendMessage(color(tof ? "&aChat filter on." : "&cChat filter off."));
			break;
		case FRIEND_REQUEST:
			tof = !profile.isFriendRequests();
			profile.setFriendRequests(tof);
			player.sendMessage(color(tof ? "&aFriend requests on." : "&cFriend requsts off."));
			break;
		case GUILD_REQUEST:
			tof = !profile.isGuildRequests();
			profile.setGuildRequests(tof);
			player.sendMessage(color(tof ? "&aGuild requests on." : "&cGuild requsts off."));
			break;
		case PARTY_REQUEST:
			tof = !profile.isPartyRequests();
			profile.setPartyRequests(tof);
			player.sendMessage(color(tof ? "&aParty requests on." : "&cParty requsts off."));
			break;
		case TRADE_REQUEST:
			tof = !profile.isTradeRequests();
			profile.setTradeRequests(tof);
			player.sendMessage(color(tof ? "&aTrade requests on." : "&cTrade requsts off."));
			break;
		case DEBUG_MESSAGES:
			tof = !profile.isToggleDebug();
			profile.setToggleDebug(tof);
			player.sendMessage(color(tof ? "&aDebug messages turned on." : "&cDebug messages turned off."));
			break;
		default:
			break;
		}
		
		//Update the menu
		menu.updateMenuItems(TYPE);
	}
	
	private String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}