package com.forgestorm.spigotcore.entity.neutral;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityIronGolem extends NeutralEntity {

	public EntityIronGolem(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.IRON_GOLEM;
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
