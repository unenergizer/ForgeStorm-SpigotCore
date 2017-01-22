package com.forgestorm.spigotcore.item.armor;

import java.util.ArrayList;

import org.bukkit.Material;

import com.forgestorm.spigotcore.item.ItemQuality;
import com.forgestorm.spigotcore.item.Tier;

public class T4Helmet extends ArmorItem {

	public T4Helmet(ItemQuality quality) {
		super(quality);
		material = Material.DIAMOND_HELMET;
		tier = Tier.T4;
		title = "Ancient Full Helmet";
		
		armor_percent = 10;
		armor_min = 9;
		armor_max = 10;
		hp_regen_min = 60;	
		hp_regen_max = 75;
		hp_increase_min = 600;
		hp_increase_max = 800;
		energy_regen_min = 7;
		energy_regen_max = 12;
		block_percent = 25;
		block_min = 1;
		block_max = 12;
		dodge_percent = 25;
		dodge_min = 1;
		dodge_max = 12;
		thorns_percent = 31;
		thorns_min = 2;
		thorns_max = 9;
		reflection_percent = 13;
		reflection_min = 1;
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
