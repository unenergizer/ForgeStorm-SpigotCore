package com.forgestorm.spigotcore.constants;

public enum ItemTypes {

	KITS(FilePaths.ITEMS_KITS),
	RECIPES_CRAFTING(FilePaths.ITEM_CRAFTING_RECIPES),
	RECIPES_FINISHED(FilePaths.ITEMS_RECIPES_FINISHED),
	INGREDIENTS(FilePaths.ITEMS_INGREDIENTS),
	MENU(FilePaths.ITEMS_MENU),
	VANITY(FilePaths.ITEMS_VANITY);
	
	private final FilePaths path;
	
	ItemTypes(FilePaths path) {
		this.path = path;
	}
	
	public FilePaths getFilePaths() {
		return path;
	}
}
