package com.forgestorm.spigotcore.item.armor;

import java.util.ArrayList;

import org.bukkit.Material;

import com.forgestorm.spigotcore.item.ItemQuality;
import com.forgestorm.spigotcore.item.Tier;

public class T5Leggings extends ArmorItem {

	public T5Leggings(ItemQuality quality) {
		super(quality);
		material = Material.GOLD_LEGGINGS;
		tier = Tier.T5;
		title = "Legendary Platemail Leggings";
		
		armor_percent = 10;
		armor_min = 12;
		armor_max = 13;
		hp_regen_min = 80;	
		hp_regen_max = 120;
		hp_increase_min = 1500;
		hp_increase_max = 2500;
		energy_regen_min = 7;
		energy_regen_max = 12;
		block_percent = 30;
		block_min = 1;
		block_max = 12;
		dodge_percent = 30;
		dodge_min = 1;
		dodge_max = 12;
		thorns_percent = 20;
		thorns_min = 2;
		thorns_max = 9;
		reflection_percent = 15;
		reflection_min = 2;
		reflection_max = 5;
		gem_find_percent = 5;
		gem_find_min = 5;
		gem_find_max = 20;
		item_find_percent = 5;
		item_find_min = 1;
		item_find_max = 4;
		ice_resistance_percent = 3;
		ice_resistance_min = 1;
		ice_resistance_max = 2;
		fire_resistance_percent = 3;
		fire_resistance_min = 1;
		fire_resistance_max = 2;
		poison_resistance_percent = 3;
		poison_resistance_min = 1;
		poison_resistance_max = 2;
		dexterity_percent = 3;
		dexterity_min = 1;
		dexterity_max = 2;
		intellect_percent = 3;
		intellect_min = 1;
		intellect_max = 2;
		strength_percent = 3;
		strength_min = 1;
		strength_max = 2;
		vitality_percent = 3;
		vitality_min = 1;
		vitality_max = 2;
		
		lores = generateLore();
		item = buildItem();
	}

	@Override
	protected ArrayList<String> generateLore() {
		
		return generateArmorLore();
	}
}
