package com.forgestorm.spigotcore.item.profession;

import com.forgestorm.spigotcore.item.Item;
import com.forgestorm.spigotcore.item.ItemQuality;

public abstract class ProfessionItem extends Item {

	protected int double_reward_percent;
	protected int double_reward_min;
	protected int double_reward_max;
	protected int triple_reward_percent;
	protected int triple_reward_min;
	protected int triple_reward_max;
	protected int durability_percent;
	protected int durability_min;
	protected int durability_max;
	protected int gem_find_percent;
	protected int gem_find_min;
	protected int gem_find_max;
	protected int treasure_find_percent;
	protected int treasure_find_min;
	protected int treasure_find_max;
	protected int action_success_percent;
	protected int action_success_min;
	protected int action_success_max;

	public ProfessionItem(ItemQuality quality) {
		super(quality);
	}
	
	/*
	public ItemStack createTool() {
		ArrayList<String> lores = new ArrayList<>();
		lores.add(ChatColor.GRAY + "" + ChatColor.BOLD + "LVL: " + ChatColor.RESET + ChatColor.LIGHT_PURPLE + "1");
		lores.add(ChatColor.GRAY + "" + ChatColor.BOLD + "EXP: " + ChatColor.RESET + ChatColor.BLUE + "0" + " / " + experience.getExperience(2));
		lores.add(ChatColor.GRAY + "" + ChatColor.BOLD + "EXP: " + new ProgressBarString(0).buildBar() + ChatColor.GRAY + " " + "0" + "%");
		lores.add(" ");//create blank space
		lores.add(ChatColor.GRAY + "" + ChatColor.ITALIC + noviceDescription);
		
		return new ItemBuilder(noviceTool).setTitle(noviceColor + noviceTitle).addLores(lores).build();
	}
	
	public void updateToolLore(ItemStack item, List<String> updatedLore) {
		item.getItemMeta().setLore(updatedLore);
	}
	*/

}
