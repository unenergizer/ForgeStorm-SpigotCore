package com.forgestorm.spigotcore.attributes.weapon;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class VersusMonster extends Attribute {
	
	public VersusMonster() {
		name = "Versus Monster";
		regexPattern = Pattern.compile("(vs. mob:)[ ][+](\\d+)");
	}
}
