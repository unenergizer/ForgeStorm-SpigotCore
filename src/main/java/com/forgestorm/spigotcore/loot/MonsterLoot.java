package com.forgestorm.spigotcore.loot;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.constants.FilePaths;

public class MonsterLoot extends Loot {
	
	public MonsterLoot() {
		filePath = FilePaths.LOOT_TABLE_MOBS.toString();
	}

	@Override
	public List<ItemStack> generateItems() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void spawnItems() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveConfig() {
		
		file = new File(filePath);
		config =  YamlConfiguration.loadConfiguration(file);

		//set a default loot table
		config.set("junk01", "junk01");
		config.set("junk01.armorItem", "testArmorDrop");
		config.set("junk01.weaponItem", "testWeaponDrop");
		config.set("junk01.consumableItem", "testConsumableDrop");
		config.set("junk01.random.armor.enabled", true);
		config.set("junk01.random.armor.tier", "T1");
		config.set("junk01.random.armor.quality", "JUNK");
		config.set("junk01.random.weapon.enabled", false);
		config.set("junk01.random.weapon.tier", "T1");
		config.set("junk01.random.weapon.quality", "JUNK");
		config.set("junk01.currency", "copper");
		config.set("junk01.currencyDropMin", 0);
		config.set("junk01.currencyDropMax", 2);

		try {
			config.save(file);	//Save the file.
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
