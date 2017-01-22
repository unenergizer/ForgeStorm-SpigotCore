package com.forgestorm.spigotcore.entity.boss;

import com.forgestorm.spigotcore.entity.RPGEntity;
import com.forgestorm.spigotcore.profile.monster.MonsterProfileData;
import org.bukkit.Location;


abstract class BossEntity extends RPGEntity {

	BossEntity(String name, int level, Location location, MonsterProfileData profile) {
		super(name, level, location, profile);
	}

}
