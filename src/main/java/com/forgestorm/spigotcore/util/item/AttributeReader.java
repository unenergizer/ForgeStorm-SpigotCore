package com.forgestorm.spigotcore.util.item;

import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.attributes.armor.ArmorMax;
import com.forgestorm.spigotcore.attributes.armor.ArmorMin;
import com.forgestorm.spigotcore.attributes.armor.Block;
import com.forgestorm.spigotcore.attributes.armor.Dexterity;
import com.forgestorm.spigotcore.attributes.armor.Dodge;
import com.forgestorm.spigotcore.attributes.armor.EnergyRegeneration;
import com.forgestorm.spigotcore.attributes.armor.FireResistance;
import com.forgestorm.spigotcore.attributes.armor.GemFind;
import com.forgestorm.spigotcore.attributes.armor.HealthIncrease;
import com.forgestorm.spigotcore.attributes.armor.HealthRegeneration;
import com.forgestorm.spigotcore.attributes.armor.IceResistance;
import com.forgestorm.spigotcore.attributes.armor.Intellect;
import com.forgestorm.spigotcore.attributes.armor.ItemFind;
import com.forgestorm.spigotcore.attributes.armor.PoisonResistance;
import com.forgestorm.spigotcore.attributes.armor.Reflection;
import com.forgestorm.spigotcore.attributes.armor.Strength;
import com.forgestorm.spigotcore.attributes.armor.Thorns;
import com.forgestorm.spigotcore.attributes.armor.Vitality;
import com.forgestorm.spigotcore.attributes.weapon.ArmorPenetration;
import com.forgestorm.spigotcore.attributes.weapon.Blind;
import com.forgestorm.spigotcore.attributes.weapon.CriticalHitChance;
import com.forgestorm.spigotcore.attributes.weapon.DamageMax;
import com.forgestorm.spigotcore.attributes.weapon.DamageMin;
import com.forgestorm.spigotcore.attributes.weapon.FireDamage;
import com.forgestorm.spigotcore.attributes.weapon.IceDamage;
import com.forgestorm.spigotcore.attributes.weapon.Knockback;
import com.forgestorm.spigotcore.attributes.weapon.LifeSteal;
import com.forgestorm.spigotcore.attributes.weapon.PoisonDamage;
import com.forgestorm.spigotcore.attributes.weapon.PureDamage;
import com.forgestorm.spigotcore.attributes.weapon.VersusMonster;
import com.forgestorm.spigotcore.attributes.weapon.VersusPlayer;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;

import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;

@AllArgsConstructor
public class AttributeReader {

	private final SpigotCore PLUGIN;
	private final Player PLAYER;

