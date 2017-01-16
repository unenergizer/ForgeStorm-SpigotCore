package com.forgestorm.spigotcore.combat;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.Messages;
import com.forgestorm.spigotcore.entity.RPGEntity;

public class MonsterVersusPlayer extends Combat {


	public MonsterVersusPlayer(SpigotCore plugin, LivingEntity damager, LivingEntity defender) {
		super(plugin, damager, defender);
	}

	@Override
	protected void implementAttributeVariables() {
		damagerProfile = PLUGIN.getEntityManager().getRPGEntity(DAMAGER.getUniqueId()).getProfile(); //Monster
		defenderProfile = PLUGIN.getPlayerManager().getPlayerProfile((Player)DEFENDER); //Player

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
		//Player
		if (defenderDamage > 0) {
			double health = defenderProfile.getHealth();
			double remainingHealth = health - defenderDamage;
			defenderProfile.setHealth(remainingHealth);
			
			double hpDisplay = (remainingHealth / defenderProfile.getMaxHealth()) * 20;
			
			//Set hearts
			if (hpDisplay > 20) {
				DEFENDER.setHealth(20);
			} else if (hpDisplay <= 0) {
				DEFENDER.setHealth(1);
			} else {
				DEFENDER.setHealth(hpDisplay);
			}
			
			if (remainingHealth < 1) {
				//Respawn player.
				DEFENDER.sendMessage("You should die.");
				DEFENDER.setHealth(0);
			}
			
			//Send Message
			String damage = Integer.toString((int) defenderDamage);
			String hp = Integer.toString((int) remainingHealth);
			sendMessage(DEFENDER, Messages.DAMAGE_OUTPUT_PLAYER.toString().replace("%s", damage).replace("%f", hp));
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
