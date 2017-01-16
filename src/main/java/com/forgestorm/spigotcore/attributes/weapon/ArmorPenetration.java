package com.forgestorm.spigotcore.attributes.weapon;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class ArmorPenetration extends Attribute {
	
	public ArmorPenetration() {
		name = "Armor Penetration";
		regexPattern = Pattern.compile("(armor penetration:)[ ][+](\\d+)");
	}
}
