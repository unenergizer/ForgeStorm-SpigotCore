package com.forgestorm.spigotcore.util.math;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;

/**
 * This class will convert seconds to a readable string.
 * 
 * @author xYourFreindx
 * @link https://www.spigotmc.org/resources/timeformat-api.16318/
 * @see USAGE: TimeUnit.toString(69696969);
 */
public enum TimeUnit {

	SEC(ChatColor.GREEN + "second" + ChatColor.YELLOW, 1, 's'),
	MIN(ChatColor.GREEN + "minute" + ChatColor.YELLOW, 60, 'm'),
	HOUR(ChatColor.GREEN + "hour" + ChatColor.YELLOW, 3600, 'h'),
	DAY(ChatColor.GREEN + "day" + ChatColor.YELLOW, 86400, 'd'),
	WEEK(ChatColor.GREEN + "week" + ChatColor.YELLOW, 604800,'w');

	private final String name;
	private final long ms;
	private final char unit;

	private static final Map<Character, Long> convertion = new HashMap<Character, Long>();
	private static final TimeUnit[] order = new TimeUnit[] { WEEK, DAY, HOUR, MIN, SEC };
	private static final Pattern isLong = Pattern.compile("[0-9]+");

	static {
		for (TimeUnit unit : values())
			convertion.put(unit.unit, unit.ms);
	}

	private TimeUnit(String name, long ms, char unit) {
		this.name = name;
		this.ms = ms;
		this.unit = unit;
	}

	public String addUnit(int x) {
		String r = this.name;
		if (x > 1)
			r += ChatColor.GREEN + "s" + ChatColor.YELLOW;
		return x + " " + r;
	}

	private static long convert(char c) {
		if (convertion.containsKey(c))
			return convertion.get(c);
		return 0;
	}

	public static String toString(long time) {
		StringBuilder sb = new StringBuilder();
		int t;
		for (TimeUnit unit : order) {
			if (time >= unit.ms) {
				t = (int) Math.floor((double) time / (double) unit.ms);
				sb.append(unit.addUnit(t)).append(" ");
				time -= t * unit.ms;
			}
		}
		return sb.toString().trim();
	}

	public static long toLong(String string) {
		long fromString = 0;
		for (String t : string.split(" ")) {
			char c = t.charAt(t.length() - 1);
			t = t.substring(0, t.length() - 1);
			if (!isLong.matcher(t).matches())
				continue;
			fromString += Long.parseLong(t) * TimeUnit.convert(c);
		}
		return fromString;
	}
}
