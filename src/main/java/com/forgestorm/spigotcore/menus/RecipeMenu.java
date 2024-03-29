package com.forgestorm.spigotcore.menus;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import com.forgestorm.spigotcore.menus.actions.SelectRecipe;
import com.forgestorm.spigotcore.menus.actions.SendChatText;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class RecipeMenu extends Menu {

    final int MAX_FOR_PAGE = 20;
    private final SpigotCore plugin;
    private final boolean SHOW_ALL;
    private final PlayerProfileData profile;

    public RecipeMenu(SpigotCore plugin, Player player, boolean showAll) {
		super(plugin);
        this.plugin = plugin;
        SHOW_ALL = showAll;
        init("Recipe Selection Menu", 5);
        profile = this.plugin.getProfileManager().getProfile(player);
        makeMenuItems();
    }

	@Override
	protected void makeMenuItems() {
		if (!SHOW_ALL) showCollected();
		if (SHOW_ALL) showAll();
	}
	
	/**
	 * This will build a menu based on what the 
	 * recipes the player has collected.
	 */
	private void showCollected() {
		List<String> recipes = profile.getCollectedRecipes();
		
		ItemTypes type = ItemTypes.RECIPES_CRAFTING;
        ItemGenerator itemGen = plugin.getItemGen();

        int count = 0;
        for (String recipe : recipes) {
            if (count > MAX_FOR_PAGE) break;

            setItem(itemGen.generateItem(recipe, type), count++, new SelectRecipe(plugin, recipe));
        }
    }

    /**
	 * This will show all the recipes on the server,
	 * including the ones owned by the player.
	 * 
	 * TODO: Page Changing.
	 */
	private void showAll() {
        Set<String> items = plugin.getRecipeManager().getRecipes().keySet();

        ItemTypes type = ItemTypes.RECIPES_CRAFTING;
        ItemGenerator itemGen = plugin.getItemGen();

        Iterator<String> it = items.iterator();

        int count = 0;
        while (it.hasNext()) {
			if (count > MAX_FOR_PAGE) break;
			
			//System.out.println("MENU OPENED: " + profile.getCollectedRecipes().toString());
			
			String key = it.next();
			if (profile.getCollectedRecipes().contains(key)) {
                setItem(itemGen.generateItem(key, type), count++, new SelectRecipe(plugin, key));
            } else {
                String error = ChatColor.RED + "You do not own this recipe.";
                setItem(itemGen.generateItem(key, type), count++, new SendChatText(error));
			}
		}
	}
}
