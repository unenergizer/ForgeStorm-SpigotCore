package com.forgestorm.spigotcore.item.weapon;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.ChatColor;

import com.forgestorm.spigotcore.item.Item;
import com.forgestorm.spigotcore.item.ItemQuality;
import com.forgestorm.spigotcore.item.ItemText;

abstract class WeaponItem extends Item {

	protected int damageMin;
	protected int damageMax;
	protected int lifeStealPercent;
	protected int lifeStealMin;
	protected int lifeStealMax;
	protected int knockbackPercent;
	protected int knockbackMin;
	protected int knockbackMax;
	protected int criticalHitPercent;
	protected int criticalHitMin;
	protected int criticalHitMax;
	protected int blindPercent;
	protected int blindMin;
	protected int blindMax;
	protected int vsPlayerPercent;
	protected int vsPlayerMin;
	protected int vsPlayerMax;
	protected int vsMobPercent;
	protected int vsMobMin;
	protected int vsMobMax;
	protected int pureDamagePercent;
	protected int pureDamageMin;
	protected int pureDamageMax;
	protected int armorPenetrationPercent;
	protected int armorPenetrationMin;
	protected int armorPenetrationMax;
	protected int iceDamagePercent;
	protected int iceDamageMin;
	protected int iceDamageMax;
	protected int fireDamagePercent;
	protected int fireDamageMin;
	protected int fireDamageMax;
	protected int poisonDamagePercent;
	protected int poisonDamageMin;
	protected int poisonDamageMax;

	WeaponItem(ItemQuality quality) {
		super(quality);
	}

	ArrayList<String> generateWeaponLore() {
		ArrayList<String> lores = new ArrayList<>();

		/*---- GENERATE VALUES ----*/
		int lifesteal = loreChance(lifeStealPercent, lifeStealMin, lifeStealMax);
		int knockback = loreChance(knockbackPercent, knockbackMin, knockbackMax);
		int criticalHit = loreChance(criticalHitPercent, criticalHitMin, criticalHitMax);
		int blind = loreChance(blindPercent, blindMin, blindMax);
		int vsPlayer = loreChance(vsPlayerPercent, vsPlayerMin, vsPlayerMax);
		int vsMob = loreChance(vsMobPercent, vsMobMin, vsMobMax);
		int pureDamage = loreChance(pureDamagePercent, pureDamageMin, pureDamageMax);
		int armorPenetration = loreChance(armorPenetrationPercent, armorPenetrationMin, armorPenetrationMax);
		int iceDamage = loreChance(iceDamagePercent, iceDamageMin, iceDamageMax);
		int fireDamage = loreChance(fireDamagePercent, fireDamageMin, fireDamageMax);
		int poisonDamage = loreChance(poisonDamagePercent, poisonDamageMin, poisonDamageMax);


		/*---- APPLY LORE ----*/
		boolean elemantDamage = false;
		int num1 = damageGen(damageMin, damageMax);
		int num2 = damageGen(num1, damageMax + 1);
		
		System.out.println("[wep] num1: " + num1 + " num2: " + num2);
		
		if (num1 > num2) {
			num2 = num1;
		}
		
		//Every weapon gets damage.
		lores.add(ItemText.WEAPON_DAMAGE.toString().replace("%s", Integer.toString(num1)).replace("%f", Integer.toString(num2)));

		
		if (lifesteal > 0) lores.add(ItemText.WEAPON_LIFE_STEAL.toString().replace("%s", Integer.toString(lifesteal)));
		if (knockback > 0) lores.add(ItemText.WEAPON_KNOCKBACK.toString().replace("%s", Integer.toString(knockback)));
		if (criticalHit > 0) lores.add(ItemText.WEAPON_CRITICAL_HIT.toString().replace("%s", Integer.toString(criticalHit)));
		if (blind > 0) lores.add(ItemText.WEAPON_BLIND.toString().replace("%s", Integer.toString(blind)));
		if (vsPlayer > 0) lores.add(ItemText.WEAPON_VS_PLAYER.toString().replace("%s", Integer.toString(vsPlayer)));
		if (vsMob > 0) lores.add(ItemText.WEAPON_VS_MOB.toString().replace("%s", Integer.toString(vsMob)));
		if (pureDamage > 0) lores.add(ItemText.WEAPON_PURE_DAMAGE.toString().replace("%s", Integer.toString(pureDamage)));
		if (armorPenetration > 0) lores.add(ItemText.WEAPON_ARMOR_PENETRATION.toString().replace("%s", Integer.toString(armorPenetration)));
		
		if (iceDamage > 0 && !elemantDamage) {
			elemantDamage = true;
			lores.add(ItemText.WEAPON_ICE_DAMAGE.toString().replace("%s", Integer.toString(iceDamage)));
		}
		if (fireDamage > 0 && !elemantDamage) {
			elemantDamage = true;
			lores.add(ItemText.WEAPON_FIRE_DAMAGE.toString().replace("%s", Integer.toString(fireDamage)));
		}
		if (poisonDamage > 0 && !elemantDamage) {
			elemantDamage = true;
			lores.add(ItemText.WEAPON_POISON_DAMAGE.toString().replace("%s", Integer.toString(poisonDamage)));
		}

		String tiers = ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + tier.toString();
		String qualitys = quality.colorToString() + ChatColor.ITALIC + quality.nameToString();
		lores.add(tiers + ": " + qualitys);

		if (description != null) {
			lores.add(" ");//Blank line
			lores.add(ChatColor.GRAY + description);
		}

		return lores;
	}

	private int damageGen(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min) + 1;
	}
}
