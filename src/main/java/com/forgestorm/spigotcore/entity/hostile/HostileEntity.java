package com.forgestorm.spigotcore.entity.hostile;

import org.bukkit.Location;

import com.forgestorm.spigotcore.entity.RPGEntity;
import com.forgestorm.spigotcore.profile.ProfileData;

abstract class HostileEntity extends RPGEntity {

	HostileEntity(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
	}
}
