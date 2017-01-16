package com.forgestorm.spigotcore.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ChatIcons;
import com.forgestorm.spigotcore.constants.Messages;
import com.forgestorm.spigotcore.experience.ProfessionExperience;
import com.forgestorm.spigotcore.menus.RecipeOptionsMenu;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;
import com.forgestorm.spigotcore.util.imgmessage.EzImgMessage;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Admin implements CommandExecutor {

	private final SpigotCore PLUGIN;

	@Override
	public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

		//Check if the command sender is a server Operator
		if (commandSender instanceof Player && !PLUGIN.getProfileManager().getProfile((Player)commandSender).isAdmin()) {
			commandSender.sendMessage(Messages.NO_PERMISSION.toString());
			return false;
		}

		//Check command args
		if (args.length == 1) {

			switch (args[0].toLowerCase()) {
			case "eggtp":
				PLUGIN.getDragonEggTP().teleportToEgg((Player) commandSender);
				break;

			case "crafting":
				new RecipeOptionsMenu(PLUGIN).open((Player) commandSender);

				List<ItemStack> recipeItems = PLUGIN.getRecipeManager().getRecipeIngredients("Recipe01");

				for (ItemStack item : recipeItems) {
					((Player) commandSender).getInventory().addItem(item);
				}

				break;
			}

		} else if (args.length == 2) {

			switch (args[0].toLowerCase()) {
			case "chaticon":
				System.out.println("Sending chat icon...");
				((Player) commandSender).sendMessage("");
				new EzImgMessage().sendEzImgMessage((Player) commandSender, ChatIcons.valueOf(args[1].toUpperCase()), "This is test text!");
				break;
			}

		} else if (args.length == 3) {

			if (Bukkit.getPlayer(args[2]) == null){
				commandSender.sendMessage(Messages.COMMAND_ADMIN_PLAYER_NOT_ONLINE.toString());
				return false;
			}

			Player player = Bukkit.getPlayer(args[2]);
			PlayerProfileData profile = PLUGIN.getProfileManager().getProfile(player);

			switch (args[0].toLowerCase()) {
			case "demote":

				//Check which type of demote arg to use.
				switch (args[1].toLowerCase()){
				case "admin":
					profile.demoteAdmin();
					commandSender.sendMessage(Messages.COMMAND_ADMIN_DEMOTE_ADMIN_SUCCESS.toString());
					break;

				case "moderator":
					profile.demoteMod();
					commandSender.sendMessage(Messages.COMMAND_ADMIN_DEMOTE_MODERATOR_SUCCESS.toString());
					break;

				case "usergroup":
					if (profile.demote()) {
						commandSender.sendMessage(Messages.COMMAND_ADMIN_DEMOTE_USERGROUP_SUCCESS.toString());
					} else {
						commandSender.sendMessage(Messages.COMMAND_ADMIN_DEMOTE_USERGROUP_FAILURE.toString());
					}

					break;
				default:
					//Send incomplete arg message.
					break;
				}
				break;
			case "promote":

				//Check which type of promote arg to use.
				switch (args[1].toLowerCase()){
				case "admin":
					profile.promoteAdmin();
					commandSender.sendMessage(Messages.COMMAND_ADMIN_PROMOTE_ADMIN_SUCCESS.toString());
					break;

				case "moderator":
					profile.promoteMod();
					commandSender.sendMessage(Messages.COMMAND_ADMIN_PROMOTE_MODERATOR_SUCCESS.toString());
					break;

				case "usergroup":
					profile.promote();
					commandSender.sendMessage(Messages.COMMAND_ADMIN_PROMOTE_USERGROUP_SUCCESS.toString());
					break;

				default:
					//Send incomplete arg message.
					break;
				}
				break;
			default:
				//commandSender.sendMessage(Messages.COMMAND_ADMIN_UNKNOWN.toString());
				break;
			}
		} else if (args.length == 4) {

			if (Bukkit.getPlayer(args[2]) == null){
				commandSender.sendMessage(Messages.COMMAND_ADMIN_PLAYER_NOT_ONLINE.toString());
				return false;
			}

			Player player = Bukkit.getPlayer(args[2]);
			PlayerProfileData profile = PLUGIN.getProfileManager().getProfile(player);
			String argument = args[3];

			//if statement
			//check if arg 4 is a number

			switch (args[0].toLowerCase()) {
			case "set":

				//Check which type of arg to use.
				switch (args[1].toLowerCase()){
				case "usergroup":	
					profile.setUserGroup(Integer.parseInt(argument));
					commandSender.sendMessage(Messages.COMMAND_ADMIN_SET_USERGROUP_SUCCESS.toString().replace("%s", argument));
					break;

				case "currency":
					profile.setCurrency(Integer.parseInt(argument));
					int currency = profile.getCurrency();
					commandSender.sendMessage(Messages.COMMAND_ADMIN_SET_CURRENCY_SUCCESS.toString().replace("%s", Integer.toString(currency)));
					break;

				case "premiumcurrency":
					profile.setPremiumCurrency(Integer.parseInt(argument));
					int premiumCurrency = profile.getPremiumCurrency();
					commandSender.sendMessage(Messages.COMMAND_ADMIN_SET_PREMIUMCURRENCY_SUCCESS.toString().replace("%s", Integer.toString(premiumCurrency)));
					break;
				
				//PLAYER EXP
				case "pexp":
				case "pexperience":
					profile.setExperience(Integer.parseInt(argument));
					long exp = profile.getExperience();
					commandSender.sendMessage(Messages.COMMAND_ADMIN_SET_EXPERIENCE_SUCCESS.toString().replace("%s", Integer.toString((int)exp)));
					break;

				case "plvl":
				case "plevel":
					profile.setLevel(Integer.parseInt(argument));
					int level = profile.getPlayerLevel();	
					commandSender.sendMessage(Messages.COMMAND_ADMIN_SET_LEVEL_SUCCESS.toString().replace("%s", Integer.toString(level)));
					break;
				
				//MINING EXP
				case "mexp":
				case "mexperience":
					profile.setMiningExperience(Integer.parseInt(argument));
					long mexp = profile.getMiningExperience();
					commandSender.sendMessage(Messages.COMMAND_ADMIN_SET_EXPERIENCE_SUCCESS.toString().replace("%s", Integer.toString((int)mexp)));
					break;

				case "mlvl":
				case "mlevel":
					profile.setLevel(Integer.parseInt(argument));
					int mlevel = new ProfessionExperience().getLevel(profile.getMiningExperience());	
					commandSender.sendMessage(Messages.COMMAND_ADMIN_SET_LEVEL_SUCCESS.toString().replace("%s", Integer.toString(mlevel)));
					break;

				default:
					//Send incomplete arg message.
					break;
				}
				break;
			case "add":

				//Check which type of arg to use.
				switch (args[1].toLowerCase()){
				case "currency":
					int previousCurrency = profile.getCurrency();
					String addCurrencyMessage = Messages.COMMAND_ADMIN_ADD_CURRENCY_SUCCESS.toString();
					profile.addCurrency(Integer.parseInt(argument));
					int currency = profile.getCurrency();

					commandSender.sendMessage(addCurrencyMessage.replace("%c", Integer.toString(previousCurrency)).replace("%s", Integer.toString(currency)));
					break;

				case "premiumcurrency":
					int previousPremiumCurrency = profile.getCurrency();
					String addPremiumCurrencyMessage = Messages.COMMAND_ADMIN_ADD_PREMIUMCURRENCY_SUCCESS.toString();
					profile.addPremiumCurrency(Integer.parseInt(argument));
					int premiumCurrency = profile.getPremiumCurrency();

					commandSender.sendMessage(addPremiumCurrencyMessage.replace("%c", Integer.toString(previousPremiumCurrency)).replace("%s", Integer.toString(premiumCurrency)));
					break;

				case "pexp":	
				case "pexperience":
					String addXPMessage = Messages.COMMAND_ADMIN_ADD_EXPERIENCE_SUCCESS.toString();
					long previousExperience = profile.getExperience();
					profile.addExperience(Integer.parseInt(argument));
					long exp = profile.getExperience();	

					commandSender.sendMessage(addXPMessage.replace("%c", Long.toString(previousExperience)).replace("%s", Long.toString(exp)));
					break;

				case "plvl":
				case "plevel":
					String addLVLMessage = Messages.COMMAND_ADMIN_ADD_LEVEL_SUCCESS.toString();
					int currentLevel = player.getLevel();
					profile.addLevel(Integer.parseInt(argument));
					int level = profile.getPlayerLevel();	

					commandSender.sendMessage(addLVLMessage.replace("%c", Integer.toString(currentLevel)).replace("%s", Integer.toString(level)));
					break;

				case "recipe":
					String msg = PLUGIN.getRecipeManager().givePlayerRecipe(player, argument);
					commandSender.sendMessage(msg);
					break;

				default:
					//Send incomplete arg message.
					break;
				}

				break;
			case "remove":

				//Check which type of arg to use.
				switch (args[1].toLowerCase()){
				case "currency":
					int previousCurrency = profile.getCurrency();
					int currency = previousCurrency - Integer.parseInt(argument);
					String addCurrencyMessage = Messages.COMMAND_ADMIN_REMOVE_CURRENCY_SUCCESS.toString();
					profile.removeCurrency(Integer.parseInt(argument));
					commandSender.sendMessage(addCurrencyMessage.replace("%c", Integer.toString(previousCurrency)).replace("%s", Integer.toString(currency)));
					break;

				case "premiumcurrency":
					int previousPremiumCurrency = profile.getCurrency();
					int premiumCurrency = previousPremiumCurrency - Integer.parseInt(argument);
					String addPremiumCurrencyMessage = Messages.COMMAND_ADMIN_REMOVE_PREMIUMCURRENCY_SUCCESS.toString();
					profile.removePremiumCurrency(Integer.parseInt(argument));
					commandSender.sendMessage(addPremiumCurrencyMessage.replace("%c", Integer.toString(previousPremiumCurrency)).replace("%s", Integer.toString(premiumCurrency)));
					break;

				case "pexp":
				case "pexperience":
					String removeXPMessage = Messages.COMMAND_ADMIN_REMOVE_EXPERIENCE_SUCCESS.toString();
					long previousExperience = profile.getExperience();
					profile.removeExperience(Integer.parseInt(argument));
					long exp = profile.getExperience();

					commandSender.sendMessage(removeXPMessage.replace("%c", Long.toString(previousExperience)).replace("%s", Long.toString(exp)));
					break;

				case "plvl":
				case "plevel":
					String addLVLMessage = Messages.COMMAND_ADMIN_REMOVE_LEVEL_SUCCESS.toString();
					int currentLevel = player.getLevel();
					profile.removeLevel(Integer.parseInt(argument));	
					int level = profile.getPlayerLevel();		

					commandSender.sendMessage(addLVLMessage.replace("%c", Integer.toString(currentLevel)).replace("%s", Integer.toString(level)));
					break;

				case "recipe":
					String msg = PLUGIN.getRecipeManager().removePlayerRecipe((Player) commandSender, argument);
					commandSender.sendMessage(msg);
					break;	

				default:
					//Send incomplete arg message.
					break;
				}

				break;
			default:
				//commandSender.sendMessage(Messages.COMMAND_ADMIN_UNKNOWN.toString());
				break;
			}		
		} else {
			//UNKNOWN OR INCOMPLETE COMMANDS
			//commandSender.sendMessage(Messages.COMMAND_ADMIN_UNKNOWN.toString());
			//TODO send player commands and arg examples
		}
		return false;
	}
}
