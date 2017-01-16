package com.forgestorm.spigotcore.profession;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.FilePaths;
import com.forgestorm.spigotcore.constants.ProfessionType;
import com.forgestorm.spigotcore.experience.Experience;
import com.forgestorm.spigotcore.experience.ProfessionExperience;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;
import com.forgestorm.spigotcore.util.display.ActionBarText;
import com.forgestorm.spigotcore.util.math.RandomChance;
import com.forgestorm.spigotcore.util.text.ProgressBarString;
import com.forgestorm.spigotcore.world.BlockRegenerationManager;

public class Profession {

	private final SpigotCore PLUGIN;
	private final boolean showDebugMessages = false;
	
	private List<String> tools;
	private FileConfiguration config;
	private Experience experienceCalculator;

	public Profession(SpigotCore plugin) {
		PLUGIN = plugin;
		tools = new ArrayList<>();
		config = YamlConfiguration.loadConfiguration(
				new File(FilePaths.TOOL_INFORMATION.toString()));
		experienceCalculator = new ProfessionExperience();
		
		loadConfig();
	}

	public boolean toggleProfession(Player player, Material tool, Block block) {

		String toolName = tool.toString();
		String blockName = block.getType().toString();
		
		showDebugMessage("[PROFESSION] TOGGLED!");
		showDebugMessage("[PROFESSION] Tool: " + toolName + " Block: " + blockName);
		
		//tool check. if tool exists in config continue
		if (tools.contains(toolName) && tool != null && blockName != null) {
			
			showDebugMessage("[PROFESSION] TOOL EXISTS!");

			//block check, if block exists in tool config, continue
			if (config.contains(toolName + ".breaks." + blockName)) {

				showDebugMessage("[PROFESSION] BLOCK EXISTS!");
				
				//calculate profession success
				int chance = config.getInt(toolName + ".breaks." +
						blockName + ".success_rate");
				showDebugMessage("[PROFESSION] SUCCESS RATE: " + chance);

				if (RandomChance.testChance(chance)) {

					showDebugMessage("[PROFESSION] TOGGLE SUCCESS!");
					
					//Give EXP
					PlayerProfileData profile = PLUGIN.getProfileManager().getProfile(player);
					long expReward = config.getLong(toolName + ".breaks." + blockName + ".exp");
					String profession = config.getString(toolName + ".profession").toUpperCase(); 
					ProfessionType professionType = ProfessionType.valueOf(profession);
					BlockRegenerationManager blockRegen = PLUGIN.getBlockRegen();
					Material blockType = block.getType();
					
					long toolEXP = 0;
					
					switch (professionType) {
					case FARMING:
						toolEXP = profile.getFarmingExperience();
						profile.setFarmingExperience(toolEXP + expReward);
						blockRegen.setBlock(blockType, Material.AIR, block.getLocation());
						showDebugMessage("Toggled farming profession!");
						break;
					case FISHING:
						toolEXP = PLUGIN.getProfileManager().getProfile(player).getFishingExperience();
						profile.setFishingExperience(toolEXP + expReward);
						showDebugMessage("Toggled fishing profession!");
						break;
					case LUMBERJACK:
						toolEXP = PLUGIN.getProfileManager().getProfile(player).getLumberjackExperience();
						profile.setLumberjackExperience(toolEXP + expReward);
						blockRegen.setBlock(blockType, Material.WOOD, block.getLocation());
						showDebugMessage("Toggled lumberjack profession!");
						break;
					case MINING:
						toolEXP = PLUGIN.getProfileManager().getProfile(player).getMiningExperience();
						profile.setMiningExperience(toolEXP + expReward);
						blockRegen.setBlock(blockType, Material.STONE, block.getLocation());
						showDebugMessage("Toggled mining profession!");
						break;
					}

					//Give Item Drops
					//TODO!

					//CHECK IF ITEM NEEDS TO LEVEL UP
					int oldLevel = experienceCalculator.getLevel(toolEXP);
					int level = experienceCalculator.getLevel(toolEXP + expReward);
					long exp = toolEXP + expReward;
					showDebugMessage("[PROFESSION] OLD LEVEL: " + oldLevel);
					showDebugMessage("[PROFESSION] LEVEL: " + level + " EXP: " + exp);
					
					new ActionBarText().sendActionbarMessage(player, showEXPLevel(expReward, exp, level));
					
					//Level up check
					if (oldLevel != level) {
						boolean showUpgradeMsg = false;

						//IF LEVELING UP, IS IT LEVEL 20,40,60,80, to upgrade tool type?
						//aka going from wood -> stone pickaxe on leveling up at lvl 20		
						ItemStack item = player.getInventory().getItemInMainHand();
						
						switch (level) {
						case 20:
							showUpgradeMsg = true;
							item.setType(Material.valueOf(toolName.replace("WOOD", "STONE")));
							break;
						case 40:
							showUpgradeMsg = true;
							item.setType(Material.valueOf(toolName.replace("STONE", "IRON")));
							break;
						case 60:
							showUpgradeMsg = true;
							item.setType(Material.valueOf(toolName.replace("IRON", "DIAMOND")));
							break;
						case 80:
							showUpgradeMsg = true;
							item.setType(Material.valueOf(toolName.replace("DIAMOND", "GOLD")));
							break;
						}

						//Show Leveling message!
						player.sendMessage("Congradulations! Your profession item has leveled up!");
						player.sendMessage(ChatColor.GREEN + "Your pick is now level " + ChatColor.GOLD + level + ChatColor.GREEN + ".");
						

						//Show profession item upgrade message.
						if (showUpgradeMsg) {
							player.sendMessage("Congradulations! Your profession item has been upgraded!");
						}
					}

					return true; //allow the item to be broken
				} else {
					player.sendMessage("Your profession action was unsuccessful.");
					return false;
				}
			}
		}
		return false; //can not break block.
	}
	
	private String showEXPLevel(long expGain, long itemEXP, int itemLevel){
		double expPercent = experienceCalculator.getPercentToLevel(itemEXP);
		int expGoal = experienceCalculator.getExperience(itemLevel + 1 );
		String bar = ProgressBarString.buildBar(expPercent);
		return ChatColor.GRAY + "" + ChatColor.BOLD + 
				"EXP: " + 
				bar + 
				ChatColor.GRAY + ChatColor.BOLD + " " + expPercent + "%" + 
				ChatColor.RESET + ChatColor.GRAY + " [" + 
				ChatColor.BLUE + itemEXP + " / " + expGoal +
				ChatColor.RESET + ChatColor.GRAY + "] "
				+ ChatColor.GREEN + "+" + ChatColor.GRAY+ expGain + " EXP";
	}

	/**
	 * This will update the players item in hand to show the stats on the
	 * ItemStack meta. Also used for the menu system. This should only be called
	 * when the player open's their inventory.
	 * 
	 * @param player The player who opened their inventory.
	 */
	public void upgradeProfessionItems(Player player) {
		//Update items
		//TODO
	}

	private void loadConfig() {

		ConfigurationSection section = config.getConfigurationSection("");
		Iterator<String> it = section.getKeys(false).iterator();

		while (it.hasNext()) {
			tools.add(it.next());
		}
	}
	
	private void showDebugMessage(String message) {
		if (showDebugMessages) {
			System.out.println(message);
		}
	}
}
