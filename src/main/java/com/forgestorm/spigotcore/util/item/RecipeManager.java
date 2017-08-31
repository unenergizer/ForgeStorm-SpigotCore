package com.forgestorm.spigotcore.util.item;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.FilePaths;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.util.logger.ColorLogger;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Getter
public class RecipeManager {

    private final SpigotCore plugin;
    private final FileConfiguration config;
    private final Map<String, List<Data>> recipes;

    public RecipeManager(SpigotCore plugin) {
        this.plugin = plugin;
        config = YamlConfiguration.loadConfiguration(
                new File(FilePaths.ITEMS_CRAFTING_RECIPES.toString()));

        recipes = new HashMap<>();
        loadRecipes();
    }

    /**
     * Loads all the recipes within the recipe config and
     * stores them for later use
     */
    private void loadRecipes() {
        ConfigurationSection outSec = config.getConfigurationSection("");
        for (String recipe : outSec.getKeys(false)) {
            ConfigurationSection inSec = config.getConfigurationSection(recipe + ".ingredients");
            Iterator<String> inIt = inSec.getKeys(false).iterator();

            List<Data> items = new ArrayList<>();
            while (inIt.hasNext()) {
                int i = Integer.parseInt(inIt.next());

                String configName = inSec.getString(i + ".name");
                int amount = inSec.getInt(i + ".amount");

                items.add(new Data(configName, amount));
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
     *               associated with it
     * @return The item being made
     */
    public String getMakes(String recipe) {
        return config.getString(recipe + ".makes");
    }

    /**
     * Gives a player a recipe
     * <p>
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
            if (!plugin.getProfileManager().getProfile(player).getCollectedRecipes().contains(recipe)) {
                plugin.getProfileManager().getProfile(player).getCollectedRecipes().add(recipe);
                return ChatColor.GREEN + "Recipe was given to the player.";
            } else {
                return ChatColor.RED + "Player already owns that recipe.";
            }
        } else {
            return ChatColor.RED + "That recipe does not exist! Check your spelling.";
        }
    }

    /**
     * Removes a player's recipe
     * <p>
     * Administrator only.
     *
     * @param player The player who's recipe is being removed
     * @param recipe The recipe being removed
     * @return A chat message indicating if the recipe was successfully removed
     */
    public String removePlayerRecipe(Player player, String recipe) {
        if (plugin.getProfileManager().getProfile(player).getCollectedRecipes().contains(recipe)) {
            plugin.getProfileManager().getProfile(player).getCollectedRecipes().remove(recipe);

            ColorLogger.AQUA.printLog("REMOVE CALL: " + plugin.getProfileManager().getProfile(player).getCollectedRecipes().toString());

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