package com.forgestorm.spigotcore.world.instance;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerRealmData {
	
	private UUID player;
	private UUID realmOwner;
	private Location joinLocation;
	private int timeTillJoin;
	private int invincibilityTimeLeft;
	
	public PlayerRealmData(Player player, UUID realmOwner, Location joinLocation) {
		this.player = player.getUniqueId();
		this.realmOwner = realmOwner;
		this.joinLocation = joinLocation;
		
		timeTillJoin = 5;
		invincibilityTimeLeft = 15;
	}
}