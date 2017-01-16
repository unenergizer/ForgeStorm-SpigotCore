package com.forgestorm.spigotcore.attributes.armor;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class Reflection extends Attribute {
	
	public Reflection() {
		name = "Reflection";
		regexPattern = Pattern.compile("(reflection:)[ ][+](\\d+)");
	}
}
