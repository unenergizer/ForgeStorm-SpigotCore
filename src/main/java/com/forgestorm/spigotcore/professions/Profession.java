package com.forgestorm.spigotcore.professions;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.CommonSounds;
import com.forgestorm.spigotcore.constants.ProfessionType;
import com.forgestorm.spigotcore.constants.SpigotCoreMessages;
import com.forgestorm.spigotcore.experience.Experience;
import com.forgestorm.spigotcore.experience.ProfessionExperience;
import com.forgestorm.spigotcore.util.math.RandomChance;
import com.forgestorm.spigotcore.util.text.CenterChatText;
import com.forgestorm.spigotcore.util.text.ProgressBarString;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

/*********************************************************************************
 *
 * OWNER: Robert Andrew Brown & Joseph Rugh
 * PROGRAMMER: Robert Andrew Brown & Joseph Rugh
 * PROJECT: forgestorm-professions
 * DATE: 8/6/2017
 * _______________________________________________________________________________
 *
 * Copyright © 2017 ForgeStorm.com. All Rights Reserved.
 *
 * No part of this project and/or code and/or source code and/or source may be 
 * reproduced, distributed, or transmitted in any form or by any means, 
 * including photocopying, recording, or other electronic or mechanical methods, 
 * without the prior written permission of the owner.
 */

public abstract class Profession implements Listener {

    protected final SpigotCore plugin;
    protected final FileConfiguration fileConfiguration;
    protected final ProfessionType professionType;
    protected final Experience experienceCalculator = new ProfessionExperience();
    protected final int expOffSet = experienceCalculator.getExpOffSet();

    public Profession(SpigotCore plugin, FileConfiguration fileConfiguration, ProfessionType professionType) {
        this.plugin = plugin;
        this.fileConfiguration = fileConfiguration;
        this.professionType = professionType;
    }

    /**
     * This is mainly used by profession menu's to get the players stat rank.
     *
     * @param level The profession level.
     * @return A rank message.
     */
    public static String getProfessionRank(int level) {
        if (level < 20) {
            return "&7Rank: &fNovice";
        } else if (level >= 20 && level < 40) {
            return "&7Rank: &aIntermediate";
        } else if (level >= 40 && level < 60) {
            return "&7Rank: &9Proficient";
        } else if (level >= 60 && level < 80) {
            return "&7Rank: &5Expert";
        } else if (level >= 80) {
            return "&7Rank: &6Grand Master";
        }
        return null;
    }

    /**
     * This is mainly used by profession menu's to get the players stat rank.
     *
     * @param level The profession level.
     * @return A rank message.
     */
    private static int getNextProfessionUpgradeLevel(int level) {
        if (level >= 20 && level < 40) {
            return 40;
        } else if (level >= 40 && level < 60) {
            return 60;
        } else if (level >= 60 && level < 80) {
            return 80;
        } else if (level >= 80) {
            return 100;
        }
        return 20;
    }

    public abstract void onEnable();

    public abstract void onDisable();

    public abstract String getUpgrades(int rankUpgradeLevel);

    /**
     * Calls the profession toggle event. Great if a profession needs to be canceled for
     * whatever reason.
     *
     * @param player The player who performed a profession action.
     * @return True if the event is cancelled, false otherwise.
     */
    protected boolean professionToggleEvent(Player player) {
        ProfessionToggleEvent professionToggleEvent = new ProfessionToggleEvent(player, professionType);
        Bukkit.getPluginManager().callEvent(professionToggleEvent);
        return professionToggleEvent.isCancelled();
    }

