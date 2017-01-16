package com.forgestorm.spigotcore.item.weapon;

import java.util.ArrayList;

import org.bukkit.Material;

import com.forgestorm.spigotcore.item.ItemQuality;
import com.forgestorm.spigotcore.item.Tier;

public class T4Sword extends WeaponItem {

	public T4Sword(ItemQuality quality) {
		super(quality);
		material = Material.DIAMOND_SWORD;
		tier = Tier.T4;
		title = "Ancient Sword";
		
		damageMin = 65;
		damageMax = 111;
		lifeStealPercent = 10;
		lifeStealMin = 1;
		lifeStealMax = 7;
		knockbackPercent = 16;
		knockbackMin = 1;
		knockbackMax = 15;
		criticalHitPercent = 9;
		criticalHitMin = 1;
		criticalHitMax = 6;
		blindPercent = 9;
		blindMin = 1;
		blindMax = 9;
		vsPlayerPercent = 12;
		vsPlayerMin = 1;
		vsPlayerMax = 12;
		vsMobPercent = 12;
		vsMobMin = 1;
		vsMobMax = 12;
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
