package com.forgestorm.spigotcore.redis;

import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.help.LocationTrackingManager;
import com.forgestorm.spigotcore.help.Tutorial;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;
import com.forgestorm.spigotcore.util.item.InventoryStringDeSerializer;
import com.forgestorm.spigotcore.util.player.DeletePlayerFiles;
import com.forgestorm.spigotcore.world.instance.RealmManager;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RemoveNetworkPlayer {

	private final SpigotCore PLUGIN;
	
	public void removeNetworkPlayer(Player player) {
		RedisProfileManager profileManager = PLUGIN.getProfileManager();
		PlayerProfileData profile = PLUGIN.getProfileManager().getProfile(player);
		Tutorial tutorial = PLUGIN.getTutorial();
		LocationTrackingManager tracker = PLUGIN.getLocationTrackingManager();
		RealmManager prm = PLUGIN.getRealmManager();
		
		//Remove the player from the active tutorial.
		if (tutorial.getActivePlayers().containsKey(player)) {
			tutorial.endTutorial(player, true);
		}
		
		//Remove Scoreboard
		PLUGIN.getScoreboardManager().removePlayer(player);
		PLUGIN.getPuhaScoreboard().removeScoreboard(player);
		
		//Remove player from tracking list.
		tracker.removePlayer(player);
		
		//Serialize Inventory
		profile.setSerializedInventory(InventoryStringDeSerializer.toBase64(player.getInventory().getContents()));
		//System.out.println("SAVE InvData: " + profile.getSerializedInventory());
		
		//Close profile
		profile.setCurrentMenu(null);
		profileManager.unloadProfile(player);
		
		//Remove player world data.
		new DeletePlayerFiles(PLUGIN).deleteSaveFiles(player);

		//Remove them from a players realm.
		if (prm.getPlayerData().containsKey(player)) {
			//Set new logout location.
			//profile.setLocation(prm.getPlayerData().get(player).getJoinLocation());
			prm.getPlayerData().remove(player);
		}

		//Remove/unload player realm
		prm.removePlayerRealm(player);
		
		//Remove the players mount and HashMap entry.
		if (PLUGIN.getMountManager().getPlayerMounts().containsKey(player)) {
			PLUGIN.getMountManager().removePlayerMount(player);
		}
	}
}
