package com.forgestorm.spigotcore.constants;

public enum Sounds {
	
	SECRET("Secret.nbs");
	
	private String file;
	
	//Constructor
	Sounds(String filePath) {
		this.file = filePath;
	}
	
	/**
	 * Sends a string representation of the enumerator item.
	 */
	public String toString() {
		return FilePaths.SOUND.toString() + "/" + file;
	}
}
