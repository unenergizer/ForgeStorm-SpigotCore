package com.forgestorm.spigotcore.entity.mount;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityMountMule extends MountEntity {

	public EntityMountMule(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.MULE;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		Horse mule = (Horse) entity;
		mule.getInventory().setSaddle(makeSaddle());
		mule.setTamed(true);
		mule.setAdult();
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
