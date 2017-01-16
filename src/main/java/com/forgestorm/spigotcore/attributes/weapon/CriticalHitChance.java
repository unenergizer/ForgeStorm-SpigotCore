package com.forgestorm.spigotcore.attributes.weapon;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class CriticalHitChance extends Attribute {
	
	public CriticalHitChance() {
		name = "Critical Hit Chance";
		regexPattern = Pattern.compile("(critical hit:)[ ][+](\\d+)");
	}
}
