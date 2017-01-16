package com.forgestorm.spigotcore.entity.hostile;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import com.forgestorm.spigotcore.profile.ProfileData;

public class EntitySlime extends HostileEntity {

	public EntitySlime(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.SLIME;
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
