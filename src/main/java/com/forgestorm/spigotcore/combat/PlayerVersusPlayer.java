package com.forgestorm.spigotcore.combat;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.Messages;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;

import net.md_5.bungee.api.ChatColor;

public class PlayerVersusPlayer extends Combat {

	public PlayerVersusPlayer(SpigotCore plugin, LivingEntity damager, LivingEntity defender) {
		super(plugin, damager, defender);
	}

	@Override
	protected void implementAttributeVariables() {
		damagerProfile = PLUGIN.getPlayerManager().getPlayerProfile((Player) DAMAGER);
		defenderProfile = PLUGIN.getPlayerManager().getPlayerProfile((Player)DEFENDER);
		
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
		
		//Player
		if (profile.isFatigued()) {
			sendMessage(DAMAGER, ChatColor.RED + "No damage delt, you are fatigued!");
		} else if (defenderDamage > 0 && !profile.isFatigued()) {
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
			
			//Kill Player
			if (remainingHealth < 1) {
				//Respawn player.
				DEFENDER.sendMessage("You should die. 1");
				DEFENDER.setHealth(0);
			}
			
			//Send Message
			String damage = Integer.toString((int) defenderDamage);
			String name = DEFENDER.getName();
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
				DAMAGER.sendMessage("You should die. 2");
				DAMAGER.setHealth(0);
			}
		}
	}
}
