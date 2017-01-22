package com.forgestorm.spigotcore.profile.monster;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.FilePaths;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MonsterLoader {
	
	private final SpigotCore plugin;
	private final String filePath = FilePaths.ENTITY_TYPE.toString();
	
	public MonsterLoader(SpigotCore plugin) {
		this.plugin = plugin;
	}
	
	public void getConfig(String name, Location location) {
		MonsterBuilder builder = new MonsterBuilder(plugin);
		
		//Load the configuration file.
		File file = new File(filePath);
		FileConfiguration config =  YamlConfiguration.loadConfiguration(file);
     
		builder.setName(config.getString(name + ".name")); 
        builder.setType(config.getString(name + ".type"));
        builder.setTier(config.getString(name + ".tier"));
        builder.setLevel(config.getInt(name + ".level"));
        builder.setHealth(config.getInt(name + ".health"));
        builder.setFire(config.getInt(name + ".magic.fire"));
        builder.setIce(config.getInt(name + ".magic.ice"));
        builder.setPoison(config.getInt(name + ".magic.poison"));
        builder.setPlayerSkull(config.getBoolean(name + ".has.armor.playerskull"));
        builder.setSkullOwner(config.getString(name + ".has.armor.playerskullowner"));
        builder.setHelmet(config.getBoolean(name + ".has.armor.helmet"));
        builder.setChestplate(config.getBoolean(name + ".has.armor.chestplate"));
        builder.setLeggings(config.getBoolean(name + ".has.armor.leggings"));
        builder.setBoots(config.getBoolean(name + ".has.armor.boots"));
        builder.setShield(config.getBoolean(name + ".has.armor.shield"));
        builder.setAxe(config.getBoolean(name + ".has.weapon.axe"));
        builder.setBow(config.getBoolean(name + ".has.weapon.bow"));
        builder.setSpear(config.getBoolean(name + ".has.weapon.spear"));
        builder.setSword(config.getBoolean(name + ".has.weapon.sword"));
        builder.setWand(config.getBoolean(name + ".has.weapon.wand"));
        builder.setLootTable(config.getString(name + ".lootTable"));
		
        builder.build(location);
	}
}
