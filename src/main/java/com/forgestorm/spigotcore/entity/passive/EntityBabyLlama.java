package com.forgestorm.spigotcore.entity.passive;

import com.forgestorm.spigotcore.entity.RPGEntity;
import com.forgestorm.spigotcore.profile.monster.MonsterProfileData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Llama;

public class EntityBabyLlama extends RPGEntity {

	public EntityBabyLlama(String name, int level, Location location, MonsterProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.LLAMA;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		Llama llama = (Llama) entity;
		llama.setBaby();
		llama.setAgeLock(true);
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
