package com.forgestorm.spigotcore.entity.mount;

import com.forgestorm.spigotcore.profile.monster.MonsterProfileData;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;

public class EntityMountHorse extends MountEntity {

	public EntityMountHorse(String name, int level, Location location, MonsterProfileData profile) {
		super(name, level, location, profile);
		entityType = EntityType.HORSE;
	}

	@Override
	protected void createEntity() {
		spawnEntity();
		Horse horse = (Horse) entity;
		horse.setStyle(Horse.Style.WHITE_DOTS);
		horse.setColor(Horse.Color.CHESTNUT);
		horse.getInventory().setSaddle(makeSaddle());
		horse.setTamed(true);
		horse.setAdult();
	}

	@Override
	protected void killReward() {
		// TODO Auto-generated method stub
		
	}
}
