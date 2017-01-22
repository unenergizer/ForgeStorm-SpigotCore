package com.forgestorm.spigotcore.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import com.forgestorm.spigotcore.SpigotCore;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeatherChange  implements Listener {

	private final SpigotCore plugin;
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent event) {
		
		//Cancel weather changes not made by the WorldAnimator.
		if (!plugin.getWorldAnimator().isAllowWeatherChange()) {
			event.setCancelled(true);
		}
	}
}
