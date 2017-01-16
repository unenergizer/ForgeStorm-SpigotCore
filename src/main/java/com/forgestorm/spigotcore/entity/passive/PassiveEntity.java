package com.forgestorm.spigotcore.entity.passive;

import org.bukkit.Location;

import com.forgestorm.spigotcore.entity.RPGEntity;
import com.forgestorm.spigotcore.profile.ProfileData;

abstract class PassiveEntity extends RPGEntity {

	PassiveEntity(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
	}
}
