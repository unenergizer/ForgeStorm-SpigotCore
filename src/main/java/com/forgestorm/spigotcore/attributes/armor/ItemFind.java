package com.forgestorm.spigotcore.attributes.armor;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class ItemFind extends Attribute {
	
	public ItemFind() {
		name = "Item Find";
		regexPattern = Pattern.compile("(item find:)[ ][+](\\d+)");
	}
}
