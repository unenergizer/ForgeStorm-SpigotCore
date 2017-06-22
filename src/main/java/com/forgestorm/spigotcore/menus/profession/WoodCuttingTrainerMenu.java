package com.forgestorm.spigotcore.menus.profession;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.constants.ProfessionType;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import com.forgestorm.spigotcore.experience.Experience;
import com.forgestorm.spigotcore.experience.ProfessionExperience;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.OpenProfessionsMenu;
import com.forgestorm.spigotcore.menus.actions.PurchaseProfession;
import com.forgestorm.spigotcore.util.item.ItemBuilder;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import com.forgestorm.spigotcore.util.text.ProgressBarString;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class WoodCuttingTrainerMenu extends Menu {

    private final SpigotCore plugin;
    private final Player player;
    private final boolean openAtNPC;

    public WoodCuttingTrainerMenu(SpigotCore plugin, Player player, boolean openAtNPC) {
        super(plugin);
        this.plugin = plugin;
        this.player = player;
        this.openAtNPC = openAtNPC;
        init("Wood Cutting Trainer", 1);
        makeMenuItems();
    }

	@Override
	protected void makeMenuItems() {
        ItemStack exitButton;
        ItemTypes type = ItemTypes.MENU;
        ItemGenerator itemGen = plugin.getItemGen();

        PlayerProfileData profile = plugin.getProfileManager().getProfile(player);
        boolean hasProfession = profile.isLumberjackActive();

        if (hasProfession) {
            //Player info
            Experience ec = new ProfessionExperience();
            int expOffSet = ec.getExpOffSet();
            long currentExperience = profile.getLumberjackExperience();
            int currentLevel = ec.getLevel(currentExperience);
            int currentPercentage = (int) ec.getPercentToLevel(currentExperience);
            int friendlyExperience = (int) currentExperience - expOffSet;
            int friendlyExperienceGoal = ec.getExperience(currentLevel + 1) - expOffSet;

            //Show current profession info.
            ItemStack toolStats, blockInfo, toolInfo, upgradeTool;
            toolStats = itemGen.generateItem("profession_wood_cutting_stats", type);
            blockInfo = itemGen.generateItem("profession_wood_cutting_tier_info", type);
            toolInfo = itemGen.generateItem("profession_wood_cutting_tool_info", type);
            upgradeTool = itemGen.generateItem("profession_wood_cutting_upgrades", type);

            List<String> toolStatsLore, blockLore, toolLore, upgradeLore;
            toolStatsLore = new ArrayList<>();
            blockLore = new ArrayList<>();
            toolLore = new ArrayList<>();
            upgradeLore = new ArrayList<>();

            blockLore.add("&71. Oak Logs");
            toolLore.add("&71. Wood Axe");

            String rank = "";

            if (currentLevel < 20) {
                rank = "&7Rank: &fNovice";
            } else if (currentLevel >= 20) {
                rank = "&7Rank: &aIntermediate";
                blockLore.add("&72. Spruce Logs");
                toolLore.add("&72. Stone Axe");
            } else if (currentLevel >= 40) {
                rank = "&7Rank: &9Proficient";
                blockLore.add("&73. Birch Logs");
                toolLore.add("&73. Iron Axe");
            } else if (currentLevel >= 60) {
                rank = "&7Rank: &5Expert";
                blockLore.add("&74. Jungle Logs");
                toolLore.add("&74. Diamond Axe");
            } else if (currentLevel >= 80) {
                rank = "&7Rank: &6Grand Master";
                blockLore.add("&75. Acacia Logs");
                toolLore.add("&75. Gold Axe");
            }

            toolStatsLore.add(rank);
            toolStatsLore.add("&7LVL: &c" + currentLevel);
            toolStatsLore.add("&7EXP: " + ProgressBarString.buildBar(currentPercentage) + " &7" + currentPercentage + "%");
            toolStatsLore.add("&7EXP: &a" + friendlyExperience + "&7 / &a" + friendlyExperienceGoal);

            // Show them default info about upgrading their items.
            upgradeLore.add("&7You can upgrade your tool");
            upgradeLore.add("&7and what logs you can chop");
            upgradeLore.add("&7every &a20 &7levels.");
            upgradeLore.add("");
            upgradeLore.add("&7Example:");
            upgradeLore.add("&b20&7, &b40&7, &b60&7, and &b80");

            // Set items in the inventory menu.
            setItem(new ItemBuilder(toolStats).addLores(toolStatsLore).build(true), 0);
            setItem(new ItemBuilder(blockInfo).addLores(blockLore).build(true), 1);
            setItem(new ItemBuilder(toolInfo).addLores(toolLore).build(true), 2);
            setItem(new ItemBuilder(upgradeTool).addLores(upgradeLore).build(true), 3);
        } else {
            //Buy It
            ItemStack buy = itemGen.generateItem("profession_buy_wood_cutting", type);
            setItem(buy, 0, new PurchaseProfession(plugin, ProfessionType.WOOD_CUTTING, 250));
        }

        //If they did not use the NPC to open this menu, then give them the
        //back button.
        if (!openAtNPC) {
            ItemStack backButton = itemGen.generateItem("back_button", type);
            setItem(backButton, 7, new OpenProfessionsMenu(plugin));
        }

        //Set exit button
        exitButton = itemGen.generateItem("exit_button", type);
        setItem(exitButton, 8, new Exit(plugin));
    }
}