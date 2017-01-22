package com.forgestorm.spigotcore.entity.neutral;

import com.forgestorm.spigotcore.profile.monster.MonsterProfileData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.PolarBear;

public class EntityBabyPolarBear extends NeutralEntity {

	public EntityBabyPolarBear(String name, int level, Location location, MonsterProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.POLAR_BEAR;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		PolarBear bear = (PolarBear) entity;
		bear.setBaby();
		bear.setAgeLock(true);
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
