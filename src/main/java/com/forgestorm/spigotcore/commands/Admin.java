package com.forgestorm.spigotcore.commands;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ChatIcons;
import com.forgestorm.spigotcore.constants.SpigotCoreMessages;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import com.forgestorm.spigotcore.experience.Experience;
import com.forgestorm.spigotcore.experience.ProfessionExperience;
import com.forgestorm.spigotcore.menus.RecipeOptionsMenu;
import com.forgestorm.spigotcore.menus.realm.RealmShopPage1;
import com.forgestorm.spigotcore.util.imgmessage.EzImgMessage;
import com.forgestorm.spigotcore.util.math.TimeUnit;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@AllArgsConstructor
public class Admin implements CommandExecutor {

    private final SpigotCore plugin;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        //Check if the command sender is a server Operator
        if ((commandSender instanceof Player) && !plugin.getProfileManager().getProfile((Player) commandSender).isAdmin()) {
            commandSender.sendMessage(SpigotCoreMessages.NO_PERMISSION.toString());
            return false;
        }

        //Check command args
        if (args.length == 1) {

            switch (args[0].toLowerCase()) {
                case "tpegg":
                    plugin.getDragonEggTP().teleportToEgg((Player) commandSender);
                    break;

                case "setegg":
                    Player player = (Player) commandSender;
                    plugin.getDragonEggTP().addLocation(player);
                    break;

                case "crafting":
                    new RecipeOptionsMenu(plugin).open((Player) commandSender);

                    List<ItemStack> recipeItems = plugin.getRecipeManager().getRecipeIngredients("Recipe01");

                    for (ItemStack item : recipeItems) {
                        ((Player) commandSender).getInventory().addItem(item);
                    }
                    break;

                case "uptime":
                    long currentTime = System.currentTimeMillis() / 1000;
                    long totalTime = currentTime - plugin.getStartTime();
                    commandSender.sendMessage(ChatColor.GREEN + "Server Up Time" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + TimeUnit.toString(totalTime) + ChatColor.DARK_GRAY + ".");
                    break;

                case "status": //Shows the current stats to the administrator.
                    int realms = plugin.getRealmManager().getLoadedRealms().size();
                    int armorStands = 0;
                    long statusCurrentTime = System.currentTimeMillis() / 1000;
                    long statusTotalTime = statusCurrentTime - plugin.getStartTime();

                    //Get the number of living entities.
                    for (Entity entity : ((Player) commandSender).getWorld().getEntities()) {
                        if (entity instanceof LivingEntity) {
                            if (!(entity instanceof Player)) {
                                if (entity instanceof ArmorStand) {
                                    armorStands++;
                                }
                            }
                        }
                    }

                    //Send SpigotCoreMessages!
                    commandSender.sendMessage(" ");
                    commandSender.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "/-- SERVER STATS --/");
                    commandSender.sendMessage(ChatColor.GREEN + "Server Up Time" + ChatColor.DARK_GRAY + ": " + ChatColor.YELLOW + TimeUnit.toString(statusTotalTime) + ChatColor.DARK_GRAY + ".");
                    commandSender.sendMessage(ChatColor.GREEN + "Players Online: " + ChatColor.YELLOW + Bukkit.getOnlinePlayers().size());
                    commandSender.sendMessage(ChatColor.GREEN + "Holograms: " + ChatColor.YELLOW + armorStands);
                    commandSender.sendMessage(ChatColor.GREEN + "Worlds Loaded: " + ChatColor.YELLOW + Bukkit.getWorlds().size());
                    commandSender.sendMessage(ChatColor.GREEN + "RealmsLoaded: " + ChatColor.YELLOW + realms);
                    break;

                case "shop":
                    new RealmShopPage1(plugin).open((Player) commandSender);
                    break;
            }

        } else if (args.length == 2) {

            switch (args[0].toLowerCase()) {
                case "chaticon":
                    System.out.println("Sending chat icon...");
                    commandSender.sendMessage("");
                    new EzImgMessage().sendEzImgMessage((Player) commandSender, ChatIcons.valueOf(args[1].toUpperCase()), "This is test text!");
                    break;
            }

        } else if (args.length == 3) {

            if (Bukkit.getPlayer(args[2]) == null) {
                commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_PLAYER_NOT_ONLINE.toString());
                return false;
            }

            Player player = Bukkit.getPlayer(args[2]);
            PlayerProfileData profile = plugin.getProfileManager().getProfile(player);

            switch (args[0].toLowerCase()) {
                case "demote":

                    //Check which type of demote arg to use.
                    switch (args[1].toLowerCase()) {
                        case "admin":
                        case "administrator":
                            profile.demoteAdmin();
                            commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_DEMOTE_ADMIN_SUCCESS.toString());
                            break;

                        case "mod":
                        case "moderator":
                            profile.demoteMod();
                            commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_DEMOTE_MODERATOR_SUCCESS.toString());
                            break;

                        case "usergroup":
                            if (profile.demote()) {
                                commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_DEMOTE_USERGROUP_SUCCESS.toString());
                            } else {
                                commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_DEMOTE_USERGROUP_FAILURE.toString());
                            }

                            break;
                        default:
                            //Send incomplete arg message.
                            break;
                    }
                    break;
                case "promote":

                    //Check which type of promote arg to use.
                    switch (args[1].toLowerCase()) {
                        case "admin":
                        case "administrator":
                            profile.promoteAdmin();
                            commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_PROMOTE_ADMIN_SUCCESS.toString());
                            break;

                        case "mod":
                        case "moderator":
                            profile.promoteMod();
                            commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_PROMOTE_MODERATOR_SUCCESS.toString());
                            break;

                        case "usergroup":
                            profile.promote();
                            commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_PROMOTE_USERGROUP_SUCCESS.toString());
                            break;

                        default:
                            //Send incomplete arg message.
                            break;
                    }
                    break;
                case "addLantern":
                    WorldEditPlugin worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
                    Selection selection = worldEdit.getSelection((Player) commandSender);

                    if (selection != null) {
                        Location min = selection.getMinimumPoint();
                        Location max = selection.getMaximumPoint();

                        if (min == max) {
                            plugin.getLantern().addLantern(min, args[1], args[2]);
                        } else {
                            commandSender.sendMessage(ChatColor.RED + "Your First and Second WorldEdit selection must match.");
                        }
                    } else {
                        commandSender.sendMessage(ChatColor.RED + "Your must make a WorldEdit selection.");
                    }
                    break;
                case "addchestloot":
                    WorldEditPlugin worldEditt = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
                    Selection selectionn = worldEditt.getSelection((Player) commandSender);

                    if (selectionn != null) {
                        Location min = selectionn.getMinimumPoint();
                        Location max = selectionn.getMaximumPoint();

                        if (min == max) {
                            plugin.getChestLoot().addChestLocation(player, min);
                        } else {
                            commandSender.sendMessage(ChatColor.RED + "Your First and Second WorldEdit selection must match.");
                        }
                    } else {
                        commandSender.sendMessage(ChatColor.RED + "Your must make a WorldEdit selection.");
                    }
                    break;
                default:
                    //commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_UNKNOWN.toString());
                    break;
            }
        } else if (args.length == 4) {

            if (Bukkit.getPlayer(args[2]) == null) {
                commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_PLAYER_NOT_ONLINE.toString());
                return false;
            }

            Player player = Bukkit.getPlayer(args[2]);
            PlayerProfileData profile = plugin.getProfileManager().getProfile(player);
            String argument = args[3];

            Experience professionExperience = new ProfessionExperience();

            //if statement
            //check if arg 4 is a number

            switch (args[0].toLowerCase()) {
                case "set":

                    //Check which type of arg to use.
                    switch (args[1].toLowerCase()) {
                        case "usergroup":
                            profile.setUserGroup(Integer.parseInt(argument));
                            commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_SET_USERGROUP_SUCCESS.toString().replace("%s", argument));
                            break;

                        case "currency":
                            profile.setCurrency(Integer.parseInt(argument));
                            int currency = profile.getCurrency();
                            commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_SET_CURRENCY_SUCCESS.toString().replace("%s", Integer.toString(currency)));
                            break;

                        case "premiumcurrency":
                            profile.setPremiumCurrency(Integer.parseInt(argument));
                            int premiumCurrency = profile.getPremiumCurrency();
                            commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_SET_PREMIUM_CURRENCY_SUCCESS.toString().replace("%s", Integer.toString(premiumCurrency)));
                            break;

                        //PLAYER EXP
                        case "pexp":
                        case "pexperience":
                            profile.setExperience(Integer.parseInt(argument));
                            long exp = profile.getExperience();
                            commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_SET_EXPERIENCE_SUCCESS.toString().replace("%s", Integer.toString((int) exp)));
                            break;

                        case "plvl":
                        case "plevel":
                            profile.setLevel(Integer.parseInt(argument));
                            int level = profile.getPlayerLevel();
                            commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_SET_LEVEL_SUCCESS.toString().replace("%s", Integer.toString(level)));
                            break;

                        //FARMING EXP
                        case "fexp":
                        case "fexperience":
                            profile.setFarmingExperience(Integer.parseInt(argument + professionExperience.getExpOffSet()));
                            long fexp = profile.getFarmingExperience();
                            commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_SET_EXPERIENCE_SUCCESS.toString().replace("%s", Integer.toString((int) fexp)));
                            break;

                        case "flvl":
                        case "flevel":
                            profile.setFarmingExperience(professionExperience.getExperience(Integer.parseInt(argument)));
                            int flevel = new ProfessionExperience().getLevel(profile.getFarmingExperience());
                            commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_SET_LEVEL_SUCCESS.toString().replace("%s", Integer.toString(flevel)));
                            break;

                        //MINING EXP
                        case "mexp":
                        case "mexperience":
                            profile.setMiningExperience(Integer.parseInt(argument + professionExperience.getExpOffSet()));
                            long mexp = profile.getMiningExperience();
                            commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_SET_EXPERIENCE_SUCCESS.toString().replace("%s", Integer.toString((int) mexp)));
                            break;

                        case "mlvl":
                        case "mlevel":
                            profile.setMiningExperience(professionExperience.getExperience(Integer.parseInt(argument)));
                            int mlevel = new ProfessionExperience().getLevel(profile.getMiningExperience());
                            commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_SET_LEVEL_SUCCESS.toString().replace("%s", Integer.toString(mlevel)));
                            break;

                        //WOOD CUTTING EXP
                        case "wcexp":
                        case "wcexperience":
                            profile.setMiningExperience(Integer.parseInt(argument + professionExperience.getExpOffSet()));
                            long wcexp = profile.getMiningExperience();
                            commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_SET_EXPERIENCE_SUCCESS.toString().replace("%s", Integer.toString((int) wcexp)));
                            break;

                        case "wclvl":
                        case "wclevel":
                            profile.setLumberjackExperience(professionExperience.getExperience(Integer.parseInt(argument)));
                            int wclevel = new ProfessionExperience().getLevel(profile.getLumberjackExperience());
                            commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_SET_LEVEL_SUCCESS.toString().replace("%s", Integer.toString(wclevel)));
                            break;

                        default:
                            //Send incomplete arg message.
                            break;
                    }
                    break;
                case "add":

                    //Check which type of arg to use.
                    switch (args[1].toLowerCase()) {
                        case "currency":
                            int previousCurrency = profile.getCurrency();
                            String addCurrencyMessage = SpigotCoreMessages.COMMAND_ADMIN_ADD_CURRENCY_SUCCESS.toString();
                            profile.addCurrency(Integer.parseInt(argument));
                            int currency = profile.getCurrency();

                            commandSender.sendMessage(addCurrencyMessage.replace("%c", Integer.toString(previousCurrency)).replace("%s", Integer.toString(currency)));
                            break;

                        case "premiumcurrency":
                            int previousPremiumCurrency = profile.getCurrency();
                            String addPremiumCurrencyMessage = SpigotCoreMessages.COMMAND_ADMIN_ADD_PREMIUM_CURRENCY_SUCCESS.toString();
                            profile.addPremiumCurrency(Integer.parseInt(argument));
                            int premiumCurrency = profile.getPremiumCurrency();

                            commandSender.sendMessage(addPremiumCurrencyMessage.replace("%c", Integer.toString(previousPremiumCurrency)).replace("%s", Integer.toString(premiumCurrency)));
                            break;

                        case "pexp":
                        case "pexperience":
                            String addXPMessage = SpigotCoreMessages.COMMAND_ADMIN_ADD_EXPERIENCE_SUCCESS.toString();
                            long previousExperience = profile.getExperience();
                            profile.addExperience(Integer.parseInt(argument));
                            long exp = profile.getExperience();

                            commandSender.sendMessage(addXPMessage.replace("%c", Long.toString(previousExperience)).replace("%s", Long.toString(exp)));
                            break;

                        case "plvl":
                        case "plevel":
                            String addLVLMessage = SpigotCoreMessages.COMMAND_ADMIN_ADD_LEVEL_SUCCESS.toString();
                            int currentLevel = player.getLevel();
                            profile.addLevel(Integer.parseInt(argument));
                            int level = profile.getPlayerLevel();

                            commandSender.sendMessage(addLVLMessage.replace("%c", Integer.toString(currentLevel)).replace("%s", Integer.toString(level)));
                            break;

                        case "recipe":
                            String msg = plugin.getRecipeManager().givePlayerRecipe(player, argument);
                            commandSender.sendMessage(msg);
                            break;

                        default:
                            //Send incomplete arg message.
                            break;
                    }

                    break;
                case "remove":

                    //Check which type of arg to use.
                    switch (args[1].toLowerCase()) {
                        case "currency":
                            int previousCurrency = profile.getCurrency();
                            int currency = previousCurrency - Integer.parseInt(argument);
                            String addCurrencyMessage = SpigotCoreMessages.COMMAND_ADMIN_REMOVE_CURRENCY_SUCCESS.toString();
                            profile.removeCurrency(Integer.parseInt(argument));
                            commandSender.sendMessage(addCurrencyMessage.replace("%c", Integer.toString(previousCurrency)).replace("%s", Integer.toString(currency)));
                            break;

                        case "premiumcurrency":
                            int previousPremiumCurrency = profile.getCurrency();
                            int premiumCurrency = previousPremiumCurrency - Integer.parseInt(argument);
                            String addPremiumCurrencyMessage = SpigotCoreMessages.COMMAND_ADMIN_REMOVE_PREMIUM_CURRENCY_SUCCESS.toString();
                            profile.removePremiumCurrency(Integer.parseInt(argument));
                            commandSender.sendMessage(addPremiumCurrencyMessage.replace("%c", Integer.toString(previousPremiumCurrency)).replace("%s", Integer.toString(premiumCurrency)));
                            break;

                        case "pexp":
                        case "pexperience":
                            String removeXPMessage = SpigotCoreMessages.COMMAND_ADMIN_REMOVE_EXPERIENCE_SUCCESS.toString();
                            long previousExperience = profile.getExperience();
                            profile.removeExperience(Integer.parseInt(argument));
                            long exp = profile.getExperience();

                            commandSender.sendMessage(removeXPMessage.replace("%c", Long.toString(previousExperience)).replace("%s", Long.toString(exp)));
                            break;

                        case "plvl":
                        case "plevel":
                            String addLVLMessage = SpigotCoreMessages.COMMAND_ADMIN_REMOVE_LEVEL_SUCCESS.toString();
                            int currentLevel = player.getLevel();
                            profile.removeLevel(Integer.parseInt(argument));
                            int level = profile.getPlayerLevel();

                            commandSender.sendMessage(addLVLMessage.replace("%c", Integer.toString(currentLevel)).replace("%s", Integer.toString(level)));
                            break;

                        case "recipe":
                            String msg = plugin.getRecipeManager().removePlayerRecipe((Player) commandSender, argument);
                            commandSender.sendMessage(msg);
                            break;

                        default:
                            //Send incomplete arg message.
                            break;
                    }

                    break;
                default:
                    //commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_UNKNOWN.toString());
                    break;
            }
        } else {
            //UNKNOWN OR INCOMPLETE COMMANDS
            //commandSender.sendMessage(SpigotCoreMessages.COMMAND_ADMIN_UNKNOWN.toString());
            //TODO send player commands and arg examples
        }
        return false;
    }
}
