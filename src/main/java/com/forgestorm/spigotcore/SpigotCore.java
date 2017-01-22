package com.forgestorm.spigotcore;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

import com.forgestorm.spigotcore.bungeecord.BungeeCord;
import com.forgestorm.spigotcore.commands.Admin;
import com.forgestorm.spigotcore.commands.Creative;
import com.forgestorm.spigotcore.commands.Hub;
import com.forgestorm.spigotcore.commands.Item;
import com.forgestorm.spigotcore.commands.Lobby;
import com.forgestorm.spigotcore.commands.MenuHelp;
import com.forgestorm.spigotcore.commands.MenuMain;
import com.forgestorm.spigotcore.commands.MenuSettings;
import com.forgestorm.spigotcore.commands.Mod;
import com.forgestorm.spigotcore.commands.Money;
import com.forgestorm.spigotcore.commands.Monster;
import com.forgestorm.spigotcore.commands.Mount;
import com.forgestorm.spigotcore.commands.PlayTime;
import com.forgestorm.spigotcore.commands.Realm;
import com.forgestorm.spigotcore.commands.Roll;
import com.forgestorm.spigotcore.commands.Spawner;
import com.forgestorm.spigotcore.commands.WorldAnimate;
import com.forgestorm.spigotcore.entity.EntityManager;
import com.forgestorm.spigotcore.entity.EntityRespawner;
import com.forgestorm.spigotcore.entity.EntitySpawnerManager;
import com.forgestorm.spigotcore.entity.MountManager;
import com.forgestorm.spigotcore.entity.human.CitizenManager;
import com.forgestorm.spigotcore.entity.human.PlayerManager;
import com.forgestorm.spigotcore.games.dragoneggtp.DragonEggTeleport;
import com.forgestorm.spigotcore.help.LocationTrackingManager;
import com.forgestorm.spigotcore.help.Tutorial;
import com.forgestorm.spigotcore.listeners.AsyncPlayerChat;
import com.forgestorm.spigotcore.listeners.BlockBreak;
import com.forgestorm.spigotcore.listeners.BlockDamage;
import com.forgestorm.spigotcore.listeners.BlockIgnite;
import com.forgestorm.spigotcore.listeners.BlockPhysics;
import com.forgestorm.spigotcore.listeners.BlockPlace;
import com.forgestorm.spigotcore.listeners.ChunkLoad;
import com.forgestorm.spigotcore.listeners.ChunkUnload;
import com.forgestorm.spigotcore.listeners.EntityChangeBlock;
import com.forgestorm.spigotcore.listeners.EntityCombust;
import com.forgestorm.spigotcore.listeners.EntityDamage;
import com.forgestorm.spigotcore.listeners.EntityDamageByEntity;
import com.forgestorm.spigotcore.listeners.EntityDeath;
import com.forgestorm.spigotcore.listeners.EntityExplode;
import com.forgestorm.spigotcore.listeners.InventoryClick;
import com.forgestorm.spigotcore.listeners.InventoryClose;
import com.forgestorm.spigotcore.listeners.InventoryDrag;
import com.forgestorm.spigotcore.listeners.InventoryOpen;
import com.forgestorm.spigotcore.listeners.PlayerDropItem;
import com.forgestorm.spigotcore.listeners.PlayerInteract;
import com.forgestorm.spigotcore.listeners.PlayerInteractEntity;
import com.forgestorm.spigotcore.listeners.PlayerItemHeld;
import com.forgestorm.spigotcore.listeners.PlayerJoin;
import com.forgestorm.spigotcore.listeners.PlayerKick;
import com.forgestorm.spigotcore.listeners.PlayerMove;
import com.forgestorm.spigotcore.listeners.PlayerPickupItem;
import com.forgestorm.spigotcore.listeners.PlayerPortal;
import com.forgestorm.spigotcore.listeners.PlayerQuit;
import com.forgestorm.spigotcore.listeners.WeatherChange;
import com.forgestorm.spigotcore.menus.GameSelectionMenu;
import com.forgestorm.spigotcore.menus.Menu;
import com.forgestorm.spigotcore.profession.Profession;
import com.forgestorm.spigotcore.redis.RedisProfileManager;
import com.forgestorm.spigotcore.redis.RedisService;
import com.forgestorm.spigotcore.util.item.ItemGenerator;
import com.forgestorm.spigotcore.util.item.RecipeManager;
import com.forgestorm.spigotcore.util.player.DeletePlayerFiles;
import com.forgestorm.spigotcore.util.scoreboard.PuhaScoreboard;
import com.forgestorm.spigotcore.util.scoreboard.ScoreboardManager;
import com.forgestorm.spigotcore.world.BlockRegenerationManager;
import com.forgestorm.spigotcore.world.ChunkManager;
import com.forgestorm.spigotcore.world.animate.WorldAnimator;
import com.forgestorm.spigotcore.world.animate.WorldTimer;
import com.forgestorm.spigotcore.world.instance.RealmManager;

