package com.forgestorm.spigotcore.bungeecord;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.Messages;
import com.forgestorm.spigotcore.menus.GameSelectionMenu;
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

	private final SpigotCore PLUGIN;
	private final Logger log = Logger.getLogger("Minecraft");

	public BungeeCord(SpigotCore plugin) {
		PLUGIN = plugin;
	}

	public void connectToBungeeServer(Player player, String server) {
		//Send connection message.
		player.sendMessage(Messages.BUNGEECORD_CONNECT_SERVER.toString().replace("%s", server));

		try {
			Messenger messenger = Bukkit.getMessenger();
			if (!messenger.isOutgoingChannelRegistered(PLUGIN, "BungeeCord")) {
				messenger.registerOutgoingPluginChannel(PLUGIN, "BungeeCord");
			}

			if (server.length() == 0) {
				player.sendMessage("&cThe server name was empty!");
				return;
			}

			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(byteArray);

			out.writeUTF("Connect");
			out.writeUTF(server);

			player.sendPluginMessage(PLUGIN, "BungeeCord", byteArray.toByteArray());

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

			player.sendPluginMessage(PLUGIN, "BungeeCord", byteArray.toByteArray());
			
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
			
			switch(server) {
			case "mg-full-01":
				((GameSelectionMenu) PLUGIN.getGameSelectionMenu()).setArcadePlayers(playerCount);
				break;
			case "creative":
				((GameSelectionMenu) PLUGIN.getGameSelectionMenu()).setCreativePlayers(playerCount);
				break;
			}
		}
	}
}