	public void readArmorAttributes(boolean showUpdates) {

		PlayerProfileData profile = PLUGIN.getPlayerManager().getPlayerProfile(PLAYER);

		/*--- OLD ARMOR DATA ---*/
		int oldArmorMax = profile.getArmorMax();
		int oldArmorMin = profile.getArmorMin();
		int oldBlock = profile.getArmorBlock();
		int oldDexterity = profile.getDexterity();
		int oldDodge = profile.getArmorDodge();
		int oldEnergyRegen = profile.getArmorEnergyRegen();
		int oldFireResist = profile.getArmorFireResistance();
		int oldGemFind = profile.getArmorGemFind();
		int oldHealthMax = profile.getMaxHealth();
		int oldHealthRegen = profile.getArmorHealthRegen();
		int oldIceResist = profile.getArmorIceResistance();
		int oldIntellect = profile.getIntelect();
		int oldItemFind = profile.getArmorItemFind();
		int oldPoisonResist = profile.getArmorPoisonResistance();
		int oldReflection = profile.getArmorReflection();
		int oldStrength = profile.getStrength();
		int oldThorns = profile.getArmorThorns();
		int oldVitality = profile.getVitality();

		/*---- ARMOR ----*/
		int armorMax = new ArmorMax().getAttributeValue(PLAYER);
		int armorMin = new ArmorMin().getAttributeValue(PLAYER);
		int block = new Block().getAttributeValue(PLAYER);
		int dexterity = new Dexterity().getAttributeValue(PLAYER);
		int dodge = new Dodge().getAttributeValue(PLAYER);
		int baseEnergyRegen = profile.getBaseEnergyRegen();
		int energyRegen = new EnergyRegeneration().getAttributeValue(PLAYER) + baseEnergyRegen;
		int fireResist = new FireResistance().getAttributeValue(PLAYER);
		int gemFind = new GemFind().getAttributeValue(PLAYER);
		int healthMax = new HealthIncrease().getAttributeValue(PLAYER) + profile.getBaseMaxHealth();
		int healthRegen = new HealthRegeneration().getAttributeValue(PLAYER) + profile.getBaseHealthRegen();
		int iceResist = new IceResistance().getAttributeValue(PLAYER);
		int intellect = new Intellect().getAttributeValue(PLAYER);
		int itemFind = new ItemFind().getAttributeValue(PLAYER);
		int poisonResist = new PoisonResistance().getAttributeValue(PLAYER);
		int reflection = new Reflection().getAttributeValue(PLAYER);
		int strength = new Strength().getAttributeValue(PLAYER);
		int thorns = new Thorns().getAttributeValue(PLAYER);
		int vitality = new Vitality().getAttributeValue(PLAYER);

		profile.setArmorMax(armorMax);
		profile.setArmorMin(armorMin);
		profile.setArmorBlock(block);
		profile.setDexterity(dexterity);
		profile.setArmorDodge(dodge);
		profile.setArmorEnergyRegen(energyRegen);
		profile.setArmorFireResistance(fireResist);
		profile.setArmorGemFind(gemFind);
		profile.setMaxHealth(healthMax);
		profile.setArmorHealthRegen(healthRegen);
		profile.setArmorIceResistance(iceResist);
		profile.setIntelect(intellect);
		profile.setArmorItemFind(itemFind);
		profile.setArmorPoisonResistance(poisonResist);
		profile.setArmorReflection(reflection);
		profile.setStrength(strength);
		profile.setArmorThorns(thorns);
		profile.setVitality(vitality);

		//Update the players HP under their name tag.
		PLUGIN.getScoreboardManager().updatePlayerHP(profile, PLAYER);

		//Show debug.
		if (profile.isToggleDebug() && showUpdates) {
			if (armorMax != oldArmorMax) {
				int difference = armorMax - oldArmorMax;
				if (armorMax > oldArmorMax) {
					PLAYER.sendMessage(ChatColor.GREEN + "+" + difference + " Max Armor");
				} else {
					PLAYER.sendMessage(ChatColor.RED + "-" + (difference * -1) + " Max Armor");
				}
			}
			if (armorMin != oldArmorMin) { 
				int difference = armorMin - oldArmorMin;
				if (armorMin > oldArmorMin) {
					PLAYER.sendMessage(ChatColor.GREEN + "+" + difference + " Min Armor");
				} else {
					PLAYER.sendMessage(ChatColor.RED + "-" + (difference * -1) + " Min Armor");
				}
			}
			if (block != oldBlock) {
				int difference = block - oldBlock;
				if (block > oldBlock) {
					PLAYER.sendMessage(ChatColor.GREEN + "+" + difference + " Block");
				} else {
					PLAYER.sendMessage(ChatColor.RED + "-" + (difference * -1) + " Block");
				}
			}
			if (dexterity != oldDexterity) {
				int difference = dexterity - oldDexterity;
				if (dexterity > oldDexterity) {
					PLAYER.sendMessage(ChatColor.GREEN + "+" + difference + " Dexterity");
				} else {
					PLAYER.sendMessage(ChatColor.RED + "-" + (difference * -1) + " Dexterity");
				}
			}
			if (dodge != oldDodge) {
				int difference = dodge - oldDodge;
				if (dodge > oldDodge) {
					PLAYER.sendMessage(ChatColor.GREEN + "+" + difference + " Dodge");
				} else {
					PLAYER.sendMessage(ChatColor.RED + "-" + (difference * -1) + " Dodge");
				}
			}
			if (energyRegen != oldEnergyRegen) {
				int difference = energyRegen - oldEnergyRegen;
				if (energyRegen > oldEnergyRegen) {
					PLAYER.sendMessage(ChatColor.GREEN + "+" + difference + " Energy Regeneration");
				} else {
					PLAYER.sendMessage(ChatColor.RED + "-" + (difference * -1) + " Energy Regeneration");
				}
			}
			if (fireResist != oldFireResist) {
				int difference = fireResist - oldFireResist;
				if (fireResist > oldFireResist) {
					PLAYER.sendMessage(ChatColor.GREEN + "+" + difference + " Fire Resistance");
				} else {
					PLAYER.sendMessage(ChatColor.RED + "-" + (difference * -1) + " Fire Resistance");
				}
			}
			if (gemFind != oldGemFind) {
				int difference = gemFind - oldGemFind;
				if (gemFind > oldGemFind) {
					PLAYER.sendMessage(ChatColor.GREEN + "+" + difference + " Gem Find");
				} else {
					PLAYER.sendMessage(ChatColor.RED + "-" + (difference * -1) + " Gem Find");
				}
			}
			if (healthMax != oldHealthMax) {
				int difference = healthMax - oldHealthMax;
				if (healthMax > oldHealthMax) {
					PLAYER.sendMessage(ChatColor.GREEN + "+" + difference + " Health");
				} else {
					PLAYER.sendMessage(ChatColor.RED + "-" + (difference * -1) + " Health");
				}
			}
			if (healthRegen != oldHealthRegen) {
				int difference = healthRegen - oldHealthRegen;
				if (healthRegen > oldHealthRegen) {
					PLAYER.sendMessage(ChatColor.GREEN + "+" + difference + " Health Regeneration");
				} else {
					PLAYER.sendMessage(ChatColor.RED + "-" + (difference * -1) + " Health Regeneration");
				}
			}
			if (iceResist != oldIceResist) {
				int difference = iceResist - oldIceResist;
				if (iceResist > oldIceResist) {
					PLAYER.sendMessage(ChatColor.GREEN + "+" + difference + " Ice Resistance");
				} else {
					PLAYER.sendMessage(ChatColor.RED + "-" + (difference * -1) + " Ice Resistance");
				}
			}
			if (intellect != oldIntellect) {
				int difference = intellect - oldIntellect;
				if (intellect > oldIntellect) {
					PLAYER.sendMessage(ChatColor.GREEN + "+" + difference + " Intellect");
				} else {
					PLAYER.sendMessage(ChatColor.RED + "-" + (difference * -1) + " Intellect");
				}
			}
			if (itemFind != oldItemFind) {
				int difference = itemFind - oldItemFind;
				if (itemFind > oldItemFind) {
					PLAYER.sendMessage(ChatColor.GREEN + "+" + difference + " Item Find");
				} else {
					PLAYER.sendMessage(ChatColor.RED + "-" + (difference * -1) + " Item Find");
				}
			}
			if (poisonResist != oldPoisonResist) {
				int difference = poisonResist - oldPoisonResist;
				if (poisonResist > oldPoisonResist) {
					PLAYER.sendMessage(ChatColor.GREEN + "+" + difference + " Poison Resistance");
				} else {
					PLAYER.sendMessage(ChatColor.RED + "-" + (difference * -1) + " Poison Resistance");
				}
			}
			if (reflection != oldReflection) {
				int difference = reflection - oldReflection;
				if (reflection > oldReflection) {
					PLAYER.sendMessage(ChatColor.GREEN + "+" + difference + " Reflection");
				} else {
					PLAYER.sendMessage(ChatColor.RED + "-" + (difference * -1) + " Reflection");
				}
			}
			if (strength != oldStrength) {
				int difference = strength - oldStrength;
				if (strength > oldStrength) {
					PLAYER.sendMessage(ChatColor.GREEN + "+" + difference + " Strength");
				} else {
					PLAYER.sendMessage(ChatColor.RED + "-" + (difference * -1) + " Strength");
				}
			}
			if (thorns != oldThorns) {
				int difference = thorns - oldThorns;
				if (thorns > oldThorns) {
					PLAYER.sendMessage(ChatColor.GREEN + "+" + difference + " Thorns");
				} else {
					PLAYER.sendMessage(ChatColor.RED + "-" + (difference * -1) + " Thorns");
				}
			}
			if (vitality != oldVitality) {
				int difference = vitality - oldVitality;
				if (vitality > oldVitality) {
					PLAYER.sendMessage(ChatColor.GREEN + "+" + difference + " Vitality");
				} else {
					PLAYER.sendMessage(ChatColor.RED + "-" + (difference * -1) + " Vitality");
				}
			}
		}
	}


