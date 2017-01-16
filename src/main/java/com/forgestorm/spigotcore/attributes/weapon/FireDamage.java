package com.forgestorm.spigotcore.attributes.weapon;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class FireDamage extends Attribute {
	
	public FireDamage() {
		name = "Fire Damage";
		regexPattern = Pattern.compile("(fire dmg:)[ ][+](\\d+)");
	}
}
