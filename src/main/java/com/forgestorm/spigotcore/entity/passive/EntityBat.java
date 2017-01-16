package com.forgestorm.spigotcore.entity.passive;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityBat extends PassiveEntity {

	public EntityBat(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.BAT;
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
