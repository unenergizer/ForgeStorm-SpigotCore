package com.forgestorm.spigotcore.entity.hostile;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityGuardian extends HostileEntity {

	public EntityGuardian(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.GUARDIAN;
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
