package com.forgestorm.spigotcore.menus.actions;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.menus.CraftingMenu;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class SelectRecipe implements ClickAction {

    private final SpigotCore plugin;
    private final String recipe;

    @Override
    public void click(Player player) {
        new CraftingMenu(plugin, recipe).open(player);
    }

}
