package com.forgestorm.spigotcore.entity.passive;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntitySheep extends PassiveEntity {

	public EntitySheep(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.SHEEP;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		Sheep sheep = (Sheep) entity;
		sheep.setAdult();
		sheep.setBreed(false);
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
