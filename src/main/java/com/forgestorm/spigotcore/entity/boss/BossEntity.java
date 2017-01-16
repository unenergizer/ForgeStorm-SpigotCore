package com.forgestorm.spigotcore.entity.boss;

import org.bukkit.Location;

import com.forgestorm.spigotcore.entity.RPGEntity;
import com.forgestorm.spigotcore.profile.ProfileData;


abstract class BossEntity extends RPGEntity {

	BossEntity(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
	}

}
