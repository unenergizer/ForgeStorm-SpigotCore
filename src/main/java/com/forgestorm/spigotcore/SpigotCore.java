package com.forgestorm.spigotcore;

import com.forgestorm.spigotcore.bungeecord.BungeeCord;
import com.forgestorm.spigotcore.chestloot.ChestLoot;
import com.forgestorm.spigotcore.citizens.CitizenManager;
import com.forgestorm.spigotcore.commands.Admin;
import com.forgestorm.spigotcore.commands.Compass;
import com.forgestorm.spigotcore.commands.Creative;
import com.forgestorm.spigotcore.commands.LanternCommand;
import com.forgestorm.spigotcore.commands.MenuHelp;
import com.forgestorm.spigotcore.commands.MenuMain;
import com.forgestorm.spigotcore.commands.MenuSettings;
import com.forgestorm.spigotcore.commands.Mod;
import com.forgestorm.spigotcore.commands.Money;
import com.forgestorm.spigotcore.commands.PlayTime;
import com.forgestorm.spigotcore.commands.Roll;
import com.forgestorm.spigotcore.commands.SafeStop;
import com.forgestorm.spigotcore.commands.Tutorial;
import com.forgestorm.spigotcore.commands.WorldAnimate;
import com.forgestorm.spigotcore.database.MongoDatabaseManager;
import com.forgestorm.spigotcore.games.dragoneggtp.DragonEggTeleport;
import com.forgestorm.spigotcore.help.AnimatedTutorial;
import com.forgestorm.spigotcore.help.LocationTrackingManager;
import com.forgestorm.spigotcore.hiddenpaths.HiddenPaths;
import com.forgestorm.spigotcore.listeners.PlayerInteract;
import com.forgestorm.spigotcore.menus.GameSelectionMenu;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.menus.MenuListener;
import com.forgestorm.spigotcore.player.PlayerManager;
import com.forgestorm.spigotcore.player.SetupNetworkPlayer;
import com.forgestorm.spigotcore.sound.InventorySounds;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import com.forgestorm.spigotcore.util.item.RecipeManager;
import com.forgestorm.spigotcore.util.player.DeletePlayerFiles;
import com.forgestorm.spigotcore.util.scoreboard.ScoreboardManager;
import com.forgestorm.spigotcore.world.BlockHolograms;
import com.forgestorm.spigotcore.world.BlockRegenerationManager;
import com.forgestorm.spigotcore.world.WorldSettings;
import com.forgestorm.spigotcore.world.animate.Lantern;
import com.forgestorm.spigotcore.world.animate.WorldAnimator;
import com.forgestorm.spigotcore.world.animate.WorldTimer;
import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

@Getter
public class SpigotCore extends JavaPlugin {

    // API
    private TitleManagerAPI titleManagerAPI;

    // Local
    private BlockRegenerationManager blockRegen;
    private BungeeCord bungeecord;
    private CitizenManager citizenManager;
    private DragonEggTeleport dragonEggTP;
    private ItemGenerator itemGen;
    private LocationTrackingManager locationTrackingManager;
    private MongoDatabaseManager profileManager;
    private SetupNetworkPlayer setupNetworkPlayer;
    private PlayerManager playerManager;
    private RecipeManager recipeManager;
    private AnimatedTutorial animatedTutorial;
    private ScoreboardManager scoreboardManager;
    private WorldTimer worldTimer;
    private WorldAnimator worldAnimator;
    private BlockHolograms blockHolograms;
    private Lantern lantern;
    private ChestLoot chestLoot;
    private HiddenPaths hiddenPaths;
    private MenuListener menuListener;
    private InventorySounds inventorySounds;
    private WorldSettings worldSettings;

    // Menus
    private Menu gameSelectionMenu;

    // Stats
    private long startTime;

    @Override
    public void onEnable() {

        // Delete any existing player files.
        new DeletePlayerFiles(this).deleteSaveDirectory();

        // Continue setup
        loadClasses();
        registerSpigotListeners();
        registerCommands();
        registerBungeecordChannels();
        registerMenus();

        // Set stats
        startTime = System.currentTimeMillis() / 1000;
    }

