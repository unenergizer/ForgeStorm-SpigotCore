package com.forgestorm.spigotcore.attributes.armor;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class Vitality extends Attribute {
	
	public Vitality() {
		name = "Vitality";
		regexPattern = Pattern.compile("(vit:)[ ][+](\\d+)");
	}
}
