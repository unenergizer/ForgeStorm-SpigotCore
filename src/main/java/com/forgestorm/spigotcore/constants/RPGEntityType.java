package com.forgestorm.spigotcore.constants;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;

import com.forgestorm.spigotcore.entity.RPGEntity;
import com.forgestorm.spigotcore.entity.boss.EntityEnderDragon;
import com.forgestorm.spigotcore.entity.boss.EntityWither;
import com.forgestorm.spigotcore.entity.hostile.EntityBabyZombie;
import com.forgestorm.spigotcore.entity.hostile.EntityBlaze;
import com.forgestorm.spigotcore.entity.hostile.EntityCreeper;
import com.forgestorm.spigotcore.entity.hostile.EntityElderGuardian;
import com.forgestorm.spigotcore.entity.hostile.EntityEndermite;
import com.forgestorm.spigotcore.entity.hostile.EntityEvoker;
import com.forgestorm.spigotcore.entity.hostile.EntityEvokerFangs;
import com.forgestorm.spigotcore.entity.hostile.EntityGhast;
import com.forgestorm.spigotcore.entity.hostile.EntityGuardian;
import com.forgestorm.spigotcore.entity.hostile.EntityHusk;
import com.forgestorm.spigotcore.entity.hostile.EntityKillerBunny;
import com.forgestorm.spigotcore.entity.hostile.EntityMagmaCube;
import com.forgestorm.spigotcore.entity.hostile.EntityShulker;
import com.forgestorm.spigotcore.entity.hostile.EntitySilverfish;
import com.forgestorm.spigotcore.entity.hostile.EntitySkeleton;
import com.forgestorm.spigotcore.entity.hostile.EntitySlime;
import com.forgestorm.spigotcore.entity.hostile.EntityStray;
import com.forgestorm.spigotcore.entity.hostile.EntityVex;
import com.forgestorm.spigotcore.entity.hostile.EntityVindicator;
import com.forgestorm.spigotcore.entity.hostile.EntityWitch;
import com.forgestorm.spigotcore.entity.hostile.EntityWitherSkeleton;
import com.forgestorm.spigotcore.entity.hostile.EntityZombie;
import com.forgestorm.spigotcore.entity.mount.EntityMountDonkey;
import com.forgestorm.spigotcore.entity.mount.EntityMountHorse;
import com.forgestorm.spigotcore.entity.mount.EntityMountLlama;
import com.forgestorm.spigotcore.entity.mount.EntityMountMule;
import com.forgestorm.spigotcore.entity.mount.EntityMountSkeletonHorse;
import com.forgestorm.spigotcore.entity.mount.EntityMountZombieHorse;
import com.forgestorm.spigotcore.entity.neutral.EntityBabyPolarBear;
import com.forgestorm.spigotcore.entity.neutral.EntityBabyWolf;
import com.forgestorm.spigotcore.entity.neutral.EntityBabyZombiePigman;
import com.forgestorm.spigotcore.entity.neutral.EntityCaveSpider;
import com.forgestorm.spigotcore.entity.neutral.EntityEnderman;
import com.forgestorm.spigotcore.entity.neutral.EntityIronGolem;
import com.forgestorm.spigotcore.entity.neutral.EntityPolarBear;
import com.forgestorm.spigotcore.entity.neutral.EntitySpider;
import com.forgestorm.spigotcore.entity.neutral.EntityWolf;
import com.forgestorm.spigotcore.entity.neutral.EntityZombiePigman;
import com.forgestorm.spigotcore.entity.passive.EntityBabyChicken;
import com.forgestorm.spigotcore.entity.passive.EntityBabyCow;
import com.forgestorm.spigotcore.entity.passive.EntityBabyHorse;
import com.forgestorm.spigotcore.entity.passive.EntityBabyLlama;
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


public enum RPGEntityType {
	//BOSS
	ENDER_DRAGON(EntityEnderDragon.class),
	WITHER(EntityWither.class),
	
