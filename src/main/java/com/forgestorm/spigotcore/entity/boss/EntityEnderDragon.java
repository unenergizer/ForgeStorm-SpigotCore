package com.forgestorm.spigotcore.entity.boss;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import com.forgestorm.spigotcore.profile.ProfileData;



public class EntityEnderDragon extends BossEntity {

	public EntityEnderDragon(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.ENDER_DRAGON;
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
