package com.forgestorm.spigotcore.combat;

import org.bukkit.entity.LivingEntity;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.entity.RPGEntity;

public class MonsterVersusMonster extends Combat {


	public MonsterVersusMonster(SpigotCore plugin, LivingEntity damager, LivingEntity defender) {
		super(plugin, damager, defender);
	}

	@Override
	protected void implementAttributeVariables() {
		damagerProfile = PLUGIN.getEntityManager().getRPGEntity(DAMAGER.getUniqueId()).getProfile(); //Monster
		defenderProfile = PLUGIN.getEntityManager().getRPGEntity(DEFENDER.getUniqueId()).getProfile(); //Monster

		damagerHealth = damagerProfile.getHealth();
		defenderHealth = defenderProfile.getHealth();

		/*---- DAMAGER WEAPON ----*/
		damagerWeaponDamageMax = damagerProfile.getWeaponDamageMax();
		damagerWeaponDamageMin = damagerProfile.getWeaponDamageMin();
		damagerWeaponLifeSteal = damagerProfile.getWeaponLifeSteal();
		damagerWeaponKnockback = damagerProfile.getWeaponKnockback();
		damagerWeaponCriticalHit = damagerProfile.getWeaponCriticalHit();
		damagerWeaponBlind = damagerProfile.getWeaponBlind();
		damagerWeaponVersusPlayer = damagerProfile.getWeaponVersusPlayer();
		damagerWeaponVersusMonster = damagerProfile.getWeaponVersusMonster();
		damagerWeaponPure = damagerProfile.getWeaponPure();
		damagerWeaponArmorPenetration = damagerProfile.getWeaponArmorPenetration();
		damagerWeaponIce = damagerProfile.getWeaponIce();
		damagerWeaponFire = damagerProfile.getWeaponFire();
		damagerWeaponPoison = damagerProfile.getWeaponPoison();

		/*---- DEFENDER ARMOR ----*/
		defenderArmorMax = defenderProfile.getArmorMax();
		defenderArmorMin = defenderProfile.getArmorMin();
		defenderArmorBlock = defenderProfile.getArmorBlock();
		defenderArmorDodge = defenderProfile.getArmorDodge();
		defenderArmorThorns = defenderProfile.getArmorThorns();
		defenderArmorReflection = defenderProfile.getArmorReflection();
		defenderArmorIceResistance = defenderProfile.getArmorIceResistance();
		defenderArmorFireResistance = defenderProfile.getArmorFireResistance();
		defenderArmorPoisonResistance = defenderProfile.getArmorPoisonResistance();	
	}

	@Override
	protected void applyDamage(double defenderDamage, double damagerDamage) {
		
		//Mob
		if (defenderDamage > 0) {
			double health = defenderProfile.getHealth();
			double remainingHealth = health - defenderDamage;
			defenderProfile.setHealth(remainingHealth);
			
			RPGEntity defender = PLUGIN.getEntityManager().getRPGEntity(DEFENDER.getUniqueId());
			defender.toggleDamage();
			
			if (remainingHealth < 1) {
				defender.toggleDeath();
				PLUGIN.getEntityManager().removeRPGEntity(DEFENDER.getUniqueId());
			}
		}
		
		//Mob
		if (damagerDamage > 0) {
			double health = damagerProfile.getHealth();
			double remainingHealth = health - damagerDamage;
			damagerProfile.setHealth(remainingHealth);
			
			RPGEntity damager = PLUGIN.getEntityManager().getRPGEntity(DAMAGER.getUniqueId());
			damager.toggleDamage();
			
			if (remainingHealth < 1) {
				damager.toggleDeath();
				PLUGIN.getEntityManager().removeRPGEntity(DAMAGER.getUniqueId());
			}
		}
	}
}
