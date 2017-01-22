package com.forgestorm.spigotcore.entity.passive;

import com.forgestorm.spigotcore.entity.RPGEntity;
import com.forgestorm.spigotcore.profile.monster.MonsterProfileData;
import org.bukkit.Location;

abstract class PassiveEntity extends RPGEntity {

	PassiveEntity(String name, int level, Location location, MonsterProfileData profile) {
		super(name, level, location, profile);
	}
}
