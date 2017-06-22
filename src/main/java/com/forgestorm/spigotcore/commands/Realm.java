package com.forgestorm.spigotcore.commands;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.CommonSounds;
import com.forgestorm.spigotcore.constants.SpigotCoreMessages;
import com.forgestorm.spigotcore.menus.realm.RealmMainMenu;
import com.forgestorm.spigotcore.menus.realm.RealmShopPage1;
import com.forgestorm.spigotcore.realm.RealmManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Realm implements CommandExecutor {

    private final SpigotCore plugin;
    private final ChatColor cmd = ChatColor.DARK_AQUA;
    private final ChatColor white = ChatColor.WHITE;
    private final ChatColor txt = ChatColor.GRAY;

    public Realm(SpigotCore plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

        if (!(commandSender instanceof Player)) {
            return false;
        }
        Player player = (Player) commandSender;
        RealmManager realmManager = plugin.getRealmManager();

        if (args.length == 0) {
            new RealmMainMenu(plugin).open(player);
        }

        if (args.length == 1) {
            switch (args[0].toLowerCase()) {
                case "help":
                    showCommandList(player);
                    break;
                case "reset":
                    realmManager.resetRealm(player);
                    break;
                case "shop":
                    new RealmShopPage1(plugin).open(player);
                    break;
                case "upgrade":
                    player.sendMessage(ChatColor.RED + "Upgrading realms coming soon.");
                    CommonSounds.ACTION_FAILED.playSound(player);
                    break;
                default:
                    showCommandList(player);
                    break;
            }
        }

        if (args.length == 2) {

            // Make sure the realm is open first...
            if (!realmManager.hasOpenRealm(player)) {
                player.sendMessage(ChatColor.RED + "You must open your realm before you can do this.");
                CommonSounds.ACTION_FAILED.playSound(player);
                return false;
            }

            if (Bukkit.getPlayer(args[1]) == null) {
                player.sendMessage(ChatColor.RED + "We cannot find a player named " + ChatColor.YELLOW + args[1] + ChatColor.RED + ".");
                player.sendMessage(ChatColor.RED + "Please check the spelling and make sure your friend is on the same server.");
                CommonSounds.ACTION_FAILED.playSound(player);
                return false;
            }

            Player friend = Bukkit.getPlayer(args[1]);

            switch (args[0].toLowerCase()) {
                case "invite":
                    plugin.getRealmManager().getPlayerRealm(player).addFriend(friend);
                    break;
                case "uninvite":
                    plugin.getRealmManager().getPlayerRealm(player).removeFriend(friend);
                    break;
                default:
                    showCommandList(player);
                    break;
            }
        }

        // Set realm title
        if (args.length > 2) {
            if (args[0].toLowerCase().equals("title")) {
                String title = "";

                for (String arg : args) {
                    if (arg.equals("title")) continue;
                    title = title.concat(arg + " ");
                }

                realmManager.setTitle(player, title);
                player.sendMessage(ChatColor.GREEN + "Your realm title message has been changed to:");
                player.sendMessage(ChatColor.YELLOW + title);
                CommonSounds.ACTION_SUCCESS.playSound(player);
            } else {
                showCommandList(player);
            }
        }
        return true;
    }

    private void showCommandList(Player player) {
        player.sendMessage(SpigotCoreMessages.BAR_REALM.toString());
        player.sendMessage("");
        player.sendMessage(cmd + "/realm" + white + " - " + txt + "Opens the realm menu.");
        player.sendMessage(cmd + "/realm shop" + white + " - " + txt + "Opens the realm shop menu.");
        player.sendMessage(cmd + "/realm upgrade" + white + " - " + txt + "Upgrade realm size and build region.");
        player.sendMessage(cmd + "/realm title <message>" + white + " - " + txt + "Set realm join message.");
        player.sendMessage(cmd + "/realm invite <player>" + white + " - " + txt + "Invite friends to build.");
        player.sendMessage(cmd + "/realm uninvite <player>" + white + " - " + txt + "Stop friends from building.");
        player.sendMessage(cmd + "/realm help" + white + " - " + txt + "Shows realm commands.");
        player.sendMessage(cmd + "/realm reset" + white + " - " + txt + "Resets realm. Warning! Cannot be undone.");
        player.sendMessage(SpigotCoreMessages.BAR_BOTTOM.toString());
    }
}
