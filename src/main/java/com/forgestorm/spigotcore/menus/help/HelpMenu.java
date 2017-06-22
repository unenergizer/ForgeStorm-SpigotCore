package com.forgestorm.spigotcore.menus.help;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.MainMenu;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.GiveHelpBook;
import com.forgestorm.spigotcore.menus.tracking.TrackingDeviceMenu;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.inventory.ItemStack;

public class HelpMenu extends Menu {

	private final SpigotCore plugin;

	public HelpMenu(SpigotCore plugin) {
		super(plugin);
		this.plugin = plugin;
		init("Help Menu", 1);
		makeMenuItems();
	}
	
	@Override
	protected void makeMenuItems() {
		
		ItemTypes type = ItemTypes.MENU;
		ItemGenerator itemGen = plugin.getItemGen();

		ItemStack book, tutorials, links, trackingDevice, backButton, exitButton;
		book = itemGen.generateItem("help_book", type);
		tutorials = itemGen.generateItem("help_tutorials", type);
		links = itemGen.generateItem("help_links", type);
		trackingDevice = itemGen.generateItem("help_tracking_device", type);
		backButton = itemGen.generateItem("back_button", type);
		exitButton = itemGen.generateItem("exit_button", type);
		
		setItem(book, 0, new GiveHelpBook());
		setItem(tutorials, 1, TutorialMenu.class);
		setItem(links, 2, LinksMenu.class);
		setItem(trackingDevice, 3, TrackingDeviceMenu.class);
		setItem(backButton, 7, MainMenu.class);
		setItem(exitButton, 8, new Exit(plugin));
	}
}
