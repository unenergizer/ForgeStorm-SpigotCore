package com.forgestorm.spigotcore.entity.passive;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Rabbit;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityBabyRabbit extends PassiveEntity {

	public EntityBabyRabbit(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.RABBIT;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		Rabbit rabbit = (Rabbit) entity;
		rabbit.setBaby();
		rabbit.setAgeLock(true);
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
