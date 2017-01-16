package com.forgestorm.spigotcore.util.item;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.constants.ItemTypes;

public class ItemGenerator {

	private FileConfiguration config;

	public ItemStack generateItem(String itemConfigName, ItemTypes type) {
		return generateItem(itemConfigName, type, 1);
	}
	
	public ItemStack generateItem(String itemConfigName, ItemTypes type, int amount) {
		
		//System.out.println("-------------- "+ itemConfigName + ":" + type.toString() + " --------------");
		File file = new File(type.getFilePaths().toString());
		config = YamlConfiguration.loadConfiguration(file);
		
		final String path = "." + itemConfigName; 
		
		String name = config.getString(path + ".name");
		String material = config.getString(path + ".material");
		boolean enchanted = config.getBoolean(path + ".enchant");
		int data = config.getInt(path + ".data");
		List<String> desc = new ArrayList<>();
		desc = config.getStringList(path + ".desc");
		Material mat = Material.valueOf(material);
		
		//System.out.println("NAME:" + path + "." + name);
		//System.out.println("MATE:" + path + "." + material);
		//System.out.println("ENCH:" + path + "." + enchanted);
		//System.out.println("DATA:" + path + "." + data);
		//System.out.println("DESC:" + path + "." + desc);

		return new ItemBuilder(mat, (short) data)
				.setRawTitle(name)
				.addFakeEnchantment(enchanted)
				.addLores(desc)
				.setAmount(amount)
				.build(true);
	}
}
