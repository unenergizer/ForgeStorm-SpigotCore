package com.forgestorm.spigotcore.attributes.weapon;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class PoisonDamage extends Attribute {
	
	public PoisonDamage() {
		name = "Poison Damage";
		regexPattern = Pattern.compile("(poison dmg:)[ ][+](\\d+)");
	}
}
