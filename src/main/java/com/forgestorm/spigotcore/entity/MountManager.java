package com.forgestorm.spigotcore.entity;

import com.forgestorm.spigotcore.entity.mount.MountEntity;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

@Getter
public class MountManager {

	private final Map<Player, RPGEntity> playerMounts = new HashMap<>();
	
	private void mountPlayer(Player player) {
		MountEntity entity = (MountEntity) playerMounts.get(player);
		entity.spawn();
		entity.setOwner(player);
		entity.getEntity().setPassenger(player);
	}
	
	boolean addPlayerMount(Player player, RPGEntity entity) {
		if (!playerMounts.containsKey(player)) {
			playerMounts.put(player, entity);
			mountPlayer(player);
			return true;
		}
		return false;
	}
	
	public boolean removePlayerMount(Player player) {
		if (!playerMounts.containsKey(player)) {
			playerMounts.get(player).toggleDeath();
			playerMounts.remove(player);
			return true;
		}
		return false;
	}
	
	public boolean removeMount(UUID uuid) {
		for (Entry<Player, RPGEntity> entry: playerMounts.entrySet()) {
			if (entry.getValue().getEntity().getUniqueId().equals(uuid)) {
				entry.getValue().toggleDeath();
				playerMounts.remove(entry.getKey());
				return true;
			}
		}
		return false;
	}
	
	public boolean containsMount(UUID uuid) {
		for (Entry<Player, RPGEntity> entry: playerMounts.entrySet()) {
			if (entry.getValue().getEntity().getUniqueId().equals(uuid)) {
				return true;
			}
		}
		return false;
	}
}
