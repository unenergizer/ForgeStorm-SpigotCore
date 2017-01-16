package com.forgestorm.spigotcore.entity.neutral;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PolarBear;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityBabyPolarBear extends NeutralEntity {

	public EntityBabyPolarBear(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.POLAR_BEAR;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		PolarBear bear = (PolarBear) entity;
		bear.setBaby();
		bear.setAgeLock(true);
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
