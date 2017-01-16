package com.forgestorm.spigotcore.entity.mount;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityMountDonkey extends MountEntity {

	public EntityMountDonkey(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.DONKEY;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		Horse donkey = (Horse) entity;
		donkey.getInventory().setSaddle(makeSaddle());
		donkey.setTamed(true);
		donkey.setAdult();
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
