package com.forgestorm.spigotcore.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntityManager {
	
	private Map<UUID, RPGEntity> rpgEntity = new HashMap<>();
	
	public void addRPGEntity(RPGEntity entity) {
		rpgEntity.put(entity.getEntity().getUniqueId(), entity);
	}
	
	public void removeRPGEntity(UUID uuid) {
		rpgEntity.remove(uuid);
	}
	
	public RPGEntity getRPGEntity(UUID uuid) {
		return rpgEntity.get(uuid);
	}
}
