package com.forgestorm.spigotcore.attributes.weapon;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class IceDamage extends Attribute {
	
	public IceDamage() {
		name = "Ice Damage";
		regexPattern = Pattern.compile("(ice dmg:)[ ][+](\\d+)");
	}
}
