package com.forgestorm.spigotcore.menus.actions;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.CraftingMenu;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class TryCraftingRecipe implements ClickAction {

    private final SpigotCore plugin;
    private final String recipe;

    @Override
    public void click(Player player) {
        CraftingMenu menu = (CraftingMenu) plugin.getProfileManager().getProfile(player).getCurrentMenu();

        if (menu.testForMatch()) {
            //give item

            ItemGenerator itemGen = plugin.getItemGen();
            ItemStack item = itemGen.generateItem(recipe, ItemTypes.RECIPES_FINISHED);
            player.getInventory().addItem(item);
            player.closeInventory();
            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Congratulations! You have crafted the recipe!");
		} else {
			player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Cannot forge recipe! Check your items!");
		}
	}

}
