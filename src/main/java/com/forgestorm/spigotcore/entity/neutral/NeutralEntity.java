package com.forgestorm.spigotcore.entity.neutral;

import com.forgestorm.spigotcore.entity.RPGEntity;
import com.forgestorm.spigotcore.profile.monster.MonsterProfileData;
import org.bukkit.Location;

public abstract class NeutralEntity extends RPGEntity {

	public NeutralEntity(String name, int level, Location location, MonsterProfileData profile) {
		super(name, level, location, profile);
	}
}
