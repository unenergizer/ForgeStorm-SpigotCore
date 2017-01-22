package com.forgestorm.spigotcore.entity.hostile;

import com.forgestorm.spigotcore.profile.monster.MonsterProfileData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class EntityShulker extends HostileEntity {

	public EntityShulker(String name, int level, Location location, MonsterProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.SHULKER;
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
