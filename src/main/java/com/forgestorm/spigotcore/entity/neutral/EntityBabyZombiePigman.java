package com.forgestorm.spigotcore.entity.neutral;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PigZombie;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityBabyZombiePigman extends NeutralEntity {

	public EntityBabyZombiePigman(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.PIG_ZOMBIE;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		PigZombie pigZombie = (PigZombie) entity;
		pigZombie.setBaby(true);
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
