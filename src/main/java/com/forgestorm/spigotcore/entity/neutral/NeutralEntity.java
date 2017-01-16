package com.forgestorm.spigotcore.entity.neutral;

import org.bukkit.Location;

import com.forgestorm.spigotcore.entity.RPGEntity;
import com.forgestorm.spigotcore.profile.ProfileData;

public abstract class NeutralEntity extends RPGEntity {

	public NeutralEntity(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
	}
}
