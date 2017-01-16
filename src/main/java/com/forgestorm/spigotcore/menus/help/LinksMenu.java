package com.forgestorm.spigotcore.menus.help;

import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.constants.Messages;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.SendChatText;
import com.forgestorm.spigotcore.util.item.ItemGenerator;

public class LinksMenu extends Menu {
	
	private final SpigotCore PLUGIN;
	
	public LinksMenu(SpigotCore plugin) {
		super(plugin);
		PLUGIN = plugin;
		init("ForgeStorm Links", 1);
		makeMenuItems();
	}

	@Override
	protected void makeMenuItems() {
		ItemStack forgeStorm, theGreatHayley, backButton, exitButton;
		ItemTypes type = ItemTypes.MENU;
		ItemGenerator itemGen = PLUGIN.getItemGen();
				
		forgeStorm = itemGen.generateItem("links_forgestorm", type);
		theGreatHayley = itemGen.generateItem("links_hayley", type);
		backButton = itemGen.generateItem("back_button", type);
		exitButton = itemGen.generateItem("exit_button", type);

		String[] forgestormMessages = {
				"",
				"",
				Messages.BAR_SOCIAL_MEDIA.toString(),
				"",
				Messages.FS_SOCIAL_WEB.toString(),
				Messages.FS_SOCIAL_FACEBOOK.toString(),
				Messages.FS_SOCIAL_TWITTER.toString(),
				Messages.FS_SOCIAL_YOUTUBE.toString(),
				"",
				Messages.BAR_BOTTOM.toString()
		};
		String[] hayleyMessages = {
				"",
				"",
				Messages.BAR_SOCIAL_MEDIA.toString(),
				"",
				Messages.HAYLEY_TWITCH.toString(),
				Messages.HAYLEY_INSTAGRAM.toString(),
				Messages.HAYLEY_TWITTER.toString(),
				Messages.HAYLEY_YOUTUBE.toString(),
				"",
				Messages.BAR_BOTTOM.toString()
		};
		
		setItem(forgeStorm, 0, new SendChatText(forgestormMessages));
		setItem(theGreatHayley, 1, new SendChatText(hayleyMessages));
		setItem(backButton, 7, HelpMenu.class);
		setItem(exitButton, 8, new Exit(PLUGIN));
	}
}
