package com.forgestorm.spigotcore.entity.boss;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import com.forgestorm.spigotcore.profile.ProfileData;



public class EntityWither extends BossEntity {

	public EntityWither(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.WITHER;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
