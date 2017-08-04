package com.forgestorm.spigotcore.constants;

public enum FilePaths {

    SETTINGS("settings.yml"),

	SOUND("sounds"),
	
	WORLD_PLAYER_DEFAULT("world/emptyworld"),
	
	//ORIGINAL
	CHAT_ICONS("resources/chaticons/"),
    CHEST_LOOT("lootchests/ChestLocations.yml"),
    CITIZENS("npc/citizens.yml"),
    BLOCK_HOLOGRAMS("world/blockholograms.yml"),
    ENTITY_SPAWNER("entity/EntitySpawner.yml"),
    ENTITY_TYPE("entity/EntityType.yml"),
    GAMES_DRAGONEGG("games/dragoneggteleport.yml"),
	GAMES_PARKOUR("games/parkour.yml"),
    ITEMS_ARMOR("items/Armor.yml"),
    ITEMS_CRAFTING_RECIPES("items/ItemCraftingRecipes.yml"),
    ITEMS_FOOD("items/Food.yml"),
    ITEMS_INGREDIENTS("items/Ingredients.yml"),
    ITEMS_KITS("items/Kits.yml"),
    ITEMS_MENU("items/Menu.yml"),
	ITEMS_RECIPES_FINISHED("items/FinishedRecipes.yml"),
	ITEMS_RECIPES_CRAFTING("items/ItemCraftingRecipes.yml"),
	ITEMS_VANITY("items/Vanity.yml"),
    ITEMS_WEAPON("items/Weapon.yml"),
    PROFESSION_FARMING("professions/Farming.yml"),
    PROFESSION_FISHING("professions/Fishing.yml"),
    PROFESSION_COOKING_AND_SMELTING("professions/CookingAndSmelting.yml"),
    PROFESSION_WOOD_CUTTING("professions/WoodCutting.yml"),
    PROFESSION_MINING("professions/Mining.yml"),
    HELP_TUTORIAL("help/tutorial.yml"),
    HELP_TRACKER("help/trackingLocations.yml"),
    WORLD_ANIMATE_ANIMATIONS("animations/animation.yml"),
    WORLD_ANIMATE_LANTERNS("animations/lanterns.yml"),
    WORLD_ANIMATE_SCHEMATIC("animations/schematics/"),

	//Hub
	BOSS_BAR_ANNOUNCEMENTS("language/BossBarAnnouncements.yml"),
	GAME_TIP_ANNOUNCER("language/tips.yml");
	
	private final String filePath;
	
	//Constructor
	FilePaths(String filePath) {
		this.filePath = filePath;
	}
	
	/**
	 * Sends a string representation of the enumerator item.
	 */
    public String toString() {
        return "plugins/FS-SpigotCore/" + filePath;
    }
}
