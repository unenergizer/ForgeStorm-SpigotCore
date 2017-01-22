package com.forgestorm.spigotcore.entity.mount;

import com.forgestorm.spigotcore.profile.monster.MonsterProfileData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;

public class EntityMountDonkey extends MountEntity {

	public EntityMountDonkey(String name, int level, Location location, MonsterProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.DONKEY;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		Horse donkey = (Horse) entity;
		donkey.getInventory().setSaddle(makeSaddle());
		donkey.setTamed(true);
		donkey.setAdult();
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
