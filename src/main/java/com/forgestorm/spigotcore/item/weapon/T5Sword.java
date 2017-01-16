package com.forgestorm.spigotcore.item.weapon;

import java.util.ArrayList;

import org.bukkit.Material;

import com.forgestorm.spigotcore.item.ItemQuality;
import com.forgestorm.spigotcore.item.Tier;

public class T5Sword extends WeaponItem {

	public T5Sword(ItemQuality quality) {
		super(quality);
		material = Material.GOLD_SWORD;
		tier = Tier.T5;
		title = "Legendary Sword";
		
		damageMin = 130;
		damageMax = 201;
		lifeStealPercent = 8;
		lifeStealMin = 1;
		lifeStealMax = 8;
		knockbackPercent = 20;
		knockbackMin = 1;
		knockbackMax = 20;
		criticalHitPercent = 7;
		criticalHitMin = 1;
		criticalHitMax = 10;
		blindPercent = 11;
		blindMin = 1;
		blindMax = 11;
		vsPlayerPercent = 15;
		vsPlayerMin = 1;
		vsPlayerMax = 15;
		vsMobPercent = 15;
		vsMobMin = 1;
		vsMobMax = 15;
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
