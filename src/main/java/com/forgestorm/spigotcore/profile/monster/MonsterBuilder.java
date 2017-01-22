package com.forgestorm.spigotcore.profile.monster;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.RPGEntityType;
import com.forgestorm.spigotcore.entity.RPGEntity;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Location;

@Getter
@Setter
class MonsterBuilder {
	
	private final SpigotCore PLUGIN;
	
	private String name;
	private String type;
	private String tier;
	private int level;
	private int health;
	private int ice;
	private int fire;
	private int poison;
	private boolean playerSkull;
	private String skullOwner;
	private boolean helmet;
	private boolean chestplate;
	private boolean leggings;
	private boolean boots;
	private boolean shield;
	private boolean axe;
	private boolean bow;
	private boolean spear;
	private boolean sword;
	private boolean wand;
	private String lootTable;
	private boolean usingMagic;
	
	private MonsterProfileData profile;
	
	MonsterBuilder(SpigotCore plugin) {
		PLUGIN = plugin;
	}
	
	void build(Location location) {
		//Apply attributes
		profile = new MonsterProfileData();
		applyAttributesToProfile();
		
		//Create RPGEntity data class.
		RPGEntityType entityType = RPGEntityType.valueOf(type.toUpperCase());
		RPGEntity rpgEntity = entityType.getType(name, level, location, profile);

		System.out.println(" >>> " + name + " >>> " + level + " >>> " + location.toString() + " >>> " + profile.toString());
		
		//Spawn the monster
		rpgEntity.spawn();
		
		//Save the monsters name to the profile.
		profile.setName(rpgEntity.getName());
		
		//Set profile
		PLUGIN.getEntityManager().addRPGEntity(rpgEntity);
		
		//Set name
		rpgEntity.getEntity().setCustomName(name);
	}
	
	private void applyAttributesToProfile() {
		
		double maxHealth = profile.getBaseMaxHealth() + health;

		profile.setHealth(maxHealth);
		profile.setMaxHealth((int) maxHealth);
	
		
		profile.setArmorMin(0);
		profile.setArmorMax(0);
		profile.setArmorBlock(calculatePercents() / 3);
		profile.setArmorDodge(calculatePercents() / 3);
		profile.setArmorThorns(calculatePercents() / 3);
		profile.setArmorReflection(calculatePercents() / 3);
		profile.setArmorIceResistance(calculatePercents() / 3);
		profile.setArmorFireResistance(calculatePercents() / 3);
		profile.setArmorPoisonResistance(calculatePercents() / 3);

		/*---- WEAPON ----*/
		profile.setWeaponDamageMin(calculatePercents());
		profile.setWeaponDamageMax(calculatePercents());
		profile.setWeaponLifeSteal(calculatePercents() / 3);
		profile.setWeaponKnockback(calculatePercents() / 3);
		profile.setWeaponCriticalHit(calculatePercents() / 3);
		profile.setWeaponBlind(calculatePercents() / 3);
		profile.setWeaponVersusPlayer(calculatePercents() / 3);
		profile.setWeaponVersusMonster(calculatePercents() / 3);
		profile.setWeaponPure(calculatePercents() / 3);
		profile.setWeaponArmorPenetration(calculatePercents() / 3);
		
		if (ice > 70 && !usingMagic) {
			profile.setWeaponIce(calculatePercents());
			name = ChatColor.BLUE + "Ice " + name;
			
		} else {
			profile.setWeaponIce(0);
		}
		
		if (fire > 70 && !usingMagic) {
			profile.setWeaponFire(calculatePercents());
			name = ChatColor.RED + "Fire " + name;
			
		} else {
			profile.setWeaponFire(0);
		}		
		
		if (poison > 70 && !usingMagic) {
			profile.setWeaponPoison(calculatePercents());
			name = ChatColor.DARK_GREEN + "Poison " + name;
			
		} else {
			profile.setWeaponPoison(0);
		}
		
	}

	private int calculatePercents() {
		return level;
	}
}