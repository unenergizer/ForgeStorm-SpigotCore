package com.forgestorm.spigotcore.menus.tracking;

import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.help.HelpMenu;
import com.forgestorm.spigotcore.util.item.ItemGenerator;

public class TrackingDeviceMenu extends Menu {

	private final SpigotCore PLUGIN;
	
	public TrackingDeviceMenu(SpigotCore plugin) {
		super(plugin);
		PLUGIN = plugin;
		init("Tracking Device Menu", 1);
		makeMenuItems();
	}

	@Override
	protected void makeMenuItems() {
		
		ItemTypes type = ItemTypes.MENU;
		ItemGenerator itemGen = PLUGIN.getItemGen();
		
		//#1 Menu Tracking Type (NPC and Building Locations, Profession Locations[xyz cords, buildings, farms, mining areas, etc]
		//NPC -> NPC's.
		//Close menu
		//Activate compass and action bar text (distance to, up or down indicator)
		//When player is  5 blocks from xyz, clear compass directions and notify player of arrival.
		ItemStack trackNPC = itemGen.generateItem("tracking_menu_npc", type);
		ItemStack locations = itemGen.generateItem("tracking_menu_locations", type);
		ItemStack professions = itemGen.generateItem("tracking_menu_professions", type);
		ItemStack backButton = itemGen.generateItem("back_button", type);
		ItemStack exitButton = itemGen.generateItem("exit_button", type);

		setItem(trackNPC, 0, TrackNPCMenu.class);
		setItem(locations, 1, TrackBuildingMenu.class);
		setItem(professions, 2, TrackProfessionMenu.class);
		setItem(backButton, 7, HelpMenu.class);
		setItem(exitButton, 8, new Exit(PLUGIN));
	}

	
	
	
}