    @Override
    public void onDisable() {
        // Save All Players
//        for (Player player : Bukkit.getOnlinePlayers()) {
//            new RemoveNetworkPlayer(this, player, true);
//        }

        // Disable Classes
        profileManager.onDisable();
        playerManager.onDisable();
        blockRegen.onDisable();
        citizenManager.onDisable();
        dragonEggTP.onDisable();
        locationTrackingManager.onDisable();
        blockHolograms.onDisable();
        lantern.onDisable();
        chestLoot.onDisable();
    }

    private void loadClasses() {
        //Load API
        titleManagerAPI = (TitleManagerAPI) Bukkit.getServer().getPluginManager().getPlugin("TitleManager");

        //Load classes (do not rearrange)
        profileManager = new MongoDatabaseManager(
                this,
                "localhost",
                27017,
                "fsAdmin",
                "fsPassword",
                "minecraft");
        setupNetworkPlayer = new SetupNetworkPlayer(this);
        bungeecord = new BungeeCord(this);
        worldAnimator = new WorldAnimator(this);
        worldTimer = new WorldTimer(this);
        blockRegen = new BlockRegenerationManager();
        itemGen = new ItemGenerator();
        animatedTutorial = new AnimatedTutorial(this);
        dragonEggTP = new DragonEggTeleport(this);
        recipeManager = new RecipeManager(this);
        locationTrackingManager = new LocationTrackingManager(this);
        scoreboardManager = new ScoreboardManager(this);
        citizenManager = new CitizenManager(this);
        blockHolograms = new BlockHolograms(this);
        lantern = new Lantern(this);
        chestLoot = new ChestLoot(this);
        hiddenPaths = new HiddenPaths(this);
        menuListener = new MenuListener(this);
        inventorySounds = new InventorySounds(this);
        playerManager = new PlayerManager(this);
        worldSettings = new WorldSettings(this);

        //Run threads
        profileManager.runTaskTimerAsynchronously(this, 0, 1);
        setupNetworkPlayer.runTaskTimer(this, 0, 1);
        citizenManager.runTaskTimer(this, 0, 5);
        worldTimer.runTaskTimer(this, 0, 1);
        blockRegen.runTaskTimer(this, 0, 20);
        locationTrackingManager.runTaskTimerAsynchronously(this, 0, 5);
        chestLoot.runTaskTimer(this, 0, 20);
    }

    private void registerCommands() {
        getCommand("roll").setExecutor(new Roll());
        getCommand("admin").setExecutor(new Admin(this));
        getCommand("creative").setExecutor(new Creative(this));
        getCommand("lantern").setExecutor(new LanternCommand(this));
        getCommand("mod").setExecutor(new Mod(this));
        getCommand("money").setExecutor(new Money(this));
        getCommand("playtime").setExecutor(new PlayTime(this));
        getCommand("roll").setExecutor(new Roll());
        getCommand("safestop").setExecutor(new SafeStop(this));
        getCommand("tutorial").setExecutor(new Tutorial(this));
        getCommand("worldanimate").setExecutor(new WorldAnimate(this));

        //Menu commands
        getCommand("compass").setExecutor(new Compass(this));
        getCommand("help").setExecutor(new MenuHelp(this));
        getCommand("mainmenu").setExecutor(new MenuMain(this));
        getCommand("settings").setExecutor(new MenuSettings(this));
    }

    /**
     * Register event listeners.
     * TODO: Refactor these listeners into more appropriate classes.
     */
    private void registerSpigotListeners() {
        PluginManager pm = Bukkit.getPluginManager();

        // Registering Bukkit Events
        pm.registerEvents(new PlayerInteract(this), this);
    }

    /**
     * Register Bungeecord channels.
     */
    private void registerBungeecordChannels() {
        Messenger messenger = getServer().getMessenger();

        messenger.registerIncomingPluginChannel(this, "BungeeCord", bungeecord);
        messenger.registerOutgoingPluginChannel(this, "BungeeCord");
    }

    /**
     * Register universal menus.
     */
    private void registerMenus() {
        gameSelectionMenu = new GameSelectionMenu(this);
    }
}
