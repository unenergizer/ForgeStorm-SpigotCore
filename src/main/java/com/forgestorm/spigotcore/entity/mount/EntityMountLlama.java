package com.forgestorm.spigotcore.entity.mount;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Llama;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntityMountLlama extends MountEntity {

	public EntityMountLlama(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.LLAMA;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		Llama llama = (Llama) entity;
		llama.setTamed(true);
		llama.setAdult();
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
