package com.forgestorm.spigotcore.item;

import com.forgestorm.spigotcore.util.item.ItemBuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Random;

@Getter
@Setter
public abstract class Item {

	protected ItemStack item;
	protected Material material;
	protected String title;
	protected String description;
	protected ArrayList<String> lores;
	protected ItemQuality quality;
	protected Tier tier;
	
	public Item(ItemQuality quality) {
		this.quality = quality;
	}
	
	protected abstract ArrayList<String> generateLore();
	
	/**
	 * Removes tag information for an item and returns it back to
	 * the calling function
	 * 
	 * @return The item
	 */
	protected ItemStack buildItem() {
		ItemStack item = new ItemBuilder(material).setTitle(quality.colorToString() + title).addLores(lores).build(true);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		itemMeta.addItemFlags(ItemFlag.HIDE_DESTROYS);
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
		item.setItemMeta(itemMeta);
		return item;
	}
	
	public void dropItem(Location location) {
		Bukkit.getWorld(location.getWorld().getName()).dropItemNaturally(location, item);
	}
	
	public void givePlayerItem(Player player) {
		PlayerInventory inv = player.getInventory();
		
		for (int i = 0; i <= 35; i++) {
			if (inv.getItem(i) == null) {
				inv.setItem(i, item);
				return;
			}
		}

	}
	
	protected int loreChance(int percent, int min, int max) {
		Random random = new Random();
		int willDrop = random.nextInt(100 - 1 + 1) + 1;
		
		if (percent >= willDrop) {
			int randomNum = random.nextInt((max - min) + 1) + min;
			double qualityIncrease = quality.getMultiplier() * randomNum;
			return  randomNum + (int)qualityIncrease;
		}
		
		return 0;
	}
}
