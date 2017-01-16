package com.forgestorm.spigotcore.attributes.armor;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class Strength extends Attribute {
	
	public Strength() {
		name = "Strength";
		regexPattern = Pattern.compile("(str:)[ ][+](\\d+)");
	}
}
