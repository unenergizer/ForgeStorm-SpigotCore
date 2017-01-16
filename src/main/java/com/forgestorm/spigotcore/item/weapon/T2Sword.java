package com.forgestorm.spigotcore.item.weapon;

import java.util.ArrayList;

import org.bukkit.Material;

import com.forgestorm.spigotcore.item.ItemQuality;
import com.forgestorm.spigotcore.item.Tier;

public class T2Sword extends WeaponItem {

	public T2Sword(ItemQuality quality) {
		super(quality);
		material = Material.STONE_SWORD;
		tier = Tier.T2;
		title = "Broadsword";
		
		damageMin = 10;
		damageMax = 16;
		lifeStealPercent = 4;
		lifeStealMin = 1;
		lifeStealMax = 15;
		knockbackPercent = 10;
		knockbackMin = 1;
		knockbackMax = 3;
		criticalHitPercent = 5;
		criticalHitMin = 1;
		criticalHitMax = 4;
		blindPercent = 5;
		blindMin = 1;
		blindMax = 7;
		vsPlayerPercent = 9;
		vsPlayerMin = 1;
		vsPlayerMax = 10;
		vsMobPercent = 9;
		vsMobMin = 1;
		vsMobMax = 10;
		pureDamagePercent = 6;
		pureDamageMin = 1;
		pureDamageMax = 5;
		armorPenetrationPercent = 21;
		armorPenetrationMin = 1;
		armorPenetrationMax = 1;
		iceDamagePercent = 1;
		iceDamageMin = 1;
		iceDamageMax = 1;
		fireDamagePercent = 1;
		fireDamageMin = 1;
		fireDamageMax = 1;
		poisonDamagePercent = 1;
		poisonDamageMin = 1;
		poisonDamageMax = 1;
		
		lores = generateLore();
		item = buildItem();
	}

	@Override
	protected ArrayList<String> generateLore() {
		return generateWeaponLore();
	}

}
