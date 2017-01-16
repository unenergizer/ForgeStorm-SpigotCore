package com.forgestorm.spigotcore.world.animate;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.forgestorm.spigotcore.SpigotCore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorldTimer extends BukkitRunnable {

	private final SpigotCore PLUGIN;
	private int day = 0;
	private long time = 0;
	private long lastTime = Long.MAX_VALUE;
	
	public WorldTimer(final SpigotCore plugin) {
		this.PLUGIN = plugin;
	}
	
	@Override
	public void run() {
		time = Bukkit.getWorlds().get(0).getTime();
		
		//Check if something should animate.
		//If world is paused, only animate once.		
		if (time != lastTime) {
			
			//Increment days
			if (time == 0) {
				day++;
				System.out.println("Day: " + day);
			}
			
			//Animate
			PLUGIN.getWorldAnimator().shouldAnimate(time);
			lastTime = time;
		}
		
		//Check if a block should be restored in the world
		//TODO:
		
		//Print out the time in console.
		//System.out.println("Time: " + Long.toString(time));
	}
}
