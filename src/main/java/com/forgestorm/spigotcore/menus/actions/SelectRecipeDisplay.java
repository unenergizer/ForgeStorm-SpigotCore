package com.forgestorm.spigotcore.menus.actions;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.menus.RecipeMenu;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class SelectRecipeDisplay implements ClickAction {

    private final SpigotCore plugin;
    private final boolean showAll;

    @Override
    public void click(Player player) {
        new RecipeMenu(plugin, player, showAll).open(player);
    }
}
