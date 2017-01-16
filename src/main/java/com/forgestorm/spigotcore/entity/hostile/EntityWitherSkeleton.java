package com.forgestorm.spigotcore.entity.hostile;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityWitherSkeleton extends HostileEntity {

	public EntityWitherSkeleton(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.WITHER_SKELETON;
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
