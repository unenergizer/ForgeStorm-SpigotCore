package com.forgestorm.spigotcore.item;

import org.bukkit.ChatColor;

public enum ItemText {

	ARMOR_ARMOR(ChatColor.RED + "ARMOR: %s-%f"),	
	ARMOR_BLOCK(ChatColor.RED + "BLOCK: +%s%"),	
	ARMOR_DEXTERITY(ChatColor.RED + "DEX: +%s"),	
	ARMOR_DODGE(ChatColor.RED + "DODGE: +%s%"),	
	ARMOR_ENERGY_REGEN(ChatColor.RED + "ENERGY REGEN: +%s%"),	
	ARMOR_FIRE_RESIST(ChatColor.RED + "FIRE RESISTANCE: +%s%"),	
	ARMOR_GEM_FIND(ChatColor.RED + "GEM FIND: +%s%"),	
	ARMOR_HP_INCREASE(ChatColor.RED + "HP: +%s"),	
	ARMOR_HP_REGEN(ChatColor.RED + "HP REGEN: +%s HP/s"),	
	ARMOR_ICE_RESIST(ChatColor.RED + "ICE RESISTANCE: +%s%"),	
	ARMOR_INTELLECT(ChatColor.RED + "INT: +%s"),	
	ARMOR_ITEM_FIND(ChatColor.RED + "ITEM FIND: +%s%"),	
	ARMOR_POISON_RESIST(ChatColor.RED + "POISON RESISTANCE: +%s%"),	
	ARMOR_REFLECTION(ChatColor.RED + "REFLECTION: +%s%"),	
	ARMOR_STRENGTH(ChatColor.RED + "STR: +%s"),	
	ARMOR_THRONS(ChatColor.RED + "THORNS: +%s%"),	
	ARMOR_VITALITY(ChatColor.RED + "VIT: +%s"),	
	
	WEAPON_ARMOR_PENETRATION(ChatColor.RED + "ARMOR PENETRATION: +%s"),	
	WEAPON_BLIND(ChatColor.RED + "BLIND: +%s"),	
	WEAPON_CRITICAL_HIT(ChatColor.RED + "CRITICAL HIT: +%s"),	
	WEAPON_DAMAGE(ChatColor.RED + "DMG: %s-%f"),	
	WEAPON_FIRE_DAMAGE(ChatColor.RED + "FIRE DMG: +%s"),	
	WEAPON_ICE_DAMAGE(ChatColor.RED + "ICE DMG: +%s"),	
	WEAPON_KNOCKBACK(ChatColor.RED + "KNOCKBACK: +%s"),	
	WEAPON_LIFE_STEAL(ChatColor.RED + "LIFE STEAL: +%s"),	
	WEAPON_POISON_DAMAGE(ChatColor.RED + "POISON DMG: +%s"),	
	WEAPON_PURE_DAMAGE(ChatColor.RED + "PURE DMG: +%s"),	
	WEAPON_VS_MOB(ChatColor.RED + "vs. MOB: +%s DMG"),	
	WEAPON_VS_PLAYER(ChatColor.RED + "vs. PLAYER: +%s DMG"),
	
	ITEM_UNTRADEABLE(ChatColor.GRAY + "" + ChatColor.ITALIC + "Untradeable");
	
	private String message;

	//Constructor
	ItemText(String message) {
		this.message = message;
	}

	/**
	 * Sends a string representation of the enumerator item.
	 */
	public String toString() {
		return message;
	}
}
