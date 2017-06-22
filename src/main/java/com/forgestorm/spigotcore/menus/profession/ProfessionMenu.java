package com.forgestorm.spigotcore.menus.profession;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.constants.ProfessionType;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import com.forgestorm.spigotcore.menus.MainMenu;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.OpenLearnedProfessionMenu;
import com.forgestorm.spigotcore.menus.actions.ShowTrackingDeviceMessage;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ProfessionMenu extends Menu {

    private final SpigotCore plugin;
    private final Player player;

	public ProfessionMenu(SpigotCore plugin, Player player) {
		super(plugin);
        this.plugin = plugin;
        this.player = player;
        init("Professions Menu", 1);
        makeMenuItems();
	}

	@Override
	protected void makeMenuItems() {
        ItemStack itemStack, backButton, exitButton;
        ItemTypes type = ItemTypes.MENU;
        ItemGenerator itemGen = plugin.getItemGen();
        PlayerProfileData profile = plugin.getProfileManager().getProfile(player);

        // Cooking
        if (profile.isCookingActive()) {
            itemStack = itemGen.generateItem("profession_cooking_learned", type);
            setItem(itemStack, 0, new OpenLearnedProfessionMenu(plugin, ProfessionType.COOKING));
        } else {
            itemStack = itemGen.generateItem("profession_cooking", type);
            setItem(itemStack, 0, new ShowTrackingDeviceMessage());
        }

        // Farming
        if (profile.isFarmingActive()) {
            itemStack = itemGen.generateItem("profession_farming_learned", type);
            setItem(itemStack, 1, new OpenLearnedProfessionMenu(plugin, ProfessionType.FARMING));
        } else {
            itemStack = itemGen.generateItem("profession_farming", type);
            setItem(itemStack, 1, new ShowTrackingDeviceMessage());
        }

        // Fishing
        if (profile.isFishingActive()) {
            itemStack = itemGen.generateItem("profession_fishing_learned", type);
            setItem(itemStack, 2, new OpenLearnedProfessionMenu(plugin, ProfessionType.FISHING));
        } else {
            itemStack = itemGen.generateItem("profession_fishing", type);
            setItem(itemStack, 2, new ShowTrackingDeviceMessage());
        }

        // Mining
        if (profile.isMiningActive()) {
            itemStack = itemGen.generateItem("profession_mining_learned", type);
            setItem(itemStack, 3, new OpenLearnedProfessionMenu(plugin, ProfessionType.MINING));
        } else {
            itemStack = itemGen.generateItem("profession_mining", type);
            setItem(itemStack, 3, new ShowTrackingDeviceMessage());
        }

        // Smelting
        if (profile.isSmeltingActive()) {
            itemStack = itemGen.generateItem("profession_smelting_learned", type);
            setItem(itemStack, 4, new OpenLearnedProfessionMenu(plugin, ProfessionType.SMELTING));
        } else {
            itemStack = itemGen.generateItem("profession_smelting", type);
            setItem(itemStack, 4, new ShowTrackingDeviceMessage());
        }

        // Wood Cutting
        if (profile.isLumberjackActive()) {
            itemStack = itemGen.generateItem("profession_wood_cutting_learned", type);
            setItem(itemStack, 5, new OpenLearnedProfessionMenu(plugin, ProfessionType.WOOD_CUTTING));
        } else {
            itemStack = itemGen.generateItem("profession_wood_cutting", type);
            setItem(itemStack, 5, new ShowTrackingDeviceMessage());
        }

		backButton = itemGen.generateItem("back_button", type);
		exitButton = itemGen.generateItem("exit_button", type);
		setItem(backButton, 7, MainMenu.class);
        setItem(exitButton, 8, new Exit(plugin));
    }
}
