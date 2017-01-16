package com.forgestorm.spigotcore.entity.passive;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Pig;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityPig extends PassiveEntity {

	public EntityPig(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.PIG;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		Pig pig = (Pig) entity;
		pig.setAdult();
		pig.setBreed(false);
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
