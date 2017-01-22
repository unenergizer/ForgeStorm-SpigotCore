package com.forgestorm.spigotcore.constants;

public enum FilePaths {
	
	
	//RPG CODE
	LOOT_TABLE_CHEST("loot/Chest.yml"),
	LOOT_TABLE_MOBS("loot/Monster.yml"),
	
	SETTINGS("settings.yml"),

	SOUND("sounds"),
	
	WORLD_PLAYER_DEFAULT("world/emptyworld"),
	
	//ORIGINAL
	CHAT_ICONS("resources/chaticons/"),
	CITIZENS("npc/citizens.yml"),
	ENTITY_SPAWNER("entity/EntitySpawner.yml"),
	ENTITY_TYPE("entity/EntityType.yml"),
	GAMES_DRAGONEGG("games/dragoneggteleport.yml"),
	GAMES_PARKOUR("games/parkour.yml"),
	ITEM_CRAFTING_RECIPES("items/ItemCraftingRecipes.yml"),
	ITEMS_INGREDIENTS("items/Ingredients.yml"),
	ITEMS_KITS("items/Kits.yml"),
	ITEMS_MENU("items/Menu.yml"),
	ITEMS_RECIPES_FINISHED("items/FinishedRecipes.yml"),
	ITEMS_RECIPES_CRAFTING("items/ItemCraftingRecipes.yml"),
	ITEMS_VANITY("items/Vanity.yml"),
	TOOL_INFORMATION("professions/tools.yml"),
	HELP_TUTORIAL("help/tutorial.yml"),
	HELP_TRACKER("help/trackingLocations.yml"),
	WORLD_ANIMATE_ANIMATIONS("animations/animation.yml"),
	WORLD_ANIMATE_SCHEMATIC("animations/schematics/");
	
	private final String filePath;
	
	//Constructor
	FilePaths(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * Sends a string representation of the enumerator item.
	 */
	public String toString() {
		String prefix = "plugins/FS-SpigotCore/";
		return prefix + filePath;
	}
}
