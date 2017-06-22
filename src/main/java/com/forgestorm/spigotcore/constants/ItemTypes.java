package com.forgestorm.spigotcore.constants;

public enum ItemTypes {

    ARMOR(FilePaths.ITEMS_ARMOR),
    FOOD(FilePaths.ITEMS_FOOD),
    KITS(FilePaths.ITEMS_KITS),
    RECIPES_CRAFTING(FilePaths.ITEMS_CRAFTING_RECIPES),
    RECIPES_FINISHED(FilePaths.ITEMS_RECIPES_FINISHED),
    INGREDIENTS(FilePaths.ITEMS_INGREDIENTS),
    MENU(FilePaths.ITEMS_MENU),
    VANITY(FilePaths.ITEMS_VANITY),
    WEAPON(FilePaths.ITEMS_WEAPON);

    private final FilePaths path;

    ItemTypes(FilePaths path) {
        this.path = path;
	}
	
	public FilePaths getFilePaths() {
		return path;
	}
}
