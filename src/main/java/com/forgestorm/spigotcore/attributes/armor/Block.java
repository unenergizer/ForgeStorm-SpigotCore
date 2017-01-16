package com.forgestorm.spigotcore.attributes.armor;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class Block extends Attribute {
	
	public Block() {
		name = "Block";
		regexPattern = Pattern.compile("(block:)[ ][+](\\d+)");
	}
}