	//HOSTILE
	BABY_ZOMBIE(EntityBabyZombie.class),
	BABY_ZOMBIE_VILLAGER(EntityBabyVillager.class),
	BLAZE(EntityBlaze.class),
	CREEPER(EntityCreeper.class),
	ELDER_GUARDIAN(EntityElderGuardian.class),
	ENDERMITE(EntityEndermite.class),
	EVOKER(EntityEvoker.class),
	EVOKER_FANGS(EntityEvokerFangs.class),
	GHAST(EntityGhast.class),
	GUARDIAN(EntityGuardian.class),
	HUSK(EntityHusk.class),
	KILLER_BUNNY(EntityKillerBunny.class),
	MAGMA_CUBE(EntityMagmaCube.class),
	SHULKER(EntityShulker.class),
	SILVERFISH(EntitySilverfish.class),
	SKELETON(EntitySkeleton.class),
	SLIME(EntitySlime.class),
	STRAY(EntityStray.class),
	VEX(EntityVex.class),
	VINDICATOR(EntityVindicator.class),
	WITCH(EntityWitch.class),
	WITHER_SKELETON(EntityWitherSkeleton.class),
	ZOMBIE(EntityZombie.class),
	
	//MOUND
	MOUNT_DONKEY(EntityMountDonkey.class),
	MOUNT_HORSE(EntityMountHorse.class),
	MOUNT_LLAMA(EntityMountLlama.class),
	MOUNT_MULE(EntityMountMule.class),
	MOUNT_PIG(EntityPig.class),
	MOUNT_SKELETON_HORSE(EntityMountSkeletonHorse.class),
	MOUNT_ZOMBIE_HORSE(EntityMountZombieHorse.class),
	
	//NEUTRAL
	BABY_POLAR_BEAR(EntityBabyPolarBear.class),
	BABY_WOLF(EntityBabyWolf.class),
	BABY_ZOMBIE_PIGMAN(EntityBabyZombiePigman.class),
	CAVE_SPIDER(EntityCaveSpider.class),
	ENDERMAN(EntityEnderman.class),
	IRON_GOLEM(EntityIronGolem.class),
	POLAR_BEAR(EntityPolarBear.class),
	SPIDER(EntitySpider.class),
	WOLF(EntityWolf.class),
	ZOMBIE_PIGMAN(EntityZombiePigman.class),
	
	//PASSIVE
	BABY_CHICKEN(EntityBabyChicken.class),
	BABY_COW(EntityBabyCow.class),
	BABY_HORSE(EntityBabyHorse.class),
	BABY_LLAMA(EntityBabyLlama.class),
	BABY_MOOSHROOM(EntityBabyMooshroom.class),
	BABY_OCELOT(EntityBabyOcelot.class),
	BABY_PIG(EntityBabyPig.class),
	BABY_RABBIT(EntityBabyRabbit.class),
	BABY_SHEEP(EntityBabySheep.class),
	BABY_SKELETON_HORSE(EntityBabySkeletonHorse.class),
	BABY_VILLAGER(EntityBabyVillager.class),
	BABY_ZOMBIE_HORSE(EntityBabyZombieHorse.class),
	BAT(EntityBat.class),
	CHICKEN(EntityChicken.class),
	COW(EntityCow.class),
	GIANT(EntityGiant.class),
	MOOSHROOM(EntityMooshroom.class),
	OCELOT(EntityOcelot.class),
	PIG(EntityPig.class),
	RABBIT(EntityRabbit.class),
	SHEEP(EntitySheep.class),
	SNOW_GOLEM(EntitySnowGolem.class),
	SQUID(EntitySquid.class),
	VILLAGER(EntityVillager.class);
	
	
	Class<? extends RPGEntity> clazz;
	
	RPGEntityType(Class<? extends RPGEntity> clazz) {
		this.clazz = clazz;
	}
	
	public RPGEntity getType(String name, int level, Location location, ProfileData profile) {
		Constructor<? extends RPGEntity> constructor;
		try {
			constructor = clazz.getConstructor(String.class, int.class, Location.class);
			return constructor.newInstance(name, level, location, profile);
		} catch (NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
}