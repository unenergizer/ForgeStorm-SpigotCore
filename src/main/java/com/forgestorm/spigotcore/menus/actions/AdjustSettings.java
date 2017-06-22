package com.forgestorm.spigotcore.menus.actions;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.SettingTypes;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import com.forgestorm.spigotcore.menus.SettingsMenu;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class AdjustSettings implements ClickAction {

	private final SpigotCore plugin;
	private final SettingTypes type;

	@Override
	public void click(Player player) {
		PlayerProfileData profile = plugin.getProfileManager().getProfile(player);
		SettingsMenu menu = (SettingsMenu) profile.getCurrentMenu();
		
		boolean tof;
		switch (type) {
			case CHAT_FILTER:
			tof = !profile.isChatFilter();
			profile.setChatFilter(tof);
			player.sendMessage(color(tof ? "&aChat filter on." : "&cChat filter off."));
			break;
		case FRIEND_REQUEST:
			tof = !profile.isFriendRequests();
			profile.setFriendRequests(tof);
			player.sendMessage(color(tof ? "&aFriend requests on." : "&cFriend requests off."));
			break;
		case GUILD_REQUEST:
			tof = !profile.isGuildRequests();
			profile.setGuildRequests(tof);
			player.sendMessage(color(tof ? "&aGuild requests on." : "&cGuild requests off."));
			break;
		case PARTY_REQUEST:
			tof = !profile.isPartyRequests();
			profile.setPartyRequests(tof);
			player.sendMessage(color(tof ? "&aParty requests on." : "&cParty requests off."));
			break;
		case TRADE_REQUEST:
			tof = !profile.isTradeRequests();
			profile.setTradeRequests(tof);
			player.sendMessage(color(tof ? "&aTrade requests on." : "&cTrade requests off."));
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
		menu.updateMenuItems(type);
	}
	
	private String color(String msg) {
		return ChatColor.translateAlternateColorCodes('&', msg);
	}
}