package com.forgestorm.spigotcore.menus;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.constants.SettingTypes;
import com.forgestorm.spigotcore.menus.actions.AdjustSettings;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;
import com.forgestorm.spigotcore.util.item.ItemGenerator;

public class SettingsMenu extends Menu {

	private final SpigotCore PLUGIN;
	private final PlayerProfileData profile;
	private final ItemGenerator itemGen;
	private final ItemTypes type = ItemTypes.MENU;

	private ItemStack chatFilterOn, chatFilterOff,
			guildRequestsOn, guildRequestsOff, friendRequestsOn, friendRequestsOff,
			partyRequestsOn, partyRequestsOff, tradeRequestsOn, tradeRequestsOff,
			debugOn, debugOff;

	public SettingsMenu(SpigotCore plugin, Player player) {
		super(plugin);
		PLUGIN = plugin;
		init("Account Settings Menu", 1);
		profile = plugin.getProfileManager().getProfile(player);
		itemGen = plugin.getItemGen();
		makeMenuItems();
	}

	@Override
	protected void makeMenuItems() {
		
		ItemStack backButton, exitButton;
		
		chatFilterOn = itemGen.generateItem("settings_chat_filter_on", type);
		chatFilterOff = itemGen.generateItem("settings_chat_filter_off", type);
		guildRequestsOn = itemGen.generateItem("settings_guild_requests_on", type);
		guildRequestsOff = itemGen.generateItem("settings_guild_requests_off", type);	
		friendRequestsOn = itemGen.generateItem("settings_friend_requests_on", type);
		friendRequestsOff = itemGen.generateItem("settings_friend_requests_off", type);
		partyRequestsOn = itemGen.generateItem("settings_party_requests_on", type);
		partyRequestsOff = itemGen.generateItem("settings_party_requests_off", type);	
		tradeRequestsOn = itemGen.generateItem("settings_trade_requests_on", type);
		tradeRequestsOff = itemGen.generateItem("settings_trade_requests_off", type);	
		debugOn = itemGen.generateItem("settings_debug_on", type);
		debugOff = itemGen.generateItem("settings_debug_off", type);
		
		backButton = itemGen.generateItem("back_button", type);
		exitButton = itemGen.generateItem("exit_button", type);

		updateMenuItems(SettingTypes.ALL);

		//Back button!
		setItem(backButton, 7, PlayerMenu.class);
		setItem(exitButton, 8, new Exit(PLUGIN));
	}

	public void updateMenuItems(SettingTypes type) {
		//Chat Filter
		if (type.equals(SettingTypes.CHAT_FILTER) || type.equals(SettingTypes.ALL)) {
			if (profile.isChatFilter()) {
				setItem(chatFilterOn, 0, new AdjustSettings(PLUGIN, SettingTypes.CHAT_FILTER));
			} else {
				setItem(chatFilterOff, 0, new AdjustSettings(PLUGIN, SettingTypes.CHAT_FILTER));
			}
		}

		//Guild requests
		if (type.equals(SettingTypes.GUILD_REQUEST) || type.equals(SettingTypes.ALL)) {
			if (profile.isGuildRequests()) {
				setItem(guildRequestsOn, 1, new AdjustSettings(PLUGIN, SettingTypes.GUILD_REQUEST));
			} else {
				setItem(guildRequestsOff, 1, new AdjustSettings(PLUGIN, SettingTypes.GUILD_REQUEST));
			}
		}

		//Friend requests
		if (type.equals(SettingTypes.FRIEND_REQUEST) || type.equals(SettingTypes.ALL)) {
			if (profile.isFriendRequests()) {
				setItem(friendRequestsOn, 2, new AdjustSettings(PLUGIN, SettingTypes.FRIEND_REQUEST));
			} else {
				setItem(friendRequestsOff, 2, new AdjustSettings(PLUGIN, SettingTypes.FRIEND_REQUEST));
			}
		}

		//Party requests
		if (type.equals(SettingTypes.PARTY_REQUEST) || type.equals(SettingTypes.ALL)) {
			if (profile.isPartyRequests()) {
				setItem(partyRequestsOn, 3, new AdjustSettings(PLUGIN, SettingTypes.PARTY_REQUEST));
			} else {
				setItem(partyRequestsOff, 3, new AdjustSettings(PLUGIN, SettingTypes.PARTY_REQUEST));
			}
		}
		
		//Trade requests
		if (type.equals(SettingTypes.TRADE_REQUEST) || type.equals(SettingTypes.ALL)) {
			if (profile.isTradeRequests()) {
				setItem(tradeRequestsOn, 4, new AdjustSettings(PLUGIN, SettingTypes.TRADE_REQUEST));
			} else {
				setItem(tradeRequestsOff, 4, new AdjustSettings(PLUGIN, SettingTypes.TRADE_REQUEST));
			}
		}
		
		//Debug Messages
		if (type.equals(SettingTypes.DEBUG_MESSAGES) || type.equals(SettingTypes.ALL)) {
			if (profile.isToggleDebug()) {
				setItem(debugOn, 5, new AdjustSettings(PLUGIN, SettingTypes.DEBUG_MESSAGES));
			} else {
				setItem(debugOff, 5, new AdjustSettings(PLUGIN, SettingTypes.DEBUG_MESSAGES));
			}
		}
	}
}
