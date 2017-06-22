package com.forgestorm.spigotcore.crafting;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.FilePaths;
import com.forgestorm.spigotcore.constants.ItemTypes;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class CraftingConfig {

	private final SpigotCore plugin;
	
	private final FileConfiguration config;
	
	public CraftingConfig(SpigotCore plugin) {
		this.plugin = plugin;
		config = YamlConfiguration.loadConfiguration(
				new File(FilePaths.ITEMS_CRAFTING_RECIPES.toString()));
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

		return plugin.getItemGen().generateItem(make, type);
	}
	
	/**
	 * Retrieves a list of items needed to craft a
	 * item.
	 * 
	 * @param recipeName the name of the recipe
	 * @return A list of ItemStacks.
	 */
	public List<ItemStack> getRecipeItems(String recipeName) {
		ConfigurationSection section = config.getConfigurationSection(recipeName + ".ingredients");
		
		List<ItemStack> items = new ArrayList<>();

		for (String s : section.getKeys(false)) {
			int i = Integer.parseInt(s);

			String name = section.getString(i + ".name");
			int amount = section.getInt(i + ".amount");

			items.add(plugin.getItemGen().generateItem(name, ItemTypes.INGREDIENTS, amount));
		}
		
		return items;
	}
}