    /**
     * This will show the experience gained to the player and show leveling notifications.
     *
     * @param player            The player who is performing a profession action.
     * @param currentExperience The professions current experience prior to the profession action.
     * @param experienceGained  The experience gained after the profession action.
     */
    protected void showExperienceMessages(Player player, long currentExperience, long experienceGained) {
        int oldLevel = experienceCalculator.getLevel(currentExperience);
        int level = experienceCalculator.getLevel(currentExperience + experienceGained);
        int upgradeLevel = getNextProfessionUpgradeLevel(level);
        double percentTillUpgrade = (level * 100) / upgradeLevel;
        String percentString = Integer.toString((int) percentTillUpgrade);
        long exp = currentExperience + experienceGained;
        String professionName = professionType.getProfessionName();

        // Send ActionBar title message.
        plugin.getTitleManagerAPI().sendActionbar(player, generateActionBarMessage(experienceGained, exp, level));

        //Level up check
        if (oldLevel == level) return;

        // Show Leveling message!
        player.sendMessage("");
        player.sendMessage(SpigotCoreMessages.BAR_LEVEL_UP.toString());
        player.sendMessage("");
        player.sendMessage(formatLevelUpMessage(SpigotCoreMessages.LEVEL_UP_01.toString()));
        player.sendMessage(formatLevelUpMessage("&7Your &3" + professionName + " &7profession is now level &3" + level + "&7!!!"));
        player.sendMessage("");
        player.sendMessage(formatLevelUpMessage("&7Upgrade Progression: &d" + percentString + "&7% (level " + level + "/" + upgradeLevel + ")"));
        player.sendMessage(formatLevelUpMessage("&bLevel " + level + " " + ProgressBarString.buildBar(percentTillUpgrade) + " &bLevel " + upgradeLevel));
        player.sendMessage(formatLevelUpMessage("&7Level " + upgradeLevel + " Unlocks: " + getUpgrades(upgradeLevel)));
        player.sendMessage(SpigotCoreMessages.BAR_BOTTOM.toString());

        // Play success sound
        CommonSounds.ACTION_SUCCESS.playSound(player);

        // Show Fireworks for leveling!
        for (double i = 0; i < 2; i++) {
            Firework fw = player.getWorld().spawn(player.getLocation().subtract(0, -1, 0), Firework.class);
            FireworkMeta fm = fw.getFireworkMeta();
            fm.addEffect(FireworkEffect.builder()
                    .flicker(false)
                    .trail(false)
                    .with(FireworkEffect.Type.STAR)
                    .withColor(Color.YELLOW)
                    .withFade(Color.YELLOW)
                    .build());
            fw.setFireworkMeta(fm);
        }
    }

    /**
     * Helper method to auto center and color a message.
     *
     * @param message The message to format.
     * @return A formatted message.
     */
    private String formatLevelUpMessage(String message) {
        return CenterChatText.centerChatMessage(message);
    }

    /**
     * This will generate a message to show the player for using their profession item.
     *
     * @param expGain   The amount of exp they have gained this block break.
     * @param itemEXP   The amount of exp they have in total.
     * @param itemLevel The current level of their profession.
     * @return Returns a detailed and formatted message to show to the player.
     */
    private String generateActionBarMessage(long expGain, long itemEXP, int itemLevel) {
        double expPercent = experienceCalculator.getPercentToLevel(itemEXP);
        int expGoal = experienceCalculator.getExperience(itemLevel + 1) - expOffSet;
        int friendlyExpShow = (int) itemEXP - expOffSet;
        String bar = ProgressBarString.buildBar(expPercent);
        return ChatColor.GRAY + "" + ChatColor.BOLD +
                "EXP: " +
                bar +
                ChatColor.GRAY + ChatColor.BOLD + " " + expPercent + "%" +
                ChatColor.RESET + ChatColor.GRAY + " [" +
                ChatColor.BLUE + friendlyExpShow + " / " + expGoal +
                ChatColor.RESET + ChatColor.GRAY + "] "
                + ChatColor.GREEN + "+" + ChatColor.GRAY + expGain + " EXP";
    }

    /**
     * Generic fail notification to show the user if the profession action failed.
     *
     * @param player The player we will send the message to.
     */
    protected void sendChatFailMessage(Player player, String message) {
        // Send failed notifications.
        player.sendMessage(message);
        CommonSounds.ACTION_FAILED.playSound(player);
    }

    /**
     * Generic fail notification to show the user if the profession action failed.
     *
     * @param player The player we will send the message to.
     */
    protected void sendActionBarFailMessage(Player player, String message) {
        // Send failed notifications.
        plugin.getTitleManagerAPI().sendActionbar(player, ChatColor.RED + "" + ChatColor.BOLD + message);
        CommonSounds.ACTION_FAILED.playSound(player);
    }

    /**
     * Some professions will have a chance to drop gems.
     *
     * @param player The player who we might drop gems next to.
     */
    protected void dropGems(Player player, Location location) {
        int chance = RandomChance.randomInt(1, 100) - 1;
        if (chance >= 96) {
            int amount = RandomChance.randomInt(1, 3) - 1;
            ItemStack gems = new ItemStack(Material.EMERALD);
            gems.setAmount(amount);
            player.getWorld().dropItemNaturally(location, gems);
        }
    }
}
