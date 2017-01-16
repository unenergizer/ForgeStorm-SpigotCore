package com.forgestorm.spigotcore.attributes.weapon;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class DamageMin extends Attribute {
	
	public DamageMin() {
		name = "Damage";
		regexPattern = Pattern.compile("(dmg:)[ ](\\d+)");
	}
}
