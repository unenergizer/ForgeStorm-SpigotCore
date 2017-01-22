package com.forgestorm.spigotcore.attributes;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Attribute {
	
	protected String name;
	protected Pattern regexPattern;
	protected int group = 2;
	
	public Integer getAttributeValue(LivingEntity entity) {
		int value = 0;
		
		//Armor Pen: +5%, armor 4-5, thorns: +3
		
		//Check Armor.
		for (ItemStack item : entity.getEquipment().getArmorContents()) {

			//If the item is not null and has meta and has lore, continue.
			if ((item != null) && (item.hasItemMeta()) && (item.getItemMeta().hasLore())) {

				//Create an array list of "Lore" strings and add them to it.
				List<String> lore = item.getItemMeta().getLore();

				//Convert the array to a string, and make it all lowercase.
				String allLore = ChatColor.stripColor(lore.toString().toLowerCase());

				//Prepare for text matching using regular expressions.
				Matcher matcher = regexPattern.matcher(allLore);

				//Find the new value.
				if (matcher.find()) {
					//Add the value to the total.
					value += Integer.valueOf(matcher.group(group));
				}
			}
		}		
		
		//Check items in hand.
		ItemStack handItem = entity.getEquipment().getItemInMainHand();
		//If the item is not null and has meta and has lore, continue.
		if ((handItem != null) && (handItem.hasItemMeta()) && (handItem.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = handItem.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher matcher = regexPattern.matcher(allLore);
			
			//Find the new value.
			if (matcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(matcher.group(group));
			}
		}
		
		//Check off hand items.
		ItemStack offHandItem = entity.getEquipment().getItemInOffHand();
		//If the item is not null and has meta and has lore, continue.
		if ((offHandItem != null) && (offHandItem.hasItemMeta()) && (offHandItem.getItemMeta().hasLore())) {
			
			//Create an array list of "Lore" strings and add them to it.
			Object lore = offHandItem.getItemMeta().getLore();
			
			//Convert the array to a string, and make it all lowercase.
			String allLore =  ChatColor.stripColor(lore.toString().toLowerCase());
			
			//Prepare for text matching using regular expressions.
			Matcher matcher = regexPattern.matcher(allLore);
			
			//Find the new value.
			if (matcher.find()) {
				//Add the value to the total.
				value += Integer.valueOf(matcher.group(group));
			}
		}
		return value;
	}
}
