package com.forgestorm.spigotcore.menus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemLores;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.actions.ClickAction;
import com.forgestorm.spigotcore.menus.actions.ConnectToBungeeServer;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.util.item.ItemBuilder;
import com.forgestorm.spigotcore.util.item.ItemGenerator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameSelectionMenu extends Menu {

	private final SpigotCore PLUGIN;

	private ItemStack arcade, crative;
	private int arcadePlayers, creativePlayers;
	private int frame = 1;

	public GameSelectionMenu(SpigotCore plugin) {
		super(plugin);
		PLUGIN = plugin;
		init("Game Selection Menu", 1);
		makeMenuItems();
		updateMenu();
	}

	@Override
	protected void makeMenuItems() {
		ItemTypes type = ItemTypes.MENU;
		ItemGenerator itemGen = PLUGIN.getItemGen();
		ItemStack backButton, exitButton;
		
		backButton = itemGen.generateItem("back_button", type);
		exitButton = itemGen.generateItem("exit_button", type);

		setItem(backButton, 7, MainMenu.class);
		setItem(exitButton, 8, new Exit(PLUGIN));
	}

	/**
	 * This will create new items.
	 */ 
	private void updateItems() {
	
		//Create the items
		PLUGIN.getBungeecord().getPlayerCount("mg-full-01");
		arcade = createItem(ItemLores.ARCADE_TITLE.toString(), ItemLores.ARCADE_LORE.toString(), Material.BOW, arcadePlayers);
		
		PLUGIN.getBungeecord().getPlayerCount("creative");
		crative = createItem(ItemLores.CREATIVE_TITLE.toString(), ItemLores.CREATIVE_LORE.toString(), Material.GRASS, creativePlayers);
	}

	/**
	 * This will continuously update the players server selector menu.
	 * The updates are for updating the amount of players on each server and other things.
	 */
	private void updateMenu() {
		final int UPDATE_TIME = 1;

		ClickAction arcadeAction = new ConnectToBungeeServer(PLUGIN, "mg-full-01");
		ClickAction creativeAction = new ConnectToBungeeServer(PLUGIN, "creative");

		//Start the thread to update the items.
		new BukkitRunnable() {

			@Override
			public void run() {

				//Don't update anything unless a player is online.
				if (Bukkit.getOnlinePlayers().size() >= 1) {

					//Update the current frame.
					//This is for animated item descriptions.
					if (frame == 1) {
						frame++;
					} else {
						frame--;
					}
					
					//Update the Items
					updateItems();
					
					//Update Menu
					setItem(arcade, 0, arcadeAction);
					setItem(crative, 1, creativeAction);
				}
			}
		}.runTaskTimer(PLUGIN, 0, 20 * UPDATE_TIME);
	}

	/**
	 * This will create a menu item.
	 * @param title The title of the menu item (usually a server name).
	 * @param lore The description of the menu item.
	 * @param material The icon that will represent the menu item.
	 * @param server The server associated with this menu item.
	 * @return Returns a menu item ready for display to the user.
	 */
	private ItemStack createItem(String title, String lore, Material material, int playersOnline) {
		List<String> lores = new ArrayList<>();

		lores.add(""); 		//Blank line
		lores.add(lore); 	//Add actual server lore.
		lores.add(""); 		//Blank line

		//Connect now message
		if (frame == 1) {
			lores.add(ItemLores.CONNECT_FRAME_1.toString());
		} else {
			lores.add(ItemLores.CONNECT_FRAME_2.toString());
		}

		//Show players online.
		lores.add(ItemLores.PLAYERS_ONLINE.toString().replace("%s", Integer.toString(playersOnline)));

		return new ItemBuilder(material).setTitle(title).addLores(lores).build(true);
	}
}
