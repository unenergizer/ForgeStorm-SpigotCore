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
import com.forgestorm.spigotcore.profession.Profession;
import com.forgestorm.spigotcore.util.item.ItemBuilder;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import com.forgestorm.spigotcore.util.text.ProgressBarString;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CookingTrainerMenu extends Menu {

    private final SpigotCore plugin;
    private final Player player;
    private final boolean openAtNPC;

    public CookingTrainerMenu(SpigotCore plugin, Player player, boolean openAtNPC) {
        super(plugin);
        this.plugin = plugin;
        this.player = player;
        this.openAtNPC = openAtNPC;
        init("Cooking Trainer", 1);
        makeMenuItems();
    }

    @Override
    protected void makeMenuItems() {
        ItemStack exitButton;
        ItemTypes type = ItemTypes.MENU;
        ItemGenerator itemGen = plugin.getItemGen();

        PlayerProfileData profile = plugin.getProfileManager().getProfile(player);
        boolean hasProfession = profile.isCookingActive();

        if (hasProfession) {
            //Player info
            Experience ec = new ProfessionExperience();
            int expOffSet = ec.getExpOffSet();
            long currentExperience = profile.getCookingExperience();
            int currentLevel = ec.getLevel(currentExperience);
            int currentPercentage = (int) ec.getPercentToLevel(currentExperience);
            int friendlyExperience = (int) currentExperience - expOffSet;
            int friendlyExperienceGoal = ec.getExperience(currentLevel + 1) - expOffSet;

            //Show current profession info.
            ItemStack toolStats, blockInfo, toolInfo, upgradeTool;
            toolStats = itemGen.generateItem("profession_cooking_stats", type);
            blockInfo = itemGen.generateItem("profession_cooking_tier_info", type);
            toolInfo = itemGen.generateItem("profession_cooking_tool_info", type);
            upgradeTool = itemGen.generateItem("profession_cooking_upgrades", type);

            List<String> toolStatsLore, blockLore, toolLore, upgradeLore;
            toolStatsLore = new ArrayList<>();
            blockLore = new ArrayList<>();
            toolLore = new ArrayList<>();
            upgradeLore = new ArrayList<>();

            blockLore.add("&71. Wheat");
            toolLore.add("&71. 1x Furnaces");

            if (currentLevel >= 20) {
                blockLore.add("&72. Carrots");
                toolLore.add("&72. 2x Furnaces");
            }
            if (currentLevel >= 40) {
                blockLore.add("&73. Potatoes");
                toolLore.add("&73. 3x Furnaces");
            }
            if (currentLevel >= 60) {
                blockLore.add("&74. Beets");
                toolLore.add("&74. 4x Furnaces");
            }
            if (currentLevel >= 80) {
                blockLore.add("&75. Melons");
                toolLore.add("&75. 5x Furnaces");
            }

            toolStatsLore.add(Profession.getProfessionRank(currentLevel));
            toolStatsLore.add("&7LVL: &c" + currentLevel);
            toolStatsLore.add("&7EXP: " + ProgressBarString.buildBar(currentPercentage) + " &7" + currentPercentage + "%");
            toolStatsLore.add("&7EXP: &a" + friendlyExperience + "&7 / &a" + friendlyExperienceGoal);

            // Show them default info about upgrading their items.
            upgradeLore.add("&7You can upgrade how many");
            upgradeLore.add("&7furnaces you can use and");
            upgradeLore.add("&7what food you can cook");
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
            ItemStack buy = itemGen.generateItem("profession_buy_cooking", type);
            setItem(buy, 0, new PurchaseProfession(plugin, ProfessionType.COOKING, 250));
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