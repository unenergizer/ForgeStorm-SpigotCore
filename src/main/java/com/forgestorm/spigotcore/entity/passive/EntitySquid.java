package com.forgestorm.spigotcore.entity.passive;

import com.forgestorm.spigotcore.profile.monster.MonsterProfileData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class EntitySquid extends PassiveEntity {

	public EntitySquid(String name, int level, Location location, MonsterProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.SQUID;
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
