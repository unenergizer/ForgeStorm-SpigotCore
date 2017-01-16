package com.forgestorm.spigotcore.item.weapon;

import java.util.ArrayList;

import org.bukkit.Material;

import com.forgestorm.spigotcore.item.ItemQuality;
import com.forgestorm.spigotcore.item.Tier;

public class T3Sword extends WeaponItem {

	public T3Sword(ItemQuality quality) {
		super(quality);
		material = Material.IRON_SWORD;
		tier = Tier.T3;
		title = "Magic Sword";
		
		damageMin = 25;
		damageMax = 41;
		lifeStealPercent = 2;
		lifeStealMin = 1;
		lifeStealMax = 15;
		knockbackPercent = 3;
		knockbackMin = 1;
		knockbackMax = 6;
		criticalHitPercent = 2;
		criticalHitMin = 1;
		criticalHitMax = 4;
		blindPercent = 3;
		blindMin = 1;
		blindMax = 7;
		vsPlayerPercent = 10;
		vsPlayerMin = 1;
		vsPlayerMax = 12;
		vsMobPercent = 10;
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
