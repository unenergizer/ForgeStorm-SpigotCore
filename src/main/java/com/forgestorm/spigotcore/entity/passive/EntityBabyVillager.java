package com.forgestorm.spigotcore.entity.passive;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityBabyVillager extends PassiveEntity {

	public EntityBabyVillager(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.VILLAGER;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		Villager villager = (Villager) entity;
		villager.setBaby();
		villager.setAgeLock(true);
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
