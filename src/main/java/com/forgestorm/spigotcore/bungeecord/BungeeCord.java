package com.forgestorm.spigotcore.bungeecord;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.SpigotCoreMessages;
import com.forgestorm.spigotcore.menus.GameSelectionMenu;
import com.forgestorm.spigotcore.player.PlayerManager;
import com.forgestorm.spigotcore.util.logger.ColorLogger;
import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.Messenger;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BungeeCord implements PluginMessageListener {

    private final SpigotCore plugin;

    public BungeeCord(SpigotCore plugin) {

        this.plugin = plugin;
    }

    public void connectToBungeeServer(Player player, String server) {
        //Send connection message.
        player.sendMessage("");
        player.sendMessage(SpigotCoreMessages.BUNGEECORD_CONNECT_SERVER.toString().replace("%s", server));

        // TODO: SAVE PROFILE
        plugin.getProfileManager().getProfile(player).setSavingData(true);
        PlayerManager playerManager = plugin.getPlayerManager();


        playerManager.saveProfileData(player);
        playerManager.removeNetworkPlayer(player);

        new BukkitRunnable() {

            @Override
            public void run() {
                // We wait for mongo to save.
                //TODO: This is not really ideal. We should get a call back instead and then continue.

                try {
                    Messenger messenger = Bukkit.getMessenger();
                    if (!messenger.isOutgoingChannelRegistered(plugin, "BungeeCord")) {
                        messenger.registerOutgoingPluginChannel(plugin, "BungeeCord");
                    }

                    if (server.length() == 0) {
                        player.sendMessage("&cThe server name was empty!");
                        return;
                    }

                    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                    DataOutputStream out = new DataOutputStream(byteArray);

                    out.writeUTF("Connect");
                    out.writeUTF(server);

                    player.sendPluginMessage(plugin, "BungeeCord", byteArray.toByteArray());

                } catch (Exception ex) {
                    ex.printStackTrace();
                    ColorLogger.FATAL.printLog("Could not handle BungeeCord command from " + player.getName() + ": tried to connect to \"" + server + "\".");

                    return;
                }
            }
        }.runTaskLater(plugin, 20 * 3); //3 seconds
    }

    public void getPlayerCount(String serverName) {
        try {

            ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(byteArray);
            Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);

            out.writeUTF("PlayerCount");
            out.writeUTF(serverName);

            player.sendPluginMessage(plugin, "BungeeCord", byteArray.toByteArray());

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();

        if (subChannel.equals("PlayerCount")) {
            String server = in.readUTF(); // Name of server, as given in the arguments
            int playerCount = in.readInt();

            switch (server) {
                case "mg-full-01":
                    ((GameSelectionMenu) plugin.getGameSelectionMenu()).setArcadePlayers(playerCount);
                    break;
                case "creative":
                    ((GameSelectionMenu) plugin.getGameSelectionMenu()).setCreativePlayers(playerCount);
                    break;
            }
        }
    }
}
