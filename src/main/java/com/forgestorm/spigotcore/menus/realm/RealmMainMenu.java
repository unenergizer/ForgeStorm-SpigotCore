package com.forgestorm.spigotcore.menus.realm;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.MainMenu;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.menus.actions.FeatureComingSoon;
import com.forgestorm.spigotcore.menus.actions.RunPlayerCommand;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import org.bukkit.inventory.ItemStack;

public class RealmMainMenu extends Menu {

    private final SpigotCore plugin;

    public RealmMainMenu(SpigotCore plugin) {
        super(plugin);
        this.plugin = plugin;
        init("Realm Main Menu", 1);
        makeMenuItems();
    }

    @Override
    protected void makeMenuItems() {
        ItemTypes type = ItemTypes.MENU;
        ItemGenerator itemGen = plugin.getItemGen();

        ItemStack shop, upgrade, help, reset, backButton, exitButton;

        shop = itemGen.generateItem("realm_shop", type);
        upgrade = itemGen.generateItem("realm_upgrade", type);
        help = itemGen.generateItem("realm_help", type);
        reset = itemGen.generateItem("realm_reset", type);

        backButton = itemGen.generateItem("back_button", type);
        exitButton = itemGen.generateItem("exit_button", type);

        setItem(shop, 0, RealmShopPage1.class);
        setItem(upgrade, 1, new FeatureComingSoon());
        setItem(help, 2, new RunPlayerCommand(plugin, "realm help", 0));
        setItem(reset, 3, new RunPlayerCommand(plugin, "realm reset", 0));

        setItem(backButton, 7, MainMenu.class);
        setItem(exitButton, 8, new Exit(plugin));
    }
}
