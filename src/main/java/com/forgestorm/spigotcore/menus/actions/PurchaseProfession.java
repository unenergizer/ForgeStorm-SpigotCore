package com.forgestorm.spigotcore.menus.actions;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.CommonSounds;
import com.forgestorm.spigotcore.constants.ProfessionType;
import com.forgestorm.spigotcore.constants.SpigotCoreMessages;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import com.forgestorm.spigotcore.menus.profession.CookingTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.FarmingTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.FishingTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.MiningTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.SmeltingTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.WoodCuttingTrainerMenu;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class PurchaseProfession implements ClickAction {

    private final SpigotCore plugin;
    private final ProfessionType profession;
    private final int cost;

    @Override
    public void click(Player player) {
        //Buy Item (remove money from account)
        PlayerProfileData profile = plugin.getProfileManager().getProfile(player);
        boolean canPurchase = profile.removeCurrency(cost);


        if (canPurchase) {
            // Show purchase success message.
            player.sendMessage(SpigotCoreMessages.ECONOMY_PURCHASE_SUCCESS.toString());

            // Play success sound!
            CommonSounds.ACTION_SUCCESS.playSound(player);

            switch (profession) {
                case COOKING:
                    profile.setCookingActive(true);
                    new CookingTrainerMenu(plugin, player, true).open(player);
                    break;
                case FARMING:
                    profile.setFarmingActive(true);
                    new FarmingTrainerMenu(plugin, player, true).open(player);
                    break;
                case FISHING:
                    profile.setFishingActive(true);
                    new FishingTrainerMenu(plugin, player, true).open(player);
                    break;
                case WOOD_CUTTING:
                    profile.setLumberjackActive(true);
                    new WoodCuttingTrainerMenu(plugin, player, true).open(player);
                    break;
                case MINING:
                    profile.setMiningActive(true);
                    new MiningTrainerMenu(plugin, player, true).open(player);
                    break;
                case SMELTING:
                    profile.setSmeltingActive(true);
                    new SmeltingTrainerMenu(plugin, player, true).open(player);
                    break;
            }
        } else {
            // Show purchase failure message.
            player.sendMessage(SpigotCoreMessages.ECONOMY_PURCHASE_FAILED.toString());

            // Play failure sound.
            CommonSounds.ACTION_FAILED.playSound(player);
        }
    }
}
