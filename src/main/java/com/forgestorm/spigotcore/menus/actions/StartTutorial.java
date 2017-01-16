package com.forgestorm.spigotcore.menus.actions;

import org.bukkit.entity.Player;

import com.forgestorm.spigotcore.SpigotCore;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StartTutorial implements ClickAction {

	private final SpigotCore PLUGIN;
	private final String TUTORIAL;
	
	@Override
	public void click(Player player) {
		//Start the tutorial.
		PLUGIN.getTutorial().startTutorial(player, TUTORIAL);
	}
}
