package com.forgestorm.spigotcore.attributes.armor;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class Dodge extends Attribute {
	
	public Dodge() {
		name = "Dodge";
		regexPattern = Pattern.compile("(dodge:)[ ][+](\\d+)");
	}
}
