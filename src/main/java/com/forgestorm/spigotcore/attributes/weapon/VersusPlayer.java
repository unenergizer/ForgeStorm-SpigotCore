package com.forgestorm.spigotcore.attributes.weapon;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class VersusPlayer extends Attribute {
	
	public VersusPlayer() {
		name = "Versus Player";
		regexPattern = Pattern.compile("(vs. player:)[ ][+](\\d+)");
	}
}
