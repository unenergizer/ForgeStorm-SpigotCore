package com.forgestorm.spigotcore.attributes.armor;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class Intellect extends Attribute {
	
	public Intellect() {
		name = "Intellect";
		regexPattern = Pattern.compile("(int:)[ ][+](\\d+)");
	}
}
