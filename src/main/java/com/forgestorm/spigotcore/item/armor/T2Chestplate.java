package com.forgestorm.spigotcore.item.armor;

import java.util.ArrayList;

import org.bukkit.Material;

import com.forgestorm.spigotcore.item.ItemQuality;
import com.forgestorm.spigotcore.item.Tier;

public class T2Chestplate extends ArmorItem {

	public T2Chestplate(ItemQuality quality) {
		super(quality);
		material = Material.CHAINMAIL_CHESTPLATE;
		tier = Tier.T2;
		title = "Chainmail";
		
		armor_percent = 10;
		armor_min = 2;
		armor_max = 3;
		hp_regen_min = 1;	
		hp_regen_max = 25;
		hp_increase_min = 60;
		hp_increase_max = 80;
		energy_regen_min = 3;
		energy_regen_max = 7;
		block_percent = 9;
		block_min = 1;
		block_max = 8;
		dodge_percent = 9;
		dodge_min = 1;
		dodge_max = 8;
		thorns_percent = 5;
		thorns_min = 1;
		thorns_max = 3;
		reflection_percent = 5;
		reflection_min = 1;
		reflection_max = 2;
		gem_find_percent = 5;
		gem_find_min = 1;
		gem_find_max = 8;
		item_find_percent = 5;
		item_find_min = 1;
		item_find_max = 2;
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
	protected ArrayList<String> generateLore() { return generateArmorLore(); }
}
