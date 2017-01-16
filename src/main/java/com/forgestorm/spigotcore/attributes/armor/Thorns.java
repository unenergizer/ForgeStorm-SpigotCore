package com.forgestorm.spigotcore.attributes.armor;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class Thorns extends Attribute {
	
	public Thorns() {
		name = "Thorns";
		regexPattern = Pattern.compile("(thorns:)[ ][+](\\d+)");
	}
}
