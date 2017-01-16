package com.forgestorm.spigotcore.util.text;

import java.util.ArrayList;

import org.bukkit.ChatColor;

public class StringSplitter {
	
	public static ArrayList<String> split(String string, int maxLength) {
		String[] split = string.split(" ");
		string = "";
		ArrayList<String> newString = new ArrayList<String>();
		for (int i = 0; i < split.length; i++) {
			string += (string.length() == 0 ? "" : " ") + split[i];
			if (ChatColor.stripColor(string).length() > maxLength) {
				newString
				.add((newString.size() > 0 ? ChatColor.getLastColors(newString.get(newString.size() - 1)) : "") + string);
				string = "";
			}
		}
		if (string.length() > 0)
			newString.add((newString.size() > 0 ? ChatColor.getLastColors(newString.get(newString.size() - 1)) : "") + string);
		return newString;
	}
}
