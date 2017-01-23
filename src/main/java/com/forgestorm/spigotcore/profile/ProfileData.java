package com.forgestorm.spigotcore.profile;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public abstract class ProfileData {
	
	protected int id;
	protected UUID uuid;
	protected String name;
	protected double health;
	protected int maxHealth;
	protected float energy;
	protected float maxEnergy = 100;
	
	/*---- BASE STATS ----*/
	protected int baseMaxHealth = 50;
	protected int baseHealthRegen = 1;
	protected int baseEnergyRegen = 10; //10% Regen rate increase.
	protected int baseMaxDamage = 1;
	
	/*---- ENTITY ----*/
	protected int dexterity;
	protected int intellect;
	protected int strength;
	protected int vitality;
	
	/*---- ARMOR ----*/
	protected int armorMax;
	protected int armorMin;
	protected int armorHealthRegen;
	protected int armorHealthIncrease;
	protected int armorEnergyRegen;
	protected int armorBlock;
	protected int armorDodge;
	protected int armorThorns;
	protected int armorReflection;
	protected int armorIceResistance;
	protected int armorFireResistance;
	protected int armorPoisonResistance;
	
	/*---- WEAPON ----*/
	protected int weaponDamageMax;
	protected int weaponDamageMin;
	protected int weaponLifeSteal;
	protected int weaponKnockback;
	protected int weaponCriticalHit;
	protected int weaponBlind;
	protected int weaponVersusPlayer;
	protected int weaponVersusMonster;
	protected int weaponPure;
	protected int weaponArmorPenetration;
	protected int weaponIce;
	protected int weaponFire;
	protected int weaponPoison;

	public void setHealth(double amount) {
		if (amount <= 0) {
			health = 0;
		} else {
			health = amount;
		}
	}
}
