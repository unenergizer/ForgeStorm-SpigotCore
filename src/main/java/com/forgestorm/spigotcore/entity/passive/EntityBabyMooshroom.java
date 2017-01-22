package com.forgestorm.spigotcore.entity.passive;

import com.forgestorm.spigotcore.profile.monster.MonsterProfileData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.MushroomCow;

public class EntityBabyMooshroom extends PassiveEntity {

	public EntityBabyMooshroom(String name, int level, Location location, MonsterProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.MUSHROOM_COW;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		MushroomCow mushroomCow = (MushroomCow) entity;
		mushroomCow.setBaby();
		mushroomCow.setAgeLock(true);
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
