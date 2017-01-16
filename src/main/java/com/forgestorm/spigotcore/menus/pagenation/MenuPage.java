package com.forgestorm.spigotcore.menus.pagenation;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.ClickAction;

import lombok.Getter;
import lombok.Setter;

public interface MenuPage {
	List<SlotData> generateItems(SpigotCore plugin);
}

@Getter
@Setter
class SlotData {
	private int slot;
	private ItemStack item;
	private ClickAction clickAction = null;
	private Class<? extends Menu> clazz = null;
	
	public SlotData(ItemStack item, int slot) {
		this.item = item;
		this.slot = slot;
	}
}