import io.puharesource.mc.titlemanager.api.v2.TitleManagerAPI;
import lombok.Getter;

@Getter
public class SpigotCore extends JavaPlugin {

	//API's
	private TitleManagerAPI titleManagerAPI;

	//Local
	private BlockRegenerationManager blockRegen;
	private BungeeCord bungeecord;
	private ChunkManager chunkManager;
	private CitizenManager citizenManager;
	private DragonEggTeleport dragonEggTP;
	private EntityManager entityManager;
	private EntityRespawner entityRespawner;
	private EntitySpawnerManager entitySpawnerManager;
	private ItemGenerator itemGen;
	private LocationTrackingManager locationTrackingManager;
	private Profession profession;
	private RedisProfileManager profileManager;
	private RecipeManager recipeManager;
	private RedisService redisService;
	private Tutorial tutorial;
	private ScoreboardManager scoreboardManager;
	private WorldTimer worldTimer;
	private WorldAnimator worldAnimator;
	private MountManager mountManager;
	private PlayerManager playerManager;
	private RealmManager realmManager;
	private PuhaScoreboard puhaScoreboard;

	//Menus
	private Menu gameSelectionMenu;

	@Override
	public void onEnable() {

		//Delete any existing player files.
		new DeletePlayerFiles(this).deleteSaveDirectory();
		
		//Continue setup
		loadClasses();
		registerSpigotListeners();
		registerCommands();
		registerBungeecordChannels();
		registerMenus();
	}

	@Override
	public void onDisable() {
		realmManager.disable();
		profileManager.disable();
		redisService.disable();		
		blockRegen.disable();
		citizenManager.disable();
		dragonEggTP.disable();
		locationTrackingManager.disable();
		puhaScoreboard.disable();
	}

	private void loadClasses() {
		//Load API's
		titleManagerAPI = (TitleManagerAPI) Bukkit.getServer().getPluginManager().getPlugin("TitleManager");

		//Load classes (do not rearrange)
		redisService = new RedisService("localhost", "6379");
		profileManager = new RedisProfileManager(this);
		bungeecord = new BungeeCord(this);
		entityManager = new EntityManager();
		entityRespawner = new EntityRespawner(this);
		entitySpawnerManager = new EntitySpawnerManager(this);
		chunkManager = new ChunkManager(this);
		worldAnimator = new WorldAnimator(this);
		worldTimer = new WorldTimer(this);
		blockRegen = new BlockRegenerationManager(this);
		profession = new Profession(this);
		itemGen = new ItemGenerator();
		tutorial = new Tutorial(this);
		citizenManager = new CitizenManager(this);
		dragonEggTP = new DragonEggTeleport(this);
		recipeManager = new RecipeManager(this);
		locationTrackingManager = new LocationTrackingManager(this);
		scoreboardManager = new ScoreboardManager(this);
		realmManager = new RealmManager(this);
		mountManager = new MountManager();
		playerManager = new PlayerManager(this);
		puhaScoreboard = new PuhaScoreboard(this);

		//Run threads
		citizenManager.runTaskTimer(this, 0, 5);
		worldTimer.runTaskTimer(this, 0, 1);
		locationTrackingManager.runTaskTimerAsynchronously(this, 0, 5);
		realmManager.runTaskTimerAsynchronously(this, 0, 20);
	}

