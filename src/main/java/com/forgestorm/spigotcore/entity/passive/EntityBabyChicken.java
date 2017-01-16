package com.forgestorm.spigotcore.entity.passive;

import org.bukkit.Location;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityBabyChicken extends PassiveEntity {

	public EntityBabyChicken(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);

		entityType = EntityType.CHICKEN;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		Chicken babyChicken = (Chicken) entity;
		babyChicken.setBaby();
		babyChicken.setAgeLock(true);
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
