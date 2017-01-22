package com.forgestorm.spigotcore.item.armor;

import com.forgestorm.spigotcore.item.Item;
import com.forgestorm.spigotcore.item.ItemQuality;
import com.forgestorm.spigotcore.item.ItemText;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Random;

abstract class ArmorItem extends Item {

	protected int armor_percent;
	protected int armor_min;
	protected int armor_max;
	protected int hp_regen_min;	
	protected int hp_regen_max;
	protected int hp_increase_min;
	protected int hp_increase_max;
	protected int energy_regen_min;
	protected int energy_regen_max;
	protected int block_percent;
	protected int block_min;
	protected int block_max;
	protected int dodge_percent;
	protected int dodge_min;
	protected int dodge_max;
	protected int thorns_percent;
	protected int thorns_min;
	protected int thorns_max;
	protected int reflection_percent;
	protected int reflection_min;
	protected int reflection_max;
	protected int gem_find_percent;
	protected int gem_find_min;
	protected int gem_find_max;
	protected int item_find_percent;
	protected int item_find_min;
	protected int item_find_max;
	protected int ice_resistance_percent;
	protected int ice_resistance_min;
	protected int ice_resistance_max;
	protected int fire_resistance_percent;
	protected int fire_resistance_min;
	protected int fire_resistance_max;
	protected int poison_resistance_percent;
	protected int poison_resistance_min;
	protected int poison_resistance_max;
	protected int dexterity_percent;
	protected int dexterity_min;
	protected int dexterity_max;
	protected int intellect_percent;
	protected int intellect_min;
	protected int intellect_max;
	protected int strength_percent;
	protected int strength_min;
	protected int strength_max;
	protected int vitality_percent;
	protected int vitality_min;
	protected int vitality_max;


	ArmorItem(ItemQuality quality) {
		super(quality);
	}

	ArrayList<String> generateArmorLore() {
		ArrayList<String> lores = new ArrayList<>();
		
		/*---- GENERATE VALUES ----*/
		int armor = loreChance(armor_percent, armor_min, armor_max);
		int hp_regen = loreChance(100, hp_regen_min, hp_regen_max);
		int hp_increase = loreChance(100, hp_increase_min, hp_increase_max);
		int energy_regen = loreChance(100, energy_regen_min, energy_regen_max);
		int block = loreChance(block_percent, block_min, block_max);
		int dodge = loreChance(dodge_percent, dodge_min, dodge_max);
		int thorns = loreChance(thorns_percent, thorns_min, thorns_max);
		int reflection = loreChance(reflection_percent, reflection_min, reflection_max);
		int gem_find = loreChance(gem_find_percent, gem_find_min, gem_find_max);
		int item_find = loreChance(item_find_percent, item_find_min, item_find_max);
		int ice_resistance = loreChance(ice_resistance_percent, ice_resistance_min, ice_resistance_max);
		int fire_resistance = loreChance(fire_resistance_percent, fire_resistance_min, fire_resistance_max);
		int poison_resistance = loreChance(poison_resistance_percent, poison_resistance_min, poison_resistance_max);
		int dexterity = loreChance(dexterity_percent, dexterity_min, dexterity_max);
		int intellect = loreChance(intellect_percent, intellect_min, intellect_max);
		int strength = loreChance(strength_percent, strength_min, strength_max);
		int vitality = loreChance(vitality_percent, vitality_min, vitality_max);

		/*---- APPLY LORE ----*/
		
		if (armor > 0) {
			int num1 = armorGen(armor_min, armor_max);
			System.out.println("num1: " + num1 + " min: " + armor_min + " max: " + armor_max);
			int num2 = armorGen(num1, armor_max + 1);
			lores.add(ItemText.ARMOR_ARMOR.toString().replace("%s", Integer.toString(num1)).replace("%f", Integer.toString(num2)));
		}
		
		//HP REGEN or ENERGY REGEN
		Random random = new Random();
		int num = random.nextInt(100 - 1 + 1) + 1;
		if (num > 50) {
			lores.add(ItemText.ARMOR_HP_REGEN.toString().replace("%s", Integer.toString(hp_regen)));
		} else {
			lores.add(ItemText.ARMOR_ENERGY_REGEN.toString().replace("%s", Integer.toString(energy_regen)));
		}

		//Every armor item gets health increase.
		lores.add(ItemText.ARMOR_HP_INCREASE.toString().replace("%s", Integer.toString(hp_increase)));
		
		if (block > 0) lores.add(ItemText.ARMOR_BLOCK.toString().replace("%s", Integer.toString(block)));
		if (dodge > 0) lores.add(ItemText.ARMOR_DODGE.toString().replace("%s", Integer.toString(dodge)));
		if (thorns > 0) lores.add(ItemText.ARMOR_THORNS.toString().replace("%s", Integer.toString(thorns)));
		if (reflection > 0) lores.add(ItemText.ARMOR_REFLECTION.toString().replace("%s", Integer.toString(reflection)));
		if (gem_find > 0) lores.add(ItemText.ARMOR_GEM_FIND.toString().replace("%s", Integer.toString(gem_find)));
		if (item_find > 0) lores.add(ItemText.ARMOR_ITEM_FIND.toString().replace("%s", Integer.toString(item_find)));
		if (ice_resistance > 0) lores.add(ItemText.ARMOR_ICE_RESIST.toString().replace("%s", Integer.toString(ice_resistance)));
		if (fire_resistance > 0) lores.add(ItemText.ARMOR_FIRE_RESIST.toString().replace("%s", Integer.toString(fire_resistance)));
		if (poison_resistance > 0) lores.add(ItemText.ARMOR_POISON_RESIST.toString().replace("%s", Integer.toString(poison_resistance)));
		if (dexterity > 0) lores.add(ItemText.ARMOR_DEXTERITY.toString().replace("%s", Integer.toString(dexterity)));
		if (intellect > 0) lores.add(ItemText.ARMOR_INTELLECT.toString().replace("%s", Integer.toString(intellect)));
		if (strength > 0) lores.add(ItemText.ARMOR_STRENGTH.toString().replace("%s", Integer.toString(strength)));
		if (vitality > 0) lores.add(ItemText.ARMOR_VITALITY.toString().replace("%s", Integer.toString(vitality)));

		String tiers = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + tier.toString();
		String quality = this.quality.colorToString() + ChatColor.ITALIC + this.quality.nameToString();
		lores.add(tiers + ": " + quality);

		if (description != null) {
			lores.add(" ");//Blank line
			lores.add(ChatColor.GRAY + description);
		}

		return lores;
	}

	private int armorGen(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min) + 1;
	}
}
