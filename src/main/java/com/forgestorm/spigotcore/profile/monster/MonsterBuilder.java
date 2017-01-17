package com.forgestorm.spigotcore.profile.monster;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.RPGEntityType;
import com.forgestorm.spigotcore.entity.RPGEntity;
import com.forgestorm.spigotcore.entity.boss.EntityEnderDragon;
import com.forgestorm.spigotcore.entity.boss.EntityWither;
import com.forgestorm.spigotcore.entity.hostile.EntityBabyZombie;
import com.forgestorm.spigotcore.entity.hostile.EntityBabyZombieVillager;
import com.forgestorm.spigotcore.entity.hostile.EntityBlaze;
import com.forgestorm.spigotcore.entity.hostile.EntityCreeper;
import com.forgestorm.spigotcore.entity.hostile.EntityElderGuardian;
import com.forgestorm.spigotcore.entity.hostile.EntityEndermite;
import com.forgestorm.spigotcore.entity.hostile.EntityGhast;
import com.forgestorm.spigotcore.entity.hostile.EntityGuardian;
import com.forgestorm.spigotcore.entity.hostile.EntityKillerBunny;
import com.forgestorm.spigotcore.entity.hostile.EntityMagmaCube;
import com.forgestorm.spigotcore.entity.hostile.EntityShulker;
import com.forgestorm.spigotcore.entity.hostile.EntitySilverfish;
import com.forgestorm.spigotcore.entity.hostile.EntitySkeleton;
import com.forgestorm.spigotcore.entity.hostile.EntitySlime;
import com.forgestorm.spigotcore.entity.hostile.EntityWitch;
import com.forgestorm.spigotcore.entity.hostile.EntityWitherSkeleton;
import com.forgestorm.spigotcore.entity.hostile.EntityZombie;
import com.forgestorm.spigotcore.entity.mount.EntityMountDonkey;
import com.forgestorm.spigotcore.entity.mount.EntityMountHorse;
import com.forgestorm.spigotcore.entity.mount.EntityMountMule;
import com.forgestorm.spigotcore.entity.mount.EntityMountPig;
import com.forgestorm.spigotcore.entity.mount.EntityMountSkeletonHorse;
import com.forgestorm.spigotcore.entity.mount.EntityMountZombieHorse;
import com.forgestorm.spigotcore.entity.neutral.EntityBabyWolf;
import com.forgestorm.spigotcore.entity.neutral.EntityBabyZombiePigman;
import com.forgestorm.spigotcore.entity.neutral.EntityCaveSpider;
import com.forgestorm.spigotcore.entity.neutral.EntityEnderman;
import com.forgestorm.spigotcore.entity.neutral.EntityIronGolem;
import com.forgestorm.spigotcore.entity.neutral.EntitySpider;
import com.forgestorm.spigotcore.entity.neutral.EntityWolf;
import com.forgestorm.spigotcore.entity.neutral.EntityZombiePigman;
import com.forgestorm.spigotcore.entity.passive.EntityBabyChicken;
import com.forgestorm.spigotcore.entity.passive.EntityBabyCow;
import com.forgestorm.spigotcore.entity.passive.EntityBabyHorse;
import com.forgestorm.spigotcore.entity.passive.EntityBabyMooshroom;
import com.forgestorm.spigotcore.entity.passive.EntityBabyOcelot;
import com.forgestorm.spigotcore.entity.passive.EntityBabyPig;
import com.forgestorm.spigotcore.entity.passive.EntityBabyRabbit;
import com.forgestorm.spigotcore.entity.passive.EntityBabySheep;
import com.forgestorm.spigotcore.entity.passive.EntityBabySkeletonHorse;
import com.forgestorm.spigotcore.entity.passive.EntityBabyVillager;
import com.forgestorm.spigotcore.entity.passive.EntityBabyZombieHorse;
import com.forgestorm.spigotcore.entity.passive.EntityBat;
import com.forgestorm.spigotcore.entity.passive.EntityChicken;
import com.forgestorm.spigotcore.entity.passive.EntityCow;
import com.forgestorm.spigotcore.entity.passive.EntityGiant;
import com.forgestorm.spigotcore.entity.passive.EntityMooshroom;
import com.forgestorm.spigotcore.entity.passive.EntityOcelot;
import com.forgestorm.spigotcore.entity.passive.EntityPig;
import com.forgestorm.spigotcore.entity.passive.EntityRabbit;
import com.forgestorm.spigotcore.entity.passive.EntitySheep;
import com.forgestorm.spigotcore.entity.passive.EntitySnowGolem;
import com.forgestorm.spigotcore.entity.passive.EntitySquid;
import com.forgestorm.spigotcore.entity.passive.EntityVillager;
import com.forgestorm.spigotcore.profile.ProfileData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonsterBuilder {
	
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
	private boolean leggins;
	private boolean boots;
	private boolean shield;
	private boolean axe;
	private boolean bow;
	private boolean spear;
	private boolean sword;
	private boolean wand;
	private String lootTable;
	private boolean usingMagic;
	
	private ProfileData profile;
	
	MonsterBuilder(SpigotCore plugin) {
		PLUGIN = plugin;
	}
	
	void build(Location location) {
		//Apply attributes
		profile = new MonsterProfileData();
		applyAttributesToProfile();
		
		//Create RPGEntity data class.
		RPGEntity monster = getRPGEntityFromENUM(RPGEntityType.valueOf(type.toUpperCase()), location);
		
		//Spawn the monster
		monster.spawn();
		
		//Save the monsters name to the profile.
		profile.setName(monster.getName());
		
		//Set profile
		PLUGIN.getEntityManager().addRPGEntity(monster);
		
		//Set name
		monster.getEntity().setCustomName(name);
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
	
	private RPGEntity getRPGEntityFromENUM(RPGEntityType entityType, Location location) {
		
		switch(entityType) {
		case BABY_CHICKEN:
			return new EntityBabyChicken(name, level, location, profile);
		case BABY_COW:
			return new EntityBabyCow(name, level, location, profile);
		case BABY_HORSE:
			return new EntityBabyHorse(name, level, location, profile);
		case BABY_MOOSHROOM:
			return new EntityBabyMooshroom(name, level, location, profile);
		case BABY_OCELOT:
			return new EntityBabyOcelot(name, level, location, profile);
		case BABY_PIG:
			return new EntityBabyPig(name, level, location, profile);
		case BABY_RABBIT:
			return new EntityBabyRabbit(name, level, location, profile);
		case BABY_SHEEP:
			return new EntityBabySheep(name, level, location, profile);
		case BABY_SKELETON_HORSE:
			return new EntityBabySkeletonHorse(name, level, location, profile);
		case BABY_VILLAGER:
			return new EntityBabyVillager(name, level, location, profile);
		case BABY_WOLF:
			return new EntityBabyWolf(name, level, location, profile);
		case BABY_ZOMBIE:
			return new EntityBabyZombie(name, level, location, profile);
		case BABY_ZOMBIE_HORSE:
			return new EntityBabyZombieHorse(name, level, location, profile);
		case BABY_ZOMBIE_PIGMAN:
			return new EntityBabyZombiePigman(name, level, location, profile);
		case BABY_ZOMBIE_VILLAGER:
			return new EntityBabyZombieVillager(name, level, location, profile);
		case BAT:
			return new EntityBat(name, level, location, profile);
		case BLAZE:
			return new EntityBlaze(name, level, location, profile);
		case CAVE_SPIDER:
			return new EntityCaveSpider(name, level, location, profile);
		case CHICKEN:
			return new EntityChicken(name, level, location, profile);
		case COW:
			return new EntityCow(name, level, location, profile);
		case CREEPER:
			return new EntityCreeper(name, level, location, profile);
		case ELDER_GUARDIAN:
			return new EntityElderGuardian(name, level, location, profile);
		case ENDERMAN:
			return new EntityEnderman(name, level, location, profile);
		case ENDERMITE:
			return new EntityEndermite(name, level, location, profile);
		case ENDER_DRAGON:
			return new EntityEnderDragon(name, level, location, profile);
		case GHAST:
			return new EntityGhast(name, level, location, profile);
		case GIANT:
			return new EntityGiant(name, level, location, profile);
		case GUARDIAN:
			return new EntityGuardian(name, level, location, profile);
		case IRON_GOLEM:
			return new EntityIronGolem(name, level, location, profile);
		case KILLER_BUNNY:
			return new EntityKillerBunny(name, level, location, profile);
		case MAGMA_CUBE:
			return new EntityMagmaCube(name, level, location, profile);
		case MOOSHROOM:
			return new EntityMooshroom(name, level, location, profile);
		case MOUNT_DONKEY:
			return new EntityMountDonkey(name, level, location, profile);
		case MOUNT_HORSE:
			return new EntityMountHorse(name, level, location, profile);
		case MOUNT_MULE:
			return new EntityMountMule(name, level, location, profile);
		case MOUNT_PIG:
			return new EntityMountPig(name, level, location, profile);
		case MOUNT_SKELETON_HORSE:
			return new EntityMountSkeletonHorse(name, level, location, profile);
		case MOUNT_ZOMBIE_HORSE:
			return new EntityMountZombieHorse(name, level, location, profile);
		case OCELOT:
			return new EntityOcelot(name, level, location, profile);
		case PIG:
			return new EntityPig(name, level, location, profile);
		case RABBIT:
			return new EntityRabbit(name, level, location, profile);
		case SHEEP:
			return new EntitySheep(name, level, location, profile);
		case SHULKER:
			return new EntityShulker(name, level, location, profile);
		case SILVERFISH:
			return new EntitySilverfish(name, level, location, profile);
		case SKELETON:
			return new EntitySkeleton(name, level, location, profile);
		case SLIME:
			return new EntitySlime(name, level, location, profile);
		case SNOW_GOLEM:
			return new EntitySnowGolem(name, level, location, profile);
		case SPIDER:
			return new EntitySpider(name, level, location, profile);
		case SQUID:
			return new EntitySquid(name, level, location, profile);
		case VILLAGER:
			return new EntityVillager(name, level, location, profile);
		case WITCH:
			return new EntityWitch(name, level, location, profile);
		case WITHER:
			return new EntityWither(name, level, location, profile);
		case WITHER_SKELETON:
			return new EntityWitherSkeleton(name, level, location, profile);
		case WOLF:
			return new EntityWolf(name, level, location, profile);
		case ZOMBIE:
			return new EntityZombie(name, level, location, profile);
		case ZOMBIE_PIGMAN:
			return new EntityZombiePigman(name, level, location, profile);
		default:
			Bukkit.getLogger().info("MOB: " + name + " WAS NOT SPAWNED!");
			break;
		}
		return null;
	}
}