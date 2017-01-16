package com.forgestorm.spigotcore.attributes.armor;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class HealthRegeneration extends Attribute {
	
	public HealthRegeneration() {
		name = "Health Regeneration";
		regexPattern = Pattern.compile("(hp regen:)[ ][+](\\d+)");
	}
}
