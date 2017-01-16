package com.forgestorm.spigotcore.attributes.armor;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class Dexterity extends Attribute {
	
	public Dexterity() {
		name = "Dexterity";
		regexPattern = Pattern.compile("(dex:)[ ][+](\\d+)");
	}
}
