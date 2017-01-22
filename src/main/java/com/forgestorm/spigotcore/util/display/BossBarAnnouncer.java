package com.forgestorm.spigotcore.util.display;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.List;

class BossBarAnnouncer {
	
	private BossBar bar;
	private final String message;
	
	BossBarAnnouncer(String message) {
		this.message = message;
		setupBossBar();
	}
	
	/**
	 * This will setup a new bossbar.
	 */
	private void setupBossBar() {
		bar = Bukkit.createBossBar(color(message), BarColor.PURPLE, BarStyle.SOLID, BarFlag.DARKEN_SKY);
		bar.setVisible(true);
	}
	
	/**
	 * Sends a player entity a boss bar message.
	 * @param player The player who will receive a boss bar message.
	 */
	public void showBossBar(Player player) {
		bar.addPlayer(player);
	}

	/**
	 * Removes a boss bar from a player.
	 * @param player Will remove a boss bar from this player.
	 */
	public void removeBossBar(Player player) {
		bar.removePlayer(player);
	}
	
	/**
	 * Removes a boss bar from all players.
	 */
	public void removeAllBossBar() {
		for (Player players: Bukkit.getOnlinePlayers()) {
			bar.removePlayer(players);
		}
	}
	
	/**
	 * Converts special characters in text into Minecraft client color codes.
	 * <p>
	 * This will give the messages color.
	 * @param msg The message that needs to have its color codes converted.
	 * @return Returns a colored message!
	 */
	private String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
	
	/**
	 * Sets the progress of the current bar. From 0 - 1;
	 * @param progress The progress of the current bar.
	 */
	public void setBossBarProgress(double progress) {
		bar.setProgress(progress);
	}
	
	/**
	 * Sets a new title for the bar.
	 * @param title The title of the bar.
	 */
	void setBossBarTitle(String title) {
		bar.setTitle(color(title));
	}
	
	/**
	 * Gets a list of players who are viewing this bossbar.
	 * @return	Returns a list of players viewing this bossbar.
	 */
	public List<Player> getBossBarViewers() { return bar.getPlayers(); }
}
