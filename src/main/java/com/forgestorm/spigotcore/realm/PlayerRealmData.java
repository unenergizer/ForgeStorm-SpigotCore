package com.forgestorm.spigotcore.realm;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
@Setter
public class PlayerRealmData {
	
	private UUID player;
	private UUID realmOwner;
	private Location joinLocation;
	private int timeTillJoin;
	private int invincibilityTimeLeft;
	
	PlayerRealmData(Player player, UUID realmOwner, Location joinLocation) {
		this.player = player.getUniqueId();
		this.realmOwner = realmOwner;
		this.joinLocation = joinLocation;

        timeTillJoin = 5;
        invincibilityTimeLeft = 15;
    }
}