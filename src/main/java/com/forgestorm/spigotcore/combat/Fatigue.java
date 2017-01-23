package com.forgestorm.spigotcore.combat;

import org.bukkit.Material;

public class Fatigue {
	
	public float getEnergyCost(Material material) {
		
		if(material == Material.AIR) { return 5; }
		
		if(material == Material.WOOD_SWORD) { return 6; }
		if(material == Material.STONE_SWORD) { return 7.1f; }
		if(material == Material.IRON_SWORD) { return 8.33f; }
		if(material == Material.DIAMOND_SWORD) { return 12.5f; }
		if(material == Material.GOLD_SWORD) { return 13.5f; }
		
		if(material == Material.WOOD_AXE) { return 7.931f; }
		if(material == Material.STONE_AXE) { return 9.163f; }
		if(material == Material.IRON_AXE) { return 11; }
		if(material == Material.DIAMOND_AXE) { return 13.75f; }
		if(material == Material.GOLD_AXE) { return 14.85f; }
		
		if(material == Material.WOOD_SPADE) { return 7.21f; }
		if(material == Material.STONE_SPADE) { return 8.33f; }
		if(material == Material.IRON_SPADE) { return 10; }
		if(material == Material.DIAMOND_SPADE) { return 12.5f; }
		if(material == Material.GOLD_SPADE) { return 13.5f; }
		
		if(material == Material.WOOD_HOE) { return 9.09f; }
		if(material == Material.STONE_HOE) { return 10.9f; }
		if(material == Material.IRON_HOE) { return 11.81f; }
		if(material == Material.DIAMOND_HOE) { return 12.72f; }
		if(material == Material.GOLD_HOE) { return 13.63f; }
		
		/*
		if(m == Material.BOW) { // Arrow shooting. Bow punch will be addressed at event level.
			int tier = ItemMechanics.getItemTier(i);
			if(tier == 1) { return 0.08F; }
			if(tier == 2) { return 0.10F; }
			if(tier == 3) { return 0.11F; }
			if(tier == 4) { return 0.13F; }
			if(tier == 5) { return 0.15F; }
		}
		*/
		
		return 1;
	}
}
