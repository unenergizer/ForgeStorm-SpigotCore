package com.forgestorm.spigotcore.menus;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.TryCraftingRecipe;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CraftingMenu extends Menu {

    private final SpigotCore plugin;
    private final String RECIPE;
    private final int[] playerSlots = {
            27, 28, 29, 30,
			36, 37, 38, 39,
			45, 46, 47, 48
	};
    private List<ItemStack> requiredItems = new ArrayList<>();
    private boolean successfulCraft = false;

    public CraftingMenu(SpigotCore plugin, String recipeName) {
        super(plugin);
        this.plugin = plugin;
        RECIPE = recipeName;
        init("Crafting Menu", 6);
        addRecipeItems();
		makeMenuItems();
	}

	/**
	 * Test for a match between the crafting slots
	 * that the player puts their items into and
	 * the items required for testing. Returns true if
	 * they match or false if they do not
	 * 
	 * @return The result of them matching
	 */
	public boolean testForMatch() {
		Inventory inventory = getInventory();
		List<ItemStack> playerItems = new ArrayList<>();
		
		//Build a list of player inserted items.
		for (int playerSlot : playerSlots) {
			ItemStack item = inventory.getItem(playerSlot);
			if (item != null) playerItems.add(item);
		}
		
		boolean tof = true;
		//Check for required items.
		for (ItemStack item : requiredItems) {
			if (!playerItems.contains(item)) {
				tof = false;
			}
		}
		
		//Check for list size.
		if (requiredItems.size() != playerItems.size()) tof = false;
		
		return successfulCraft = tof;
	}

	@Override
	protected void makeMenuItems() {
        ItemGenerator itemGen = plugin.getItemGen();
        ItemTypes type = ItemTypes.MENU;
        String successRecipe = plugin.getRecipeManager().getMakes(RECIPE);

        ItemStack filler = itemGen.generateItem("filler", type);
        ItemStack create = itemGen.generateItem("create_button", type);
        ItemStack exit = itemGen.generateItem("exit_button", type);
		ItemStack instructions = itemGen.generateItem("crafting_instruction", type);
		ItemStack redPanel = itemGen.generateItem("craft_red_pane_instruction", type);
		ItemStack purplePanel = itemGen.generateItem("craft_purple_pane_instruction", type);
		
		String fillBoard[] = {
				"green", "green", "filler", "filler", "book", "filler", "filler", "exit", "exit",
				"green", "green", "filler", "filler", "filler", "filler", "filler", "exit", "exit",
				"red", "red", "red", "red", "filler", "purple", "purple", "purple", "purple",
		};

		for (int i = 0; i < fillBoard.length; i++) {
			switch (fillBoard[i]) {
                case "green":
                    setItem(create, i, new TryCraftingRecipe(plugin, successRecipe));
                    break;
                case "filler":
                    setItem(filler, i);
                    break;
                case "book":
                    setItem(instructions, i);
                    break;
                case "red": setItem(redPanel, i); break;
			case "purple": setItem(purplePanel, i); break;
                case "exit":
                    setItem(exit, i, new Exit(plugin));
                    break;
            }
        }

        setItem(filler, 31);
		setItem(filler, 40);
		setItem(filler, 49);
		
		//Set clickable slots
		for (int playerSlot : playerSlots) setMovableSlot(playerSlot);
	}
	
	private void addRecipeItems() {
        List<ItemStack> recipeItems = plugin.getRecipeManager().getRecipeIngredients(RECIPE);
        requiredItems = plugin.getRecipeManager().getRecipeIngredients(RECIPE);

        //These are the slots used by the server.
        int craftingSlots[] = {
                32, 33, 34, 35,
				41, 42, 43, 44,
				50, 51, 52, 53
		};

		for (int craftingSlot : craftingSlots) {
			if (recipeItems.isEmpty()) return;
			setItem(recipeItems.get(0), craftingSlot);
			recipeItems.remove(0);
		}
	}
	
	/**
	 * Returns the items the player put into
	 * the crafting inventory if the crafting 
	 * menu was closed.
	 * 
	 * @param player The player who is closing their inventory
	 */
	public void onClose(Player player) {
		
		if (successfulCraft) return;

		for (int j : playerSlots) {
			Inventory inventory = getInventory();
			ItemStack item = inventory.getItem(j);

			if (item != null) {
				Inventory playerInventory = player.getInventory();
				if (playerInventory.firstEmpty() != -1) {
					playerInventory.addItem(item);
				} else {
					player.getWorld().dropItemNaturally(player.getLocation(), item);
				}
			}

		}
	}
}
