package com.forgestorm.spigotcore.attributes.armor;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class GemFind extends Attribute {
	
	public GemFind() {
		name = "Gem Find";
		regexPattern = Pattern.compile("(gem find:)[ ][+](\\d+)");
	}
}
