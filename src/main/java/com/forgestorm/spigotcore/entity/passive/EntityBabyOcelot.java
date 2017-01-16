package com.forgestorm.spigotcore.entity.passive;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Ocelot;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityBabyOcelot extends PassiveEntity {

	public EntityBabyOcelot(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.OCELOT;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		Ocelot ocelot = (Ocelot) entity;
		ocelot.setBaby();
		ocelot.setAgeLock(true);
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
