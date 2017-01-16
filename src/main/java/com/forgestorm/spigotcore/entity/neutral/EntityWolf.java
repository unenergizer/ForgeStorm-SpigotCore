package com.forgestorm.spigotcore.entity.neutral;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityWolf extends NeutralEntity {

	public EntityWolf(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.WOLF;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		Wolf wolf = (Wolf) entity;
		wolf.setAdult();
		wolf.setBreed(false);
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
