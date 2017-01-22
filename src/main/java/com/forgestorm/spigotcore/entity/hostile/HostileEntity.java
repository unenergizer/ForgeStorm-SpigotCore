package com.forgestorm.spigotcore.entity.hostile;

import com.forgestorm.spigotcore.entity.RPGEntity;
import com.forgestorm.spigotcore.profile.monster.MonsterProfileData;
import org.bukkit.Location;

abstract class HostileEntity extends RPGEntity {

	HostileEntity(String name, int level, Location location, MonsterProfileData profile) {
		super(name, level, location, profile);
	}
}
