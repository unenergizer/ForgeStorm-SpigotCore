package com.forgestorm.spigotcore.entity.passive;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Giant;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityGiant extends PassiveEntity {

	public EntityGiant(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.GIANT;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		Giant giant = (Giant) entity;
		giant.setAI(true);
		
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
