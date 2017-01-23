package com.forgestorm.spigotcore.player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.constants.Messages;
import com.forgestorm.spigotcore.experience.PlayerExperience;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;
import com.forgestorm.spigotcore.util.item.AttributeReader;
import com.forgestorm.spigotcore.util.item.InventoryStringDeSerializer;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;

@AllArgsConstructor
public class SetupNetworkPlayer extends BukkitRunnable {

	private SpigotCore plugin;
	private Player player;
	private PlayerProfileData profile;

	public void run() {

		//Here we are waiting on Redis to finish getting info from the
		//database. When the data has been loaded the boolean is set to true.
		//Then we finish setting up the player profile.
		if (profile.isLoaded()) {
			cancel();

			PlayerExperience expCalc = new PlayerExperience();

            //Spawn player on spawn pad.
            plugin.getTeleportSpawn().teleportSpawn(player);

			//Add the player to the Player Manager.
			plugin.getPlayerManager().addPlayerProfile(player, profile);

			//Additional profile setup.
			profile.setOperatorRank();
			profile.setUsernameRankPrefix();
			profile.setPlayerLevel(expCalc.getLevel(profile.getExperience()));

			//Reset the players compass.
			player.setCompassTarget(new Location(Bukkit.getWorlds().get(0), 0.5, 108, -24.5));

			//Setup usergroup prefixes.
			plugin.getScoreboardManager().assignPlayer(player);

			//Send per player scoreboard.
			plugin.getTarkanScoreboard().giveScoreboard(player);
			
			//Send the player a formatted tab menu.
			String header = Messages.DISPLAY_TAB_HEADER.toString().replace("%s", player.getName());
			String footer = Messages.DISPLAY_TAB_FOOTER.toString();
			plugin.getTitleManagerAPI().setHeaderAndFooter(player, header, footer);

			//If the player is on a mount. Dismount them, and remove the mount.
			if (player.isInsideVehicle()) {
				Entity mount = player.getVehicle();
				mount.eject();
				mount.remove();
			}
			
			//Deserialize player inventory.
			player.getInventory().clear();
			if (profile.getSerializedInventory() != null && !profile.getSerializedInventory().equals("")) {
				try {
					//System.out.println("LOAD InvData: " + profile.getSerializedInventory());
					ItemStack[] inventory = InventoryStringDeSerializer.stacksFromBase64(profile.getSerializedInventory());
					player.getInventory().setContents(inventory);
				} catch (IllegalArgumentException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			//Put the player in adventure mode.
			player.setGameMode(GameMode.SURVIVAL);

			//Set player health and food level.
			player.setHealth(20);
			player.setFoodLevel(20);

			//Setup player for double jump.
			player.setAllowFlight(true);
			player.setFlying(false);

			//Give the player the boss bar message.
			new PlayerBossBar(plugin, player).addPlayerBar();

			//Give player hub items.
			ItemGenerator itemGen = plugin.getItemGen();
			ItemTypes type = ItemTypes.MENU;
			ItemStack serverSelector = itemGen.generateItem("main_menu", type);
			player.getInventory().setItem(0, serverSelector);
			
			//Update player armor and weapon stats.
			AttributeReader ar = new AttributeReader(plugin, player);
			ar.readArmorAttributes(false);
			ar.readWeaponAttributes(false);
		}
	}
}
