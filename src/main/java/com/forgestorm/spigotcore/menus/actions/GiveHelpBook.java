package com.forgestorm.spigotcore.menus.actions;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class GiveHelpBook implements ClickAction {

	private ItemStack fullHelpBook;

	@Override
	public void click(Player player) {

		makeHelpBook();
		
		//Setup for placing the item in blank inventory slot
		boolean hasBook = false;
		int invSize = player.getInventory().getSize();
		int freeSlots = 0;

		//Lets loop through he inventory and make sure the player
		//doesn't already have a book.
		for (int i = 0; i < invSize; i++) {
			ItemStack item = player.getInventory().getItem(i);

			//Check for empty slot.
			if (item == null) {
				freeSlots++;
			}

			//Check if the book exists.
			if (item != null && item.equals(fullHelpBook)) {
				hasBook = true;
				player.sendMessage(ChatColor.RED + "You already have a copy of the help book.");

				//Play a sound.
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 1F, .5F);
			}
		}

		//Check to see if the player has a book.
		if (hasBook == false && freeSlots > 0) {
			//Loop through the players inventory and find a free inventory slot.
			for (int i = 0; i < invSize; i++) {
				ItemStack item = player.getInventory().getItem(i);

				//Check for empty slot.
				if (item == null) {
					//Make sure we have not already sent them a book.
					if (hasBook == false) {
						//Place book in empty slot.
						player.getInventory().setItem(i, fullHelpBook.clone());
						hasBook = true;

						//Send message.
						player.sendMessage(ChatColor.GREEN + "The book has been placed in your inventory.");

						//Play notification sound.
						player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1F, 1F);
					}
				} 
			}
		}

		//If we could not place a book, notify the player.
		if (hasBook == false) {
			player.sendMessage(ChatColor.RED + "You do not have room in your inventory for the help book.");

			//Play a sound.
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BASS, 1F, .5F);
		}

	}

	private void makeHelpBook() {
		ArrayList<String> lores = new ArrayList<>();

		lores.add("");
		lores.add(ChatColor.GRAY + "Covers many different topics.");
		lores.add(ChatColor.GRAY + "  1" + ChatColor.DARK_PURPLE + ". " + ChatColor.GRAY + "Server Introduction");
		lores.add(ChatColor.GRAY + "  2" + ChatColor.DARK_PURPLE + ". " + ChatColor.GRAY + "Game Guide");
		lores.add(ChatColor.GRAY + "  3" + ChatColor.DARK_PURPLE + ". " + ChatColor.GRAY + "Rules");
		lores.add(ChatColor.GRAY + "  4" + ChatColor.DARK_PURPLE + ". " + ChatColor.GRAY + "Commands");

		//Give user the welcome/stats book.
		fullHelpBook = new ItemStack(Material.WRITTEN_BOOK, 1);
		BookMeta bookMeta = (BookMeta) fullHelpBook.getItemMeta();
		bookMeta.setTitle(ChatColor.YELLOW + "" + ChatColor.BOLD + "Player Handbook");
		bookMeta.setLore(lores);
		bookMeta.setAuthor(ChatColor.GREEN + "" + ChatColor.BOLD + "ForgeStorm Staff");
		bookMeta.addPage(ChatColor.RED + "" + ChatColor.BOLD + "     ForgeStorm" + ChatColor.RESET +
				"\n" + " " + ChatColor.BLUE + ChatColor.UNDERLINE + "www.ForgeStorm.com" + ChatColor.RESET +
				"\n" + "" +
				"\n" + "" +
				"\n" + ChatColor.BOLD + "Contents:" + ChatColor.RESET +
				"\n" + "   1. Index" +
				"\n" + "   2. Introduction" +
				"\n" + "   3. Game Guide" +
				"\n" + "   4. Rules" +
				"\n" + "   5. Commands");
		bookMeta.addPage(ChatColor.RED + "" + ChatColor.BOLD + "    Introduction" + ChatColor.RESET +
				"\n" + "    * the beggining *" +
				"\n" + "" +
				"\n" + "Welcome traveler!  You are about to embark on an epic adventure full of treasure and wonder." + 
				"\n" + "Tread carefully, as the world has been corrupted by an unknown evil force.  Are you up for a challenge?");
		bookMeta.addPage(ChatColor.RED + "" + ChatColor.BOLD + "     Game Guide" + ChatColor.RESET +
				"\n" + "     * how to play *" +
				"\n" + "" +
				"\n" + "This is a Role Playing Game (RPG) Minigame Server, which has several custom features not found on any other server." +
				"\n" + "" +
				"\n" + "" +
				"\n" + "" + ChatColor.BOLD + "Guide:" +
				"\n" + ChatColor.BLUE + ChatColor.UNDERLINE + "www.ForgeStorm.com/guide");
		bookMeta.addPage(ChatColor.RED + "" + ChatColor.BOLD + "   Server Rules" + ChatColor.RESET +
				"\n" + "   * don't break'em *" +
				"\n" + "" +
				"\n" + ChatColor.BOLD + "Rules:" + ChatColor.RESET +
				"\n" + "1. Do not hack!" +
				"\n" + "2. Do not advertise!" +
				"\n" + "3. Don't ask for items!" +
				"\n" + "4. Respect players." +
				"\n" + "5. Respect staff members." +
				"\n" + "6. No full CAPS messages.");
		bookMeta.addPage(ChatColor.RED + "" + ChatColor.BOLD + "     Commands" + ChatColor.RESET +
				"\n" + "    * boss mode *" +
				"\n" + "" +
				"\n" + "There are many commands that will help you on your adventures." +
				"\n" + "" +
				"\n" + "The next few pages will contain a list of all the commands you can use." +
				"\n" + "");
		bookMeta.addPage(ChatColor.BOLD + "/money" + ChatColor.RESET +
				"\n" + "Gets the amount of money you have." +
				"\n" + "" +
				"\n" + ChatColor.BOLD + "/playtime" + ChatColor.RESET +
				"\n" + "Your total play time and your current level up time." +
				"\n" + "" +
				"\n" + ChatColor.BOLD + "/roll <NumberHere>" + ChatColor.RESET +
				"\n" + "Rolls a dice. Try it with a friend.");
		bookMeta.addPage(ChatColor.BOLD + "/help" + ChatColor.RESET +
				"\n" + "This will open the help menu." +
				"\n" + "" +
				"\n" + ChatColor.BOLD + "/settings" + ChatColor.RESET +
				"\n" + "This will open the settings menu." +
				"\n" + "" +
				"\n" + ChatColor.BOLD + "/mainmenu" + ChatColor.RESET +
				"\n" + "This will open up the main menu.");
		bookMeta.addPage(ChatColor.BOLD + "/hub" + ChatColor.RESET + " or " +
				ChatColor.BOLD + "/lobby" + ChatColor.RESET +
				"\n" + "This will bring you back to the main lobby." +
				"\n" + "" +
				"\n" + ChatColor.BOLD + "/creative" + ChatColor.RESET +
				"\n" + "This will bring you to the creative server.");


		fullHelpBook.setItemMeta(bookMeta);
	}
}
