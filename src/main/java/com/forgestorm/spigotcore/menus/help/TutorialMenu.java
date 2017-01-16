package com.forgestorm.spigotcore.menus.help;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.StartTutorial;
import com.forgestorm.spigotcore.util.item.ItemBuilder;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import com.forgestorm.spigotcore.util.text.StringSplitter;

import net.md_5.bungee.api.ChatColor;

public class TutorialMenu extends Menu {

	private final SpigotCore PLUGIN;

	public TutorialMenu(SpigotCore plugin) {
		super(plugin);
		PLUGIN = plugin;
		init("Tutorial Menu", 1);
		makeMenuItems();
	}

	@Override
	protected void makeMenuItems() {
		FileConfiguration config = PLUGIN.getTutorial().getConfig();
		ConfigurationSection section = config.getConfigurationSection("Tutorial");
		String material, name;
		Iterator<String> it = section.getKeys(false).iterator();
		//TODO: dont add some tuts... lol rekt. boolean tutMenuEnabled = config.getBoolean("Tutorial." + tutorialID + ".showInTutorialMenu");
		
		//Add Back Button
		ItemStack backButton, exitButton;
		ItemTypes type = ItemTypes.MENU;
		ItemGenerator itemGen = PLUGIN.getItemGen();
		backButton = itemGen.generateItem("back_button", type);
		exitButton = itemGen.generateItem("exit_button", type);
		
		setItem(backButton, 7, HelpMenu.class);
		setItem(exitButton, 8, new Exit(PLUGIN));
		
		//Add Tutorials
		while (it.hasNext()) {
			String i = it.next();

			material = config.getString("Tutorial." + i + ".menuItem.material");
			name = ChatColor.GREEN + config.getString("Tutorial." + i + ".menuItem.name");
			int amount = config.getInt("Tutorial." + i + ".menuItem.amount");
			boolean enchanted = config.getBoolean("Tutorial." + i + ".menuItem.enchanted");
			
			//Get Lores
			String lore = config.getString("Tutorial." + i + ".menuItem.lore");		
			String start = ChatColor.YELLOW + "Click to Start the Tutorial!";
			
			List<String> lines = StringSplitter.split(lore, 20);
			List<String> linesColored = new ArrayList<>();
			
			for (String string : lines) {
				linesColored.add(ChatColor.GRAY + string);
			}

			linesColored.add("");
			linesColored.add(start);
			
			//Make ItemStack
			ItemStack tutorialItem = null;
			if (enchanted) {
				//Adds enchantment effect!
				tutorialItem = new ItemBuilder(Material.valueOf(material))
						.setTitle(name)
						.addLore("")
						.addLores(linesColored)
						.addEnchantment(Enchantment.ARROW_DAMAGE, 1)
						.setAmount(amount)
						.build(true);
			} else {
				//No enchantment effect!
				tutorialItem = new ItemBuilder(Material.valueOf(material))
						.setTitle(name)
						.addLore("")
						.addLores(linesColored)
						.setAmount(amount)
						.build(true);
			}
			
			//Set ItemStack in the menu.
			setItem(tutorialItem, Integer.parseInt(i), new StartTutorial(PLUGIN, i));
		}		
	}
}
