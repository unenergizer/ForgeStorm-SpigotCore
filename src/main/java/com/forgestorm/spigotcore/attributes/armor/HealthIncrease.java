package com.forgestorm.spigotcore.attributes.armor;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class HealthIncrease extends Attribute {
	
	public HealthIncrease() {
		name = "Health Invrease";
		regexPattern = Pattern.compile("(hp:)[ ][+](\\d+)");
	}
}
