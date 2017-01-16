package com.forgestorm.spigotcore.profile.monster;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.forgestorm.spigotcore.constants.FilePaths;

public class MonsterSaver {

	private final String FILE_PATH = FilePaths.ENTITY_TYPE.toString();

	public MonsterSaver() {
		if(!(new File(FILE_PATH)).exists()){
			createConfig();
		}
	}

	private void createConfig() {
		File file = new File(FILE_PATH);
		FileConfiguration fileConfig =  YamlConfiguration.loadConfiguration(file);

		try {
			fileConfig.save(file);	//Save the file.
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	public boolean entitySaver(String entityType, String name, int level, int health, String skullOwner, String lootTable){
		File file = new File(FILE_PATH);
		FileConfiguration config =  YamlConfiguration.loadConfiguration(file);
		
		config.set(name + ".name", name);      
		config.set(name + ".type", entityType.toString());
		config.set(name + ".tier", 0);
		config.set(name + ".level", level);
		config.set(name + ".health", health);
		config.set(name + ".magic.fire", 100);
		config.set(name + ".magic.ice", 0);
		config.set(name + ".magic.posion", 0);
		config.set(name + ".has.armor.playerskull", false);
		config.set(name + ".has.armor.playerskullowner", skullOwner);
		config.set(name + ".has.armor.helmet", false);
		config.set(name + ".has.armor.chestplate", false);
		config.set(name + ".has.armor.leggings", false);
		config.set(name + ".has.armor.boots", false);
		config.set(name + ".has.armor.shield", false);
		config.set(name + ".has.weapon.axe", false);
		config.set(name + ".has.weapon.bow", false);
		config.set(name + ".has.weapon.spear", false);
		config.set(name + ".has.weapon.sword", false);
		config.set(name + ".has.weapon.wand", false);
		config.set(name + ".lootTable", lootTable);

		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} 
		return true;
	}
}
