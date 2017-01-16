package com.forgestorm.spigotcore.attributes.armor;

import java.util.regex.Pattern;

import com.forgestorm.spigotcore.attributes.Attribute;

public class ArmorMax extends Attribute {
	
	//armor: 2-5
	
	public ArmorMax() {
		name = "Armor";
		regexPattern = Pattern.compile("(armor:)[ ](\\d+-)(\\d+)");
		group = 3;
	}
}
