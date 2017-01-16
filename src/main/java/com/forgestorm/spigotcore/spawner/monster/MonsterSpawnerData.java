package com.forgestorm.spigotcore.spawner.monster;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonsterSpawnerData {
	
	private int id;
	private Location location;
	private int spawnRadius;
	private int runRadius;
	private int mobCount;
	private int maxMobCount;
	private int respawnTime;
	private List<?> mobs = new ArrayList<>();
	
	public String getMob(int id) {
		return (String) mobs.get(id);
	}
}
