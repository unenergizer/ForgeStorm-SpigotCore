package com.forgestorm.spigotcore.item.weapon;

import java.util.ArrayList;

import org.bukkit.Material;

import com.forgestorm.spigotcore.item.ItemQuality;
import com.forgestorm.spigotcore.item.Tier;

public class T1Sword extends WeaponItem {

	public T1Sword(ItemQuality quality) {
		super(quality);
		material = Material.WOOD_SWORD;
		tier = Tier.T1;
		title = "Shortsword";
		
		damageMin = 1;
		damageMax = 4;
		lifeStealPercent = 2;
		lifeStealMin = 1;
		lifeStealMax = 30;
		knockbackPercent = 3;
		knockbackMin = 1;
		knockbackMax = 3;
		criticalHitPercent = 2;
		criticalHitMin = 1;
		criticalHitMax = 2;
		blindPercent = 3;
		blindMin = 1;
		blindMax = 4;
		vsPlayerPercent = 1;
		vsPlayerMin = 1;
		vsPlayerMax = 1;
		vsMobPercent = 1;
		vsMobMin = 1;
		vsMobMax = 1;
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
