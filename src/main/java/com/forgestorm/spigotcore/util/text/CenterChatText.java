package com.forgestorm.spigotcore.util.text;

import org.bukkit.ChatColor;

import com.forgestorm.spigotcore.constants.DefaultFontInfo;

public class CenterChatText {
	
	/**
	 * This will take a message and center it for the Minecraft chat box.
	 * 
	 * @param message The message that will be centered.
	 * @return Returns a message that is perfectly (or near perfect) centered.
	 */
	public static String centerChatMessage(String message) {
		final int DEFAULT_WIDTH = 154;
		return centerMessage(DEFAULT_WIDTH, message);
	}
	
	/**
	 * This will take a message and center it for the Minecraft chat box.
	 * 
	 * @param width How wide the text area is in pixels.
	 * @param message The message that will be centered.
	 * @return Returns a message that is perfectly (or near perfect) centered.
	 */
	public static String centerMessage(int width, String message){
		if (message != null && !message.equals("")) {
			message = ChatColor.translateAlternateColorCodes('&', message);

			int messagePxSize = 0;
			boolean previousCode = false;
			boolean isBold = false;

			for (char c : message.toCharArray()) {
				if (c == '§') {
					previousCode = true;
					continue;
				} else if (previousCode == true) {
					previousCode = false;
					if (c == 'l' || c == 'L') {
						isBold = true;
						continue;
					} else isBold = false;
				} else {
					DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
					messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
					messagePxSize++;
				}
			}

			int halvedMessageSize = messagePxSize / 2;
			int toCompensate = width - halvedMessageSize;
			int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
			int compensated = 0;
			StringBuilder sb = new StringBuilder();
			
			while (compensated < toCompensate) {
				sb.append(" ");
				compensated += spaceLength;
			}
			return sb.toString() + message;
		}
		return "";
	}
}
