package com.forgestorm.spigotcore.menus.help;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.constants.SpigotCoreMessages;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.SendChatText;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.inventory.ItemStack;

public class LinksMenu extends Menu {

	private final SpigotCore plugin;

	public LinksMenu(SpigotCore plugin) {
		super(plugin);
		this.plugin = plugin;
		init("ForgeStorm Links", 1);
		makeMenuItems();
	}

	@Override
	protected void makeMenuItems() {
		ItemStack forgeStorm, theGreatHayley, backButton, exitButton;
		ItemTypes type = ItemTypes.MENU;
		ItemGenerator itemGen = plugin.getItemGen();

		forgeStorm = itemGen.generateItem("links_forgestorm", type);
		theGreatHayley = itemGen.generateItem("links_hayley", type);
		backButton = itemGen.generateItem("back_button", type);
		exitButton = itemGen.generateItem("exit_button", type);

		String[] forgestormMessages = {
				"",
				"",
				SpigotCoreMessages.BAR_SOCIAL_MEDIA.toString(),
				"",
				SpigotCoreMessages.FS_SOCIAL_WEB.toString(),
				SpigotCoreMessages.FS_SOCIAL_FACEBOOK.toString(),
				SpigotCoreMessages.FS_SOCIAL_TWITTER.toString(),
				SpigotCoreMessages.FS_SOCIAL_YOUTUBE.toString(),
				"",
				SpigotCoreMessages.BAR_BOTTOM.toString()
		};
		String[] hayleyMessages = {
				"",
				"",
				SpigotCoreMessages.BAR_SOCIAL_MEDIA.toString(),
				"",
				SpigotCoreMessages.HAYLEY_TWITCH.toString(),
				SpigotCoreMessages.HAYLEY_INSTAGRAM.toString(),
				SpigotCoreMessages.HAYLEY_TWITTER.toString(),
				SpigotCoreMessages.HAYLEY_YOUTUBE.toString(),
				"",
				SpigotCoreMessages.BAR_BOTTOM.toString()
		};
		
		setItem(forgeStorm, 0, new SendChatText(forgestormMessages));
		setItem(theGreatHayley, 1, new SendChatText(hayleyMessages));
		setItem(backButton, 7, HelpMenu.class);
		setItem(exitButton, 8, new Exit(plugin));
	}
}
