package com.forgestorm.spigotcore.entity.passive;

import com.forgestorm.spigotcore.profile.monster.MonsterProfileData;
import org.bukkit.Location;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;

public class EntityCow extends PassiveEntity {

	public EntityCow(String name, int level, Location location, MonsterProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.COW;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		Cow cow = (Cow) entity;
		cow.setAdult();
		cow.setBreed(false);
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
