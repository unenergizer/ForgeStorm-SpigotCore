package com.forgestorm.spigotcore.attributes.weapon;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class Knockback extends Attribute {
	
	public Knockback() {
		name = "Knockback";
		regexPattern = Pattern.compile("(knockback:)[ ][+](\\d+)");
	}
}
