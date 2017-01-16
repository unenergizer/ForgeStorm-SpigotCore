package com.forgestorm.spigotcore.attributes.armor;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class IceResistance extends Attribute {
	
	public IceResistance() {
		name = "Ice Resistance";
		regexPattern = Pattern.compile("(ice resistance:)[ ][+](\\d+)");
	}
}
