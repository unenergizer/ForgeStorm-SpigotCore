package com.forgestorm.spigotcore.entity.mount;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityMountSkeletonHorse extends MountEntity {

	public EntityMountSkeletonHorse(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.SKELETON_HORSE;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		Horse horse = (Horse) entity;
		horse.getInventory().setSaddle(makeSaddle());
		horse.setTamed(true);
		horse.setAdult();
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
