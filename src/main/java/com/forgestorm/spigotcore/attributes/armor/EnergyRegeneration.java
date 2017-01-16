package com.forgestorm.spigotcore.attributes.armor;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class EnergyRegeneration extends Attribute {
	
	public EnergyRegeneration() {
		name = "Energy Regeneration";
		regexPattern = Pattern.compile("(energy regen:)[ ][+](\\d+)");
	}
}
