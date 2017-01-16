package com.forgestorm.spigotcore.combat;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.profile.ProfileData;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;

abstract class Combat {

	protected final SpigotCore PLUGIN;
	protected final LivingEntity DAMAGER;
	protected final LivingEntity DEFENDER;

	protected ProfileData damagerProfile;
	protected ProfileData defenderProfile;

	protected double damagerHealth;
	protected double defenderHealth;

	/*---- TODO: PLAYER STAT POINT ----*/
	protected int dexterity;
	protected int intelect;
	protected int strength;
	protected int vitality;

	/*---- DAMAGER WEAPON ----*/
	protected int damagerWeaponDamageMax;
	protected int damagerWeaponDamageMin;
	protected int damagerWeaponLifeSteal;
	protected int damagerWeaponKnockback;
	protected int damagerWeaponCriticalHit;
	protected int damagerWeaponBlind;
	protected int damagerWeaponVersusPlayer;
	protected int damagerWeaponVersusMonster;
	protected int damagerWeaponPure;
	protected int damagerWeaponArmorPenetration;
	protected int damagerWeaponIce;
	protected int damagerWeaponFire;
	protected int damagerWeaponPoison;

	/*---- DEFENDER ARMOR ----*/
	protected int defenderArmorMax;
	protected int defenderArmorMin;
	protected int defenderArmorBlock;
	protected int defenderArmorDodge;
	protected int defenderArmorThorns;
	protected int defenderArmorReflection;
	protected int defenderArmorIceResistance;
	protected int defenderArmorFireResistance;
	protected int defenderArmorPoisonResistance;

	/*---- COMBAT ----*/
	private String worldName;
	private Location location;

	private boolean reflection;
	protected boolean thorns;
	private boolean elementalDamage;
	private boolean iceDamage;
	private boolean fireDamage;
	protected boolean poisonDamage;

	private boolean cancelDamage;
	private double combatDamage;
	private double damageReduction;

	private Random random = new Random();

	Combat(SpigotCore plugin, LivingEntity damager, LivingEntity defender) {
		PLUGIN = plugin;
		DAMAGER = damager;
		DEFENDER = defender;
		
		worldName = damager.getWorld().getName();
	}

	protected abstract void implementAttributeVariables();
	protected abstract void applyDamage(double defenderDamage, double damagerDamage);
	
	/**
	 * Applies damage for this hit calculation.
	 */
	public void doCalculations() {
		//Get Variable Values
		implementAttributeVariables();

		//Do combat calculations
		damageReduction = calculateArmor() / 100;
		combatDamage = calculateDamage();
		
		System.out.println("-----------------");
		System.out.println("DMG: " + combatDamage + "    ARMOR: " + damageReduction);

		//Apply the new health
		double damagePercent = combatDamage * damageReduction; //combatDamage - damageReduction
		double damage = combatDamage - damagePercent;
		
		System.out.println("FINAL DMG: " + damage + "     DMG%: " + damagePercent);
		
		if (damage > 0) {
			if (!cancelDamage) {
				applyDamage(damage, 0);
			} else if (reflection && !cancelDamage) {
				applyDamage(0, damage);
			}
		}
		
		//Update the players HP under their name tag.
		if (DEFENDER instanceof Player) {
			PLUGIN.getScoreboardManager().updatePlayerHP((PlayerProfileData) defenderProfile, (Player) DEFENDER);
		}
		if (DAMAGER instanceof Player) {
			PLUGIN.getScoreboardManager().updatePlayerHP((PlayerProfileData) damagerProfile, (Player) DAMAGER);
		}
	}

