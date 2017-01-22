package com.forgestorm.spigotcore.entity.neutral;

import com.forgestorm.spigotcore.profile.monster.MonsterProfileData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Wolf;

public class EntityWolf extends NeutralEntity {

	public EntityWolf(String name, int level, Location location, MonsterProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.WOLF;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		Wolf wolf = (Wolf) entity;
		wolf.setAdult();
		wolf.setBreed(false);
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
