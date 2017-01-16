package com.forgestorm.spigotcore.api;

import org.bukkit.Bukkit;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.Messages;
import com.forgestorm.spigotcore.util.item.ItemGenerator;

public class ItemGeneratorAPI {
	
	private final SpigotCore PLUGIN = (SpigotCore) Bukkit.getPluginManager().getPlugin(Messages.DEFAULT_PLUGIN_NAME.toString());

	public ItemGenerator getItemGenerator() {
		return PLUGIN.getItemGen();
	}
}