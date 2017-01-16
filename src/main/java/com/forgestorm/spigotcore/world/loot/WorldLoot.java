package com.forgestorm.spigotcore.world.loot;

import org.bukkit.Location;

public abstract class WorldLoot {
	
	public abstract void setWorldLoot(Location location);
	public abstract void setGiveReward(Location location);
	
	public void respawn() {
		
	}
}
