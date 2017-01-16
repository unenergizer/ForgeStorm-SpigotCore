package com.forgestorm.spigotcore.menus.profession;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.constants.ProfessionType;
import com.forgestorm.spigotcore.experience.Experience;
import com.forgestorm.spigotcore.experience.ProfessionExperience;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.OpenProfessionsMenu;
import com.forgestorm.spigotcore.menus.actions.PurchaseProfession;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;
import com.forgestorm.spigotcore.util.item.ItemBuilder;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import com.forgestorm.spigotcore.util.text.ProgressBarString;

public class MiningTrainerMenu extends Menu {

	private final SpigotCore PLUGIN;
	private Player player;
	private boolean openAtNPC;

	public MiningTrainerMenu(SpigotCore plugin, Player player, boolean openAtNPC) {
		super(plugin);
		PLUGIN = plugin;
		this.player = player;
		this.openAtNPC = openAtNPC;
		init("Mining Trainer", 1);
		makeMenuItems();
	}

	@Override
	protected void makeMenuItems() {
		ItemStack exitButton;
		ItemTypes type = ItemTypes.MENU;
		ItemGenerator itemGen = PLUGIN.getItemGen();

		PlayerProfileData profile = PLUGIN.getProfileManager().getProfile(player);
		boolean hasProfession = profile.hasProfession(ProfessionType.MINING);

		if (hasProfession) {
			//Player info
			Experience ec = new ProfessionExperience();
			int pickEXP = (int) profile.getMiningExperience();
			int pickLVL = ec.getLevel(pickEXP);
			int expToNextLevel = ec.getExperience(pickLVL + 1);
			int expPercent = ((100 * pickEXP) / expToNextLevel);

			//Show current profession info.
			ItemStack toolInfo, blockInfo, upgradeTool;
			toolInfo = itemGen.generateItem("profession_toolinfo", type);
			blockInfo = itemGen.generateItem("profession_blockinfo", type);
			upgradeTool = itemGen.generateItem("profession_upgradetool", type);

			List<String> toolLore, blockLore, upgradeLore;
			toolLore = new ArrayList<>();
			blockLore = new ArrayList<>();
			upgradeLore = new ArrayList<>();

			toolLore.add("&7Rank: &eNovice");
			toolLore.add("&7LVL: &c" + pickLVL);
			toolLore.add("&7EXP: " + ProgressBarString.buildBar(expPercent) + " &7" + expPercent + "%");
			toolLore.add("&7EXP: &a" + pickEXP);

			blockLore.add("&eBreakable Blocks:");
			blockLore.add("&71. Coal Ore");

			if (pickLVL > 20) {
				blockLore.add("&72. Iron Ore");
			} else if (pickLVL > 40) {
				blockLore.add("&73. Emerald Ore");
			} else if (pickLVL > 60) {
				blockLore.add("&74. Diamond Ore");
			} else if (pickLVL > 80) {
				blockLore.add("&75. Gold Ore");
			}

			if (pickLVL == 20 || pickLVL == 40 || pickLVL == 60 || pickLVL == 80) {
				if (openAtNPC) {
					//The menu was opened by clicking on an NPC.
					//we can allow the player to upgrade their tool.
					upgradeLore.add("&aCongradulations!!!!");
					upgradeLore.add("&7Your tool is ready to be");
					upgradeLore.add("&7upgraded!");
					upgradeLore.add("");
					upgradeLore.add("&eClick to upgrade your tool!");
					upgradeLore.add("&7Cost: &c$500");

				} else {
					//The menu was opened using the "Main Menu." Because of this,
					//we can not let the user upgrade their tool.  Tools can only
					//be upgraded at the profession trainer.
					upgradeLore.add("&eCongradulations!!!!");
					upgradeLore.add("&7Your tool is ready to be");
					upgradeLore.add("&7upgraded! Visit the mining");
					upgradeLore.add("&7trainer for more information.");
					upgradeLore.add("");
					upgradeLore.add("&cYou can not upgrade here. You");
					upgradeLore.add("&cmust visit the mining trainer!");
				}
			} else {
				//Show them default info about upgrading their items.
				upgradeLore.add("&7Your tool can upgrade every 20");
				upgradeLore.add("&7levels.  When your tool reaches");
				upgradeLore.add("&720, 40, 60, or 80, visit the");
				upgradeLore.add("&7mining trainer for a paid");
				upgradeLore.add("&7item upgrade. The cost for the");
				upgradeLore.add("&7upgrade will vary depending");
				upgradeLore.add("&7on the current level");
			}

			setItem(new ItemBuilder(toolInfo).addLores(toolLore).build(true), 0);
			setItem(new ItemBuilder(blockInfo).addLores(blockLore).build(true), 1);
			setItem(new ItemBuilder(upgradeTool).addLores(upgradeLore).build(true), 2);
		} else {
			//Buy It
			ItemStack buy = itemGen.generateItem("profession_buy_mining", type);
			setItem(buy, 0, new PurchaseProfession(PLUGIN, ProfessionType.MINING, 250));
		}

		//If they did not use the NPC to open this menu, then give them the
		//back button.
		if (!openAtNPC) {
			ItemStack backButton = itemGen.generateItem("back_button", type);
			setItem(backButton, 7, new OpenProfessionsMenu(PLUGIN));
		}

		//Set exit button
		exitButton = itemGen.generateItem("exit_button", type);
		setItem(exitButton, 8, new Exit(PLUGIN));
	}
}