	/**
	 * Calculates damage based on weapon attribute points.
	 * 
	 * @return Returns the final damage count.
	 */
	private double calculateDamage() {
		double damage = 0;

		if (!cancelDamage) {

			//DAMAGE
			damage += randomizeAttribute(damagerWeaponDamageMin, damagerWeaponDamageMax);
			System.out.println("wpnDmg: " + damage);

			//LIFESTEAL
			if (attributeChance(damagerWeaponLifeSteal)){
				double lifeTaken = (damagerWeaponLifeSteal / 100) * defenderHealth;
				damage += lifeTaken;

				//Add the life back to the player
				damagerHealth += lifeTaken;

				//Play Life Steal sound.
				//TODO

				//Send Life Steal Message
				sendMessage(DEFENDER, ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "                   *OPPONENT LIFESTEAL* - " + ChatColor.RED + lifeTaken);
				sendMessage(DAMAGER, ChatColor.RED + "" + ChatColor.BOLD + "                        *LIFESTEAL* +" + ChatColor.GREEN + lifeTaken);
			}

			//Knockback
			//TODO

			//CRITICAL HIT
			if (attributeChance(damagerWeaponCriticalHit)) {
				damage += (damagerWeaponCriticalHit / 100) * damage;

				System.out.println("crit: " + damage);

				//Send messages.
				sendMessage(DEFENDER, ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "                        *CRITICAL HIT*");
				sendMessage(DAMAGER, ChatColor.RED + "" + ChatColor.BOLD + "                   *CRITICAL HIT* (" + ChatColor.RESET + defenderProfile.getName() + ChatColor.RED + ChatColor.BOLD + ")");
			}

			//BLIND
			if (attributeChance(damagerWeaponBlind)){
				DEFENDER.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3 * 20, 100));
			}

			//VERSUS PLAYER
			if (attributeChance(damagerWeaponVersusPlayer) && DEFENDER instanceof Player) {
				damage += (damagerWeaponVersusPlayer / 100) * damage;

				System.out.println("vsPlayer: " + damage);

				//Send messages.
				sendMessage(DEFENDER, "VS PLAYER");
				sendMessage(DAMAGER, "VS PLAYER");
			}

			//VERSUS MONSTER
			if (attributeChance(damagerWeaponVersusMonster) && !(DEFENDER instanceof Player)) {
				damage += (damagerWeaponVersusMonster / 100) * damage;

				System.out.println("vsMonster: " + damage);

				//Send messages.
				sendMessage(DEFENDER, "VS MONSTER");
				sendMessage(DAMAGER, "VS MONSTER");
			}

			//PURE DAMAGE
			if (attributeChance(damagerWeaponPure)) {
				damage += (damagerWeaponPure / 100) * damage;

				System.out.println("pureDMG: " + damage);

				//Send messages.
				DEFENDER.sendMessage("PURE DMG");
				DAMAGER.sendMessage("PURE DMG");
			}

			//ARMOR PENETRATION
			if (attributeChance(damagerWeaponArmorPenetration)) {
				damage += damagerWeaponArmorPenetration;

				System.out.println("armorPen: " + damage);

				//Send messages.
				DEFENDER.sendMessage("ARMOR PEN");
				DAMAGER.sendMessage("ARMOR PEN");
			}

