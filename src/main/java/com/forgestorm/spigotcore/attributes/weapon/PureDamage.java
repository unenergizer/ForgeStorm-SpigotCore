package com.forgestorm.spigotcore.attributes.weapon;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class PureDamage extends Attribute {
	
	public PureDamage() {
		name = "Pure Damage";
		regexPattern = Pattern.compile("(pure dmg:)[ ][+](\\d+)");
	}
}
