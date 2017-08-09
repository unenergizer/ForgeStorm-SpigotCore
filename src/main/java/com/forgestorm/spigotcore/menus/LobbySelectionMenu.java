package com.forgestorm.spigotcore.menus;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.ItemLores;
import com.forgestorm.spigotcore.constants.ItemTypes;
import com.forgestorm.spigotcore.menus.actions.ClickAction;
import com.forgestorm.spigotcore.menus.actions.ConnectToBungeeServer;
import com.forgestorm.spigotcore.menus.actions.Exit;
import com.forgestorm.spigotcore.util.item.ItemBuilder;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class LobbySelectionMenu extends Menu {

    private final SpigotCore plugin;

    private ItemStack lobby;
    private int lobbyPlayers;
    private int frame = 1;

    public LobbySelectionMenu(SpigotCore plugin) {
        super(plugin);
        this.plugin = plugin;
        init("Lobby Selection Menu", 1);
        makeMenuItems();
        updateMenu();
    }

    @Override
    protected void makeMenuItems() {
        ItemTypes type = ItemTypes.MENU;
        ItemGenerator itemGen = plugin.getItemGen();
        ItemStack backButton, exitButton;

        backButton = itemGen.generateItem("back_button", type);
        exitButton = itemGen.generateItem("exit_button", type);

        setItem(backButton, 7, MainMenu.class);
        setItem(exitButton, 8, new Exit(plugin));
    }

    /**
     * This will create new items.
     */
    private void updateItems() {

        //Create the items
        plugin.getBungeecord().getPlayerCount("hub-01");
        lobby = createItem(
                ItemLores.LOBBY_TITLE.toString().replace("%s", "1"),
                new String[]{
                        ItemLores.LOBBY_LORE_01.toString(),
                        ItemLores.LOBBY_LORE_02.toString(),
                        ItemLores.LOBBY_LORE_03.toString()
                },
                Material.BOOKSHELF,
                lobbyPlayers,
                1);
    }

    /**
     * This will continuously update the players server selector menu.
     * The updates are for updating the amount of players on each server and other things.
     */
    private void updateMenu() {
        final int UPDATE_TIME = 1;

        ClickAction lobbyAction = new ConnectToBungeeServer(plugin, "hub-01");

        //Start the thread to update the items.
        new BukkitRunnable() {

            @Override
            public void run() {

                //Don't update anything unless a player is online.
                if (Bukkit.getOnlinePlayers().size() >= 1) {

                    //Update the current frame.
                    //This is for animated item descriptions.
                    if (frame == 1) {
                        frame++;
                    } else {
                        frame--;
                    }

                    //Update the Items
                    updateItems();

                    //Update Menu
                    setItem(lobby, 0, lobbyAction);
                }
            }
        }.runTaskTimer(plugin, 0, 20 * UPDATE_TIME);
    }

    /**
     * This will create a menu item.
     *
     * @param title    The title of the menu item (usually a server name).
     * @param lore     The description of the menu item.
     * @param material The icon that will represent the menu item.
     * @return Returns a menu item ready for display to the user.
     */
    private ItemStack createItem(String title, String[] lore, Material material, int playersOnline, int amount) {
        List<String> lores = new ArrayList<>();

        lores.add("");        //Blank line
        Collections.addAll(lores, lore); //Add actual server lore.
        lores.add("");        //Blank line

        //Connect now message
        if (frame == 1) {
            lores.add(ItemLores.CONNECT_FRAME_1.toString());
        } else {
            lores.add(ItemLores.CONNECT_FRAME_2.toString());
        }

        //Show players online.
        lores.add(ItemLores.PLAYERS_ONLINE.toString().replace("%s", Integer.toString(playersOnline)));

        return new ItemBuilder(material).setTitle(title).addLores(lores).setAmount(amount).build(true);
    }
}
