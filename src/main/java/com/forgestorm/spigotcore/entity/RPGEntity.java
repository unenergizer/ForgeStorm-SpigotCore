package com.forgestorm.spigotcore.entity;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import com.forgestorm.spigotcore.constants.Messages;
import com.forgestorm.spigotcore.profile.ProfileData;
import com.forgestorm.spigotcore.util.text.ProgressBarString;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class RPGEntity {
	
	protected LivingEntity entity;
	protected EntityType entityType;
	private String name;
	private String modifiedName;
	private int level;
	private Location location;
	private ProfileData profile;
	
	public RPGEntity(String name, int level, Location location, ProfileData profile) {
		this.name = name;
		this.level = level;
		this.location = location;
		this.profile = profile;
	}
	
	protected abstract void createEntity();
	protected abstract void killReward();
	
	public void spawn() {
		createEntity();
		String nameLevel = Messages.ENTITY_LEVEL.toString().replace("%s", Integer.toString(level));
		entity.setCustomName(nameLevel + name);
		entity.setCustomNameVisible(true);
	}
	
	protected void spawnEntity() {
		entity = (LivingEntity) Bukkit.getWorld(location.getWorld().getName()).spawnEntity(location, entityType);
		String nameLevel = Messages.ENTITY_LEVEL.toString().replace("%s", Integer.toString(level));
		entity.setCustomName(nameLevel + name);
		entity.setCustomNameVisible(true);
	}
	
	public boolean toggleDamage() {
		//Adjust health
		if (entity.getHealth() >= 1) {
			renameEntity(); 
			return false; //Entity is alive
		} else {
			toggleDeath();
			return true; //Entity died.
		}
	}
	
	public void toggleDeath() {
		entity.resetMaxHealth();
		entity.setHealth(0);
	}
	
	private void renameEntity() {
		int percentHP = (int) ((100 * entity.getHealth()) / entity.getMaxHealth());
		String nameLevel = Messages.ENTITY_LEVEL.toString().replace("%s", Integer.toString(level));
		String nameBar = ProgressBarString.buildBar(percentHP);
		modifiedName = nameLevel + nameBar;
		
		System.out.println("-----------------");
		System.out.println("HP: " + entity.getHealth() + "   MaxHP: " + entity.getMaxHealth() + "   %HP: " + percentHP);
		
		entity.setCustomName(modifiedName);
		entity.setCustomNameVisible(true);
	}
}
