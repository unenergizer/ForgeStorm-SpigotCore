package com.forgestorm.spigotcore.util.display;

import com.forgestorm.spigotcore.SpigotCore;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AnimatedBossBarAnnouncer extends BossBarAnnouncer {
	
	private final SpigotCore PLUGIN;
	private List<String> messages;
	private boolean isCanceled;
	private int animatedFrame;
	
	public AnimatedBossBarAnnouncer(String message, SpigotCore plugin) {
		super(message);
		PLUGIN = plugin;
		isCanceled = false;
		animatedFrame = 0;
	}
	
	/**
	 * Adds messages to the boss bar.
	 * @param announcements The messages that will be looped through.
	 */
	public void addMessages(List<String> announcements) {
		this.messages = announcements;
	}
	
	/**
	 * Starts the boss bar animation.
	 */
	public void start() {
		new BukkitRunnable() {

			@Override
			public void run() {
				
				//Cancel the animation.
				if (messages == null || isCanceled) {
					cancel();
				}
				
				//Show message
				setBossBarTitle(messages.get(animatedFrame));
				
				animatedFrame++;
				
				//Reset the animated frame after a complete animation.
				if (animatedFrame == messages.size()) {
					animatedFrame = 0;
				}
				
			}
			
		}.runTaskTimer(PLUGIN, 0, 20);
	}
	
	/**
	 * Cancels the boss bar animation.
	 */
	public void cancel() {
		isCanceled = true;
	}
}