	public void readWeaponAttributes(boolean showUpdates) {

		PlayerProfileData profile = PLUGIN.getPlayerManager().getPlayerProfile(PLAYER);

		/*---- OLD WEAPON DATA----*/
		int oldArmorPen = profile.getWeaponArmorPenetration();
		int oldBlind = profile.getWeaponBlind();
		int oldCritical = profile.getWeaponCriticalHit();
		int oldDamageMin = profile.getWeaponDamageMax();
		int oldDamageMax = profile.getWeaponDamageMin();
		int oldFireDamage = profile.getWeaponFire();
		int oldIceDamage = profile.getWeaponIce();
		int oldKnockback = profile.getWeaponKnockback();
		int oldLifesteal = profile.getWeaponLifeSteal();
		int oldPoisonDamage = profile.getWeaponPoison();
		int oldPureDamage = profile.getWeaponPure();
		int oldVersusMonster = profile.getWeaponVersusMonster();
		int oldVersusPlayer = profile.getWeaponVersusPlayer();

		/*---- WEAPON DATA ----*/
		int armorPen = new ArmorPenetration().getAttributeValue(PLAYER);
		int blind = new Blind().getAttributeValue(PLAYER);
		int critical = new CriticalHitChance().getAttributeValue(PLAYER);
		int damageMin = new DamageMin().getAttributeValue(PLAYER) + profile.getBaseMaxDamage();
		int damageMax = new DamageMax().getAttributeValue(PLAYER) + profile.getBaseMaxDamage();
		int fireDamage = new FireDamage().getAttributeValue(PLAYER);
		int iceDamage = new IceDamage().getAttributeValue(PLAYER);
		int knockback = new Knockback().getAttributeValue(PLAYER);
		int lifesteal = new LifeSteal().getAttributeValue(PLAYER);
		int poisonDamage = new PoisonDamage().getAttributeValue(PLAYER);
		int pureDamage = new PureDamage().getAttributeValue(PLAYER);
		int versusMonster = new VersusMonster().getAttributeValue(PLAYER);
		int versusPlayer = new VersusPlayer().getAttributeValue(PLAYER);

		profile.setWeaponArmorPenetration(armorPen);
		profile.setWeaponBlind(blind);
		profile.setWeaponCriticalHit(critical);
		profile.setWeaponDamageMax(damageMax);
		profile.setWeaponDamageMin(damageMin);
		profile.setWeaponFire(fireDamage);
		profile.setWeaponIce(iceDamage);
		profile.setWeaponKnockback(knockback);
		profile.setWeaponLifeSteal(lifesteal);
		profile.setWeaponPoison(poisonDamage);
		profile.setWeaponPure(pureDamage);
		profile.setWeaponVersusMonster(versusMonster);
		profile.setWeaponVersusPlayer(versusPlayer);

		//Show debug.
		if (profile.isToggleDebug() && showUpdates) {
			if (armorPen != oldArmorPen) PLAYER.sendMessage("ArmorPen: " + armorPen);
			if (blind != oldBlind) PLAYER.sendMessage("Blind: " + blind);
			if (critical != oldCritical) PLAYER.sendMessage("Critical: " + critical);
			if (damageMax != oldDamageMin) PLAYER.sendMessage("DamageMax: " + damageMax);
			if (damageMin != oldDamageMax) PLAYER.sendMessage("DamageMin: " + damageMin);
			if (fireDamage != oldFireDamage) PLAYER.sendMessage("FireDamage: " + fireDamage);
			if (iceDamage != oldIceDamage) PLAYER.sendMessage("IceDamage: " + iceDamage);
			if (knockback != oldKnockback) PLAYER.sendMessage("Knockback: " + knockback);
			if (lifesteal != oldLifesteal) PLAYER.sendMessage("LifeSteal: " + lifesteal);
			if (poisonDamage != oldPoisonDamage) PLAYER.sendMessage("PoisonDamage: " + poisonDamage);
			if (pureDamage != oldPureDamage) PLAYER.sendMessage("PureDamage: " + pureDamage);
			if (versusMonster != oldVersusMonster) PLAYER.sendMessage("VersusMonster: " + versusMonster);
			if (versusPlayer != oldVersusPlayer) PLAYER.sendMessage("VersusPlayer: " + versusPlayer);
		}
	}
}
