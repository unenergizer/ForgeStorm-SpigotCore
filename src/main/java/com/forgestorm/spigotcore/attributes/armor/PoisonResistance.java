package com.forgestorm.spigotcore.attributes.armor;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class PoisonResistance extends Attribute {
	
	public PoisonResistance() {
		name = "Poison Resistance";
		regexPattern = Pattern.compile("(poison resistance:)[ ][+](\\d+)");
	}
}
