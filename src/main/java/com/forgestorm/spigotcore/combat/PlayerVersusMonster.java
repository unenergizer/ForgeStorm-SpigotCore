package com.forgestorm.spigotcore.combat;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.Messages;
import com.forgestorm.spigotcore.entity.RPGEntity;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;

import net.md_5.bungee.api.ChatColor;

public class PlayerVersusMonster extends Combat {


	public PlayerVersusMonster(SpigotCore plugin, LivingEntity damager, LivingEntity defender) {
		super(plugin, damager, defender);
	}

	@Override
	protected void implementAttributeVariables() {
		damagerProfile = PLUGIN.getPlayerManager().getPlayerProfile((Player) DAMAGER); //Player
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
		
		PlayerProfileData profile = (PlayerProfileData) damagerProfile;
		
		//Mob
		if (profile.isFatigued()) {
			sendMessage(DAMAGER, ChatColor.RED + "No damage delt, you are fatigued!");
		} else if (defenderDamage > 0) {
			double health = defenderProfile.getHealth();
			double remainingHealth = health - defenderDamage;
			defenderProfile.setHealth(remainingHealth);
			
			RPGEntity defender = PLUGIN.getEntityManager().getRPGEntity(DEFENDER.getUniqueId());
			defender.toggleDamage();
			
			if (remainingHealth < 1) {
				defender.toggleDeath();
				PLUGIN.getEntityManager().removeRPGEntity(DEFENDER.getUniqueId());
			}
			
			//Send Message
			String damage = Integer.toString((int) defenderDamage);
			String name = defender.getName();
			String hp = Integer.toString((int) remainingHealth);
			sendMessage(DAMAGER, Messages.DAMAGE_OUTPUT_OPPONENT.toString().replace("%s", damage).replace("%name%", name).replace("%f", hp));
		}
		
		//Player
		if (damagerDamage > 0) {
			double health = damagerProfile.getHealth();
			double remainingHealth = health - damagerDamage;
			damagerProfile.setHealth(remainingHealth);
			
			if (remainingHealth < 1) {
				//Respawn player.
				DAMAGER.sendMessage("You should die.");
				DAMAGER.setHealth(0);
			}
		}
	}
}
