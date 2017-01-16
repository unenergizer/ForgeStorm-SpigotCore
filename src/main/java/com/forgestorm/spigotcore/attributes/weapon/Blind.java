package com.forgestorm.spigotcore.attributes.weapon;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class Blind extends Attribute {
	
	public Blind() {
		name = "Blind";
		regexPattern = Pattern.compile("(blind:)[ ][+](\\d+)");
	}
}
