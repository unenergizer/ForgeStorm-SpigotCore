package com.forgestorm.spigotcore.entity.mount;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.entity.RPGEntity;
import com.forgestorm.spigotcore.profile.ProfileData;
import com.forgestorm.spigotcore.util.item.ItemBuilder;


public abstract class MountEntity extends RPGEntity {

	public MountEntity(String name, int level, Location location, ProfileData profile) {
		super(name, level, location, profile);
	}
	
	protected ItemStack makeSaddle() {
		return new ItemBuilder(Material.SADDLE).build(true);
	}
	
	public void setOwner(Player player) {
		if (entity instanceof Horse) {
			Horse horse = (Horse) entity;
			horse.setOwner(player);
		}
	}
}
