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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class BungeeCord implements PluginMessageListener {

    private final SpigotCore plugin;
    private final Logger log = Logger.getLogger("Minecraft");

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


        // TODO: AFTER SAVE CONFIRMATION, CONTINUE

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
            log.warning("Could not handle BungeeCord command from " + player.getName() + ": tried to connect to \"" + server + "\".");

            return;
        }
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
        String subchannel = in.readUTF();

        if (subchannel.equals("PlayerCount")) {
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

        if (subchannel.equals("MongoUpdate")) {

            // unload profile into database
            // load from database


            String playerName = in.readUTF();

            // TODO: UPDATE DATABASE
            ColorLogger.WARNING.printLog(true, "MONGO MSG: " + playerName);
        }
    }
}
