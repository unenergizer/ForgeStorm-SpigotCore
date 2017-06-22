package com.forgestorm.spigotcore.menus;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.actions.FeatureComingSoon;
import com.forgestorm.spigotcore.menus.actions.OpenProfessionsMenu;
import com.forgestorm.spigotcore.menus.help.HelpMenu;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.inventory.ItemStack;

public class MainMenu extends Menu {

	private final SpigotCore plugin;

	public MainMenu(SpigotCore plugin) {
		super(plugin);
		this.plugin = plugin;
		init("Main Menu", 4);
		makeMenuItems();
	}

	@Override
	protected void makeMenuItems() {
		ItemTypes type = ItemTypes.MENU;
		ItemGenerator itemGen = plugin.getItemGen();
		ItemStack playerInfo, help, guildManagement, friendManagement, gameSelection, lobbySelection, cosmeticMenu, professionsMenu;

		playerInfo = itemGen.generateItem("main_player_info", type);
		help = itemGen.generateItem("main_help", type);
		guildManagement = itemGen.generateItem("main_guild_management", type);
		friendManagement = itemGen.generateItem("main_friend_management", type);
		gameSelection = itemGen.generateItem("main_game_selection", type);
		lobbySelection = itemGen.generateItem("main_lobby_selection", type);
		cosmeticMenu = itemGen.generateItem("main_cosmetic_menu", type);
		professionsMenu = itemGen.generateItem("main_professions", type);

		//Row 1
		setItem(gameSelection, 12, plugin.getGameSelectionMenu(), false);
		setItem(help, 13, HelpMenu.class, false);
		setItem(lobbySelection, 14, new FeatureComingSoon(), false);

		//Row 2
		setItem(playerInfo, 20, PlayerMenu.class, false);
		setItem(guildManagement, 21, new FeatureComingSoon(), false);
		setItem(friendManagement, 22, new FeatureComingSoon(), false);
		setItem(cosmeticMenu, 23, CosmeticMenu.class, false);
		setItem(professionsMenu, 24, new OpenProfessionsMenu(plugin), false);
	}
}
