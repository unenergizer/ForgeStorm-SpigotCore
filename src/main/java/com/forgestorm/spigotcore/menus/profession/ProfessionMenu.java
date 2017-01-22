package com.forgestorm.spigotcore.menus.profession;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.constants.ProfessionType;
import com.forgestorm.spigotcore.menus.MainMenu;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.FeatureComingSoon;
import com.forgestorm.spigotcore.menus.actions.OpenMiningMenu;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;
import com.forgestorm.spigotcore.util.item.ItemGenerator;

public class ProfessionMenu extends Menu {

	private final SpigotCore PLUGIN;
	private final Player player;

	public ProfessionMenu(SpigotCore plugin, Player player) {
		super(plugin);
		PLUGIN = plugin;
		this.player = player;
		init("Professions Menu", 1);
		makeMenuItems();
	}

	@Override
	protected void makeMenuItems() {
		ItemStack farming, fishing, lumberjack, mining, backButton, exitButton;
		ItemTypes type = ItemTypes.MENU;
		ItemGenerator itemGen = PLUGIN.getItemGen();
		PlayerProfileData profile = PLUGIN.getProfileManager().getProfile(player);

		if (profile.hasProfession(ProfessionType.FARMING)) {
			farming = itemGen.generateItem("profession_toolinfo", type);
			setItem(farming, 0, new FeatureComingSoon());
		} else {
			farming = itemGen.generateItem("profession_farming", type);
			setItem(farming, 0, new FeatureComingSoon());
		}

		if (profile.hasProfession(ProfessionType.FISHING)) {
			fishing = itemGen.generateItem("profession_toolinfo", type);
			setItem(fishing, 1, new FeatureComingSoon());	
		} else {
			fishing = itemGen.generateItem("profession_fishing", type);
			setItem(fishing, 1, new FeatureComingSoon());	
		}

		if (profile.hasProfession(ProfessionType.LUMBERJACK)) {
			lumberjack = itemGen.generateItem("profession_toolinfo", type);
			setItem(lumberjack, 2, new FeatureComingSoon());		
		} else {
			lumberjack = itemGen.generateItem("profession_lumberjack", type);
			setItem(lumberjack, 2, new FeatureComingSoon());
		}
	
		if (profile.hasProfession(ProfessionType.MINING)) {
			mining = itemGen.generateItem("profession_toolinfo", type);
			setItem(mining, 3, new OpenMiningMenu(PLUGIN));	
		} else {
			mining = itemGen.generateItem("profession_mining", type);
			setItem(mining, 3, new FeatureComingSoon());
		}

		backButton = itemGen.generateItem("back_button", type);
		exitButton = itemGen.generateItem("exit_button", type);
		setItem(backButton, 7, MainMenu.class);
		setItem(exitButton, 8, new Exit(PLUGIN));
	}
}
