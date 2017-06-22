package com.forgestorm.spigotcore.menus.actions;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ProfessionType;
import com.forgestorm.spigotcore.menus.profession.CookingTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.FarmingTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.FishingTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.MiningTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.SmeltingTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.WoodCuttingTrainerMenu;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class OpenLearnedProfessionMenu implements ClickAction {

    private final SpigotCore plugin;
    private final ProfessionType professionType;

	@Override
	public void click(Player player) {
        switch (professionType) {

            case COOKING:
                new CookingTrainerMenu(plugin, player, false).open(player);
                break;
            case FARMING:
                new FarmingTrainerMenu(plugin, player, false).open(player);
                break;
            case FISHING:
                new FishingTrainerMenu(plugin, player, false).open(player);
                break;
            case MINING:
                new MiningTrainerMenu(plugin, player, false).open(player);
                break;
            case SMELTING:
                new SmeltingTrainerMenu(plugin, player, false).open(player);
                break;
            case WOOD_CUTTING:
                new WoodCuttingTrainerMenu(plugin, player, false).open(player);
                break;
        }
    }
}
