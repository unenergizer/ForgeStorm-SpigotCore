package com.forgestorm.spigotcore.attributes.weapon;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class DamageMax extends Attribute {
	
	public DamageMax() {
		name = "Damage";
		regexPattern = Pattern.compile("(dmg:)[ ](\\d+-)(\\d+)");
		group = 3;
	}
}