			//ICE
			if (attributeChance(damagerWeaponIce)){
				damage += damagerWeaponIce;
				elementalDamage = true;
				iceDamage = true;

				System.out.println("iceDMG: " + damage);

				//POTION EFFECT
				DEFENDER.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, damagerWeaponPoison * 20, 50));
			}

			//FIRE
			if (attributeChance(damagerWeaponFire) && !elementalDamage){
				damage += damagerWeaponFire;
				elementalDamage = true;
				fireDamage = true;

				System.out.println("fireDMG: " + damage);

				//POTION EFFECT
				DEFENDER.setFireTicks(damagerWeaponFire);
			}

			//POISON
			if (attributeChance(damagerWeaponPoison) && !elementalDamage){
				damage += damagerWeaponPoison;
				elementalDamage = true;
				poisonDamage = true;

				System.out.println("poisonDMG: " + damage);

				//POTION EFFECT
				DEFENDER.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, damagerWeaponPoison * 20, 100));
				DEFENDER.addPotionEffect(new PotionEffect(PotionEffectType.POISON, damagerWeaponPoison * 20, 1));
			}
		}

		return damage;
	}

	/**
	 * Calculates damage reduction based on armor attribute points.
	 * 
	 * @return Returns the final damage reduction count.
	 */
	private double calculateArmor() {
		double damageReduction = 0;

		damageReduction += randomizeAttribute(defenderArmorMin, defenderArmorMax);

		//BLOCK
		if (attributeChance(defenderArmorBlock)) {
			cancelDamage = true;
			
			//Show particle effect.

			//Play block sound
			Bukkit.getWorld(worldName).playSound(location, Sound.BLOCK_METAL_HIT, 2F, 1.0F);

			//Send messages.
			sendMessage(DEFENDER, ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "                        *BLOCK*");
			sendMessage(DAMAGER, ChatColor.RED + "" + ChatColor.BOLD + "                *OPPONENT BLOCKED* (" + ChatColor.RESET + defenderProfile.getName() + ChatColor.RED + ChatColor.BOLD + ")");
		}

		//DODGE
		if (attributeChance(defenderArmorDodge) && !cancelDamage) {
			cancelDamage = true;

			//Play sound.
			Bukkit.getWorld(worldName).playSound(location, Sound.ENTITY_ZOMBIE_INFECT, 2F, 1.5F);

			//Send Message
			sendMessage(DEFENDER, ChatColor.GREEN + "" + ChatColor.BOLD + "                        *DODGE*");
			sendMessage(DAMAGER, ChatColor.RED + "" + ChatColor.BOLD + "                *OPPONENT DODGED* (" + ChatColor.RESET + defenderProfile.getName() + ChatColor.RED + ChatColor.BOLD + ")");
		}

		//THORNS
		if (attributeChance(defenderArmorThorns)) {
			thorns = true;
		}

		//REFLECTION
		if (attributeChance(defenderArmorReflection)) {
			reflection = true;
		}

		//ICE RESISTANCE
		if (attributeChance(defenderArmorIceResistance) && iceDamage) {
			damageReduction += (defenderArmorIceResistance / 100) * damageReduction;

			//Send messages.
			sendMessage(DEFENDER, "Ice resist");
			sendMessage(DAMAGER, "Ice resist");
		}

		//FIRE RESISTANCE
		if (attributeChance(defenderArmorFireResistance) && fireDamage) {
			damageReduction += (defenderArmorFireResistance / 100) * damageReduction;

			//Send messages.
			sendMessage(DEFENDER, "Fire resist");
			sendMessage(DAMAGER, "Fire resist");
		}

		//POISON RESISTANCE
		if (attributeChance(defenderArmorPoisonResistance) && iceDamage) {
			damageReduction += (defenderArmorPoisonResistance / 100) * damageReduction;

			//Send messages.
			sendMessage(DEFENDER, "Poison resist");
			sendMessage(DAMAGER, "Poison resist");
		}

		return damageReduction;
	}

	/**
	 * Sends a combat message if the entity is a Player.
	 * 
	 * @param entity The entity that may get a combat message.
	 * @param message The message to send.
	 */
	protected void sendMessage(LivingEntity entity, String message) {
		if (entity instanceof Player) {
			entity.sendMessage(message);
		}
	}

	private int randomizeAttribute(int min, int max) {
		return random.nextInt(max - min + 1) + min;
	}

	/**
	 * Gets the chance for a particular attribute to activate.
	 * 
	 * @param percent The percent for the attribute to activate.
	 * @return Returns true if the roll was successful.
	 */
	private boolean attributeChance(int percent) {
		Random random = new Random();
		int success = random.nextInt(100) + 1;

		if (percent >= success) {
			return  true;
		}
		return false;
	}
}
