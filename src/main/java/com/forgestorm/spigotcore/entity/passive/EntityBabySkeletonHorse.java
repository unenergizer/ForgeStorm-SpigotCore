package com.forgestorm.spigotcore.entity.passive;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityBabySkeletonHorse extends PassiveEntity {

	public EntityBabySkeletonHorse(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.SKELETON_HORSE;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		Horse horse = (Horse) entity;
		horse.setBaby();
		horse.setAgeLock(true);
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
