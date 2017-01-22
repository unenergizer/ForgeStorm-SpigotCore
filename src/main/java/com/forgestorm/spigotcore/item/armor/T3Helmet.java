package com.forgestorm.spigotcore.item.armor;

import java.util.ArrayList;

import org.bukkit.Material;

import com.forgestorm.spigotcore.item.ItemQuality;
import com.forgestorm.spigotcore.item.Tier;

public class T3Helmet extends ArmorItem {

	public T3Helmet(ItemQuality quality) {
		super(quality);
		material = Material.IRON_HELMET;
		tier = Tier.T3;
		title = "Full Helmet";
		
		armor_percent = 10;
		armor_min = 6;
		armor_max = 7;
		hp_regen_min = 35;	
		hp_regen_max = 55;
		hp_increase_min = 200;
		hp_increase_max = 350;
		energy_regen_min = 5;
		energy_regen_max = 9;
		block_percent = 15;
		block_min = 1;
		block_max = 10;
		dodge_percent = 15;
		dodge_min = 1;
		dodge_max = 10;
		thorns_percent = 10;
		thorns_min = 1;
		thorns_max = 5;
		reflection_percent = 10;
		reflection_min = 1;
		reflection_max = 4;
		gem_find_percent = 5;
		gem_find_min = 3;
		gem_find_max = 15;
		item_find_percent = 5;
		item_find_min = 1;
		item_find_max = 3;
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
