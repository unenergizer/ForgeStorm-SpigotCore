package com.forgestorm.spigotcore.attributes.armor;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class FireResistance extends Attribute {
	
	public FireResistance() {
		name = "Fire Resistance";
		regexPattern = Pattern.compile("(fire resistance:)[ ][+](\\d+)");
	}
}
