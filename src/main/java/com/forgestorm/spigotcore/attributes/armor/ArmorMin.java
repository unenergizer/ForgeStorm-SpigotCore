package com.forgestorm.spigotcore.attributes.armor;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class ArmorMin extends Attribute {
	
	public ArmorMin() {
		name = "Armor";
		regexPattern = Pattern.compile("(armor:)[ ](\\d+)");
	}
}
