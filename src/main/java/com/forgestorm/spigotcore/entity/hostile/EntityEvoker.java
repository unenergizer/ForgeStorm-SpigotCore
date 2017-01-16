package com.forgestorm.spigotcore.entity.hostile;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityEvoker extends HostileEntity {

	public EntityEvoker(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.EVOKER;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
