package com.forgestorm.spigotcore.attributes.armor;

import com.forgestorm.spigotcore.attributes.Attribute;

import java.util.regex.Pattern;

public class HealthIncrease extends Attribute {
	
	public HealthIncrease() {
		name = "Health Increase";
		regexPattern = Pattern.compile("(hp:)[ ][+](\\d+)");
	}
}
