package com.forgestorm.spigotcore.constants;

public enum SoundFiles {

    SECRET("Secret.nbs");

    private final String file;

    //Constructor
    SoundFiles(String filePath) {
        this.file = filePath;
    }

    /**
	 * Sends a string representation of the enumerator item.
	 */
	public String toString() {
		return FilePaths.SOUND.toString() + "/" + file;
	}
}