	private void registerCommands() {

		//RPG COMMANDS
		getCommand("item").setExecutor(new Item());
		getCommand("monster").setExecutor(new Monster(this));
		getCommand("mount").setExecutor(new Mount(this));
		getCommand("realm").setExecutor(new Realm(this));
		getCommand("roll").setExecutor(new Roll());
		getCommand("spawner").setExecutor(new Spawner(this));

		//ORIGINAL COMMANDS
		getCommand("admin").setExecutor(new Admin(this));
		getCommand("creative").setExecutor(new Creative(this));
		getCommand("hub").setExecutor(new Hub(this));
		getCommand("lobby").setExecutor(new Lobby(this));
		getCommand("mod").setExecutor(new Mod(this));
		getCommand("money").setExecutor(new Money(this));
		getCommand("playtime").setExecutor(new PlayTime(this));
		getCommand("roll").setExecutor(new Roll());
		getCommand("wa").setExecutor(new WorldAnimate(this));

		//Menu commands
		getCommand("help").setExecutor(new MenuHelp(this));
		getCommand("mainmenu").setExecutor(new MenuMain(this));
		getCommand("settings").setExecutor(new MenuSettings(this));
	}

	private void registerSpigotListeners() {
		PluginManager pm = Bukkit.getPluginManager();

		pm.registerEvents(new AsyncPlayerChat(this), this);
		pm.registerEvents(new BlockBreak(this), this);
		pm.registerEvents(new BlockDamage(this), this);
		pm.registerEvents(new BlockIgnite(), this);
		pm.registerEvents(new BlockPhysics(), this);
		pm.registerEvents(new BlockPlace(this), this);
		pm.registerEvents(new ChunkLoad(this), this);
		pm.registerEvents(new ChunkUnload(this), this);
		pm.registerEvents(new EntityChangeBlock(), this);
		pm.registerEvents(new EntityCombust(), this);
		pm.registerEvents(new EntityDamage(this), this);
		pm.registerEvents(new EntityDamageByEntity(this), this);
		pm.registerEvents(new EntityDeath(), this);
		pm.registerEvents(new EntityExplode(this), this);
		pm.registerEvents(new InventoryClick(this), this);
		pm.registerEvents(new InventoryClose(this), this);
		pm.registerEvents(new InventoryDrag(this), this);
		pm.registerEvents(new InventoryOpen(this), this);
		pm.registerEvents(new PlayerDropItem(), this);
		pm.registerEvents(new PlayerInteract(this), this);
		pm.registerEvents(new PlayerInteractEntity(this), this);
		pm.registerEvents(new PlayerItemHeld(this), this);
		pm.registerEvents(new PlayerJoin(this), this);
		pm.registerEvents(new PlayerKick(this), this);
		pm.registerEvents(new PlayerMove(this), this);
		pm.registerEvents(new PlayerPickupItem(), this);
		pm.registerEvents(new PlayerPortal(this), this);
		pm.registerEvents(new PlayerQuit(this), this);
		pm.registerEvents(new WeatherChange(this), this);
	}

	private void registerBungeecordChannels() {
		Messenger messenger = getServer().getMessenger();

		messenger.registerIncomingPluginChannel(this, "BungeeCord", bungeecord);
		messenger.registerOutgoingPluginChannel(this, "BungeeCord");
	}

	private void registerMenus() {
		gameSelectionMenu = new GameSelectionMenu(this);
	}
}
