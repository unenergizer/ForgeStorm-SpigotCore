package com.forgestorm.spigotcore.attributes.weapon;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class LifeSteal extends Attribute {
	
	public LifeSteal() {
		name = "Life Steal";
		regexPattern = Pattern.compile("(life steal:)[ ][+](\\d+)");
	}
}
