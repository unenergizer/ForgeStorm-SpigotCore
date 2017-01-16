package com.forgestorm.spigotcore.crafting;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.FilePaths;
import com.forgestorm.spigotcore.constants.ItemTypes;

public class CraftingConfig {

	private final SpigotCore PLUGIN;
	
	private FileConfiguration config;
	
	public CraftingConfig(SpigotCore plugin) {
		PLUGIN = plugin;
		config = YamlConfiguration.loadConfiguration(
				new File(FilePaths.ITEM_CRAFTING_RECIPES.toString()));
	}
	
	/**
	 * Returns the FINAL crafted item.
	 * 
	 * @param recipeName The recipe to craft.
	 * @return A successful crafted item.
	 */
	public ItemStack makeRecipe(String recipeName) {
		
		String make = config.getString(recipeName + ".make");
		ItemTypes type = ItemTypes.valueOf(config.getString(recipeName + ".type")); 
		
		return PLUGIN.getItemGen().generateItem(make, type);
	}
	
	/**
	 * Retrieves a list of items needed to craft a
	 * item.
	 * 
	 * @param recipeName the name of the recipe
	 * @return A list of ItemStacks.
	 */
	public List<ItemStack> getRecipieItems(String recipeName) {
		ConfigurationSection section = config.getConfigurationSection(recipeName + ".ingredents");
		
		List<ItemStack> items = new ArrayList<>();
		Iterator<String> it = section.getKeys(false).iterator();
		
		while (it.hasNext()) {
			
			int i = Integer.parseInt(it.next());
			
			String name = section.getString(i + ".name");
			int amount = section.getInt(i + ".amount");
			
			items.add(PLUGIN.getItemGen().generateItem(name, ItemTypes.INGREDIENTS, amount));
		}
		
		return items;
	}
}
