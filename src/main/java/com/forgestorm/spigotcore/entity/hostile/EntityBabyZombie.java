package com.forgestorm.spigotcore.entity.hostile;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityBabyZombie extends HostileEntity {

	public EntityBabyZombie(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.ZOMBIE;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		Zombie zombie = (Zombie) entity;
		zombie.setBaby(true);
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
