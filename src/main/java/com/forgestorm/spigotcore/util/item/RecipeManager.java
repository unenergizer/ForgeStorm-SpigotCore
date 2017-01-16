package com.forgestorm.spigotcore.util.item;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.FilePaths;
import com.forgestorm.spigotcore.constants.ItemTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class RecipeManager {

	private final SpigotCore PLUGIN;
	private FileConfiguration config;

	private Map<String, List<Data>> recipes;

	public RecipeManager(SpigotCore plugin) {
		PLUGIN = plugin;
		config = YamlConfiguration.loadConfiguration(
				new File(FilePaths.ITEM_CRAFTING_RECIPES.toString()));

		recipes = new HashMap<>();
		loadRecipes();
	}

	/**
	 * Loads all the recipes within the recipe config and
	 * stores them for later use
	 */
	private void loadRecipes() {
		ConfigurationSection outSec = config.getConfigurationSection("");
		Iterator<String> outIt = outSec.getKeys(false).iterator();
		while (outIt.hasNext()) {
			String recipe = outIt.next();

			ConfigurationSection inSec = config.getConfigurationSection(recipe + ".ingredents");
			Iterator<String> inIt = inSec.getKeys(false).iterator();

			List<Data> items = new ArrayList<>();
			while (inIt.hasNext()) {
				int i = Integer.parseInt(inIt.next());

				String confiName = inSec.getString(i  + ".name");
				int amount = inSec.getInt(i + ".amount");

				items.add(new Data(confiName, amount));
			}

			recipes.put(recipe, items);
		}
	}

	/**
	 * Gets the list of itemstacks associated with a recipe
	 * 
	 * @param recipe The recipe being used
	 * @return the list of itemstacks
	 */
	public List<ItemStack> getRecipeIngredients(String recipe) {

		if (recipes.containsKey(recipe)) {

			ItemGenerator itemGen = new ItemGenerator();

			List<ItemStack> items = new ArrayList<>();
			for (Data item : recipes.get(recipe)) {

				items.add(
						itemGen.generateItem(
								item.getConfigName(),
								ItemTypes.INGREDIENTS,
								item.getAmount()));
			}
			return items;
		}
		return null;
	}
	
	/**
	 * Gets the item to be made from the
	 * crafting recipe
	 * 
	 * @param recipe The recipe to get the item
	 * associated with it
	 * @return The item being made
	 */
	public String getMakes(String recipe) {
		return config.getString(recipe + ".makes");
	}
	
	/**
	 * Gives a player a recipe
	 * 
	 * Administrator only.
	 * 
	 * @param player The player being given a recipe to
	 * @param recipe The recipe being given
	 * @return A chat message indicating if the recipe was received.
	 */
	public String givePlayerRecipe(Player player, String recipe) {
		//We can give the player the recipe if it exists.
		if (recipes.containsKey(recipe)) {

			//Give it to them, if they don't already have it.
			if (!PLUGIN.getProfileManager().getProfile(player).getCollectedRecipes().contains(recipe)) {
				PLUGIN.getProfileManager().getProfile(player).getCollectedRecipes().add(recipe);
				return ChatColor.GREEN + "Recipie was given to the player.";
			} else {
				return ChatColor.RED + "Player already owns that recipie.";
			}
		} else {
			return ChatColor.RED + "That recipe does not exist! Check your spelling.";
		}
	}
	
	/**
	 * Removes a player's recipe
	 * 
	 * Administrator only.
	 * 
	 * @param player The player who's recipe is being removed
	 * @param recipe The recipe being removed
	 * @return A chat message indicating if the recipe was successfully removed
	 */
	public String removePlayerRecipe(Player player, String recipe) {
		if (PLUGIN.getProfileManager().getProfile(player).getCollectedRecipes().contains(recipe)) {
			PLUGIN.getProfileManager().getProfile(player).getCollectedRecipes().remove(recipe);
			
			System.out.println("REMOVE CALL: " + PLUGIN.getProfileManager().getProfile(player).getCollectedRecipes().toString());
			
			return ChatColor.GREEN + "Recipe was removed from player.";
		} else {
			return ChatColor.RED + "The player does not have that recipe.";
		}
	}
}

@AllArgsConstructor
@Getter
class Data {

	private String configName;
	private int amount;
}