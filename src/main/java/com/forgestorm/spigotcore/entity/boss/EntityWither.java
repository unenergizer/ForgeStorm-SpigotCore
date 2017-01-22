package com.forgestorm.spigotcore.entity.boss;

import com.forgestorm.spigotcore.profile.monster.MonsterProfileData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;



public class EntityWither extends BossEntity {

	public EntityWither(String name, int level, Location location, MonsterProfileData profile) {
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
