package com.forgestorm.spigotcore.citizens;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.CommonSounds;
import com.forgestorm.spigotcore.constants.FilePaths;
import com.forgestorm.spigotcore.constants.SpigotCoreMessages;
import com.forgestorm.spigotcore.menus.GameSelectionMenu;
import com.forgestorm.spigotcore.menus.help.LinksMenu;
import com.forgestorm.spigotcore.menus.help.TutorialMenu;
import com.forgestorm.spigotcore.menus.merchant.BartenderMenu;
import com.forgestorm.spigotcore.menus.merchant.BoatMenu;
import com.forgestorm.spigotcore.menus.merchant.MerchantFarmingMenu;
import com.forgestorm.spigotcore.menus.merchant.MerchantFishingMenu;
import com.forgestorm.spigotcore.menus.merchant.MerchantMenu;
import com.forgestorm.spigotcore.menus.merchant.MerchantMiningMenu;
import com.forgestorm.spigotcore.menus.merchant.MerchantWoodCuttingMenu;
import com.forgestorm.spigotcore.menus.profession.CookingTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.FarmingTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.FishingTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.MiningTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.SmeltingTrainerMenu;
import com.forgestorm.spigotcore.menus.profession.WoodCuttingTrainerMenu;
import com.forgestorm.spigotcore.util.display.Hologram;
import com.forgestorm.spigotcore.util.item.NPCSkullBuilder;
import com.forgestorm.spigotcore.util.logger.ColorLogger;
import com.forgestorm.spigotcore.util.math.RandomChance;
import com.forgestorm.spigotcore.util.text.CenterChatText;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class manages what happens when a NPC is clicked.
 * It also puts a hologram above the NPC's head.
 */
public class CitizenManager extends BukkitRunnable implements Listener {

    private final static int MAX_FRAMES = 4;
    private final static int MAX_CIRLCE_FRAMES = 3;
    private final SpigotCore plugin;
    private final String filePath;
    //Holograms
    private final ArrayList<Hologram> holograms = new ArrayList<>();
    private final ArrayList<Hologram> animatedHologram = new ArrayList<>();
    private final ArrayList<Hologram> animatedHologramRightClick = new ArrayList<>();
    @Getter
    private final Map<Location, ItemStack> npcMenuLocations = new HashMap<>();
    private final ResetTimer resetTimer;
    private File file;
    private FileConfiguration config;
    private int frame = 0;
    private int circleFrame = 0;

    public CitizenManager(SpigotCore plugin) {
        this.plugin = plugin;
        resetTimer = new ResetTimer();
        resetTimer.runTaskTimerAsynchronously(plugin, 0, 1);

        filePath = FilePaths.CITIZENS.toString();

        //If citizens configuration does not exist, create the file. Otherwise lets load the file.
        if (!(new File(filePath)).exists()) {
            createCitizenConfig();
        } else {
            //lets load the configuration file.
            file = new File(filePath);
            config = YamlConfiguration.loadConfiguration(file);
        }

        //Apply Citizens Configuration after server startup.
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            applyCitizensConfiguration();
            ColorLogger.INFO.printLog("[FSCore] Applied Citizens HP and Holograms.");
        }, 5 * 20L);

        // Register Listeners
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    /**
     * This will disable this class.
     */
    public void onDisable() {
        // Cancel the animate thread.
        cancel();

        // Removes all Holograms
        removeStaticHolograms();
        removeAnimatedHolograms();

        // Unregister Listeners

    }

    @Override
    public void run() {
        if (frame > MAX_FRAMES) {
            frame = 0;
        }
        if (circleFrame > MAX_CIRLCE_FRAMES) {
            circleFrame = 0;
        }

        //Rename holograms
        for (Hologram anAnimatedHologram : animatedHologram) {
            String hologramText = "";
            String circle = "";
            String circleFlipped = "";

            if (circleFrame == 0) {
                circle = "- ";
                circleFlipped = " -";
            } else if (circleFrame == 1) {
                circle = "/ ";
                circleFlipped = " \\";
            } else if (circleFrame == 2) {
                circle = "| ";
                circleFlipped = " |";
            } else if (circleFrame == 3) {
                circle = "\\ ";
                circleFlipped = " /";
            }

            if (frame == 0) {
                hologramText = circle + CitizenType.PLAY_MINIGAMES.getName(ChatColor.RED) + ChatColor.RESET + circleFlipped;
            } else if (frame == 1) {
                hologramText = circle + CitizenType.PLAY_MINIGAMES.getName(ChatColor.YELLOW) + ChatColor.RESET + circleFlipped;
            } else if (frame == 2) {
                hologramText = circle + CitizenType.PLAY_MINIGAMES.getName(ChatColor.GREEN) + ChatColor.RESET + circleFlipped;
            } else if (frame == 3) {
                hologramText = circle + CitizenType.PLAY_MINIGAMES.getName(ChatColor.AQUA) + ChatColor.RESET + circleFlipped;
            } else if (frame == 4) {
                hologramText = circle + CitizenType.PLAY_MINIGAMES.getName(ChatColor.LIGHT_PURPLE) + ChatColor.RESET + circleFlipped;
            }

            //Rename the hologram.
            anAnimatedHologram.getArmorStands().get(0).setCustomName(hologramText);
        }

        frame++;
        circleFrame++;
    }

    /**
     * This will apply HP to a NPC.
     * The HP is displayed under the NPC's name.
     */
    private void applyCitizensConfiguration() {

        //Get all online players.
        for (Entity entity : Bukkit.getWorlds().get(0).getEntities()) {

            //Get all Citizen NPC's.
            if (entity instanceof Player && entity.hasMetadata("NPC")) {
                Player npc = (Player) entity;
                String npcName = npc.getDisplayName();
                CitizenType type = getCitizenType(npcName);

                // Setup Health, Scoreboard, and Titles..
                npc.setMaxHealth(100);
                npc.setHealth(100);
                plugin.getScoreboardManager().setupNPC(npc);

                if (type != CitizenType.NONE) {
                    if (type.equals(CitizenType.PLAY_MINIGAMES)) {
                        Location location = npc.getLocation().add(0, 1.65, 0);
                        String name = getCitizenType(npcName).getName();
                        String rightClick = ChatColor.BOLD + "RIGHT-CLICK";

                        //Setup NPC Hologram
                        Hologram hologram = new Hologram();
                        hologram.createHologram(name, location);
                        animatedHologram.add(hologram);

                        //Add a static, non-flickering Right-Click hologram.
                        Hologram hologramRC = new Hologram();
                        hologramRC.createHologram(rightClick, new Location(location.getWorld(), location.getX(), location.getY() - .3, location.getZ()));
                        animatedHologramRightClick.add(hologramRC);
                    } else {
                        ArrayList<String> hologramText = new ArrayList<>();
                        hologramText.add(getCitizenType(npcName).getName());
                        hologramText.add(ChatColor.BOLD + "RIGHT-CLICK");

                        //Build NPC Head
                        ItemStack skull = new NPCSkullBuilder().createPlayerSkullItem(npcName, type);

                        //Add NPC Tracker Menu Items
                        Location location = npc.getLocation().add(0, 1.65, 0);
                        npcMenuLocations.put(location, skull);

                        Location hologramLocation = npc.getLocation().add(0, 1.65, 0);

                        //Setup NPC Hologram
                        Hologram hologram = new Hologram();
                        hologram.createHologram(hologramText, hologramLocation);
                        //Add hologram to the array list.
                        holograms.add(hologram);
                    }
                }
            }
        }
    }

    private void removeStaticHolograms() {
        //Remove static holograms.
        holograms.forEach(Hologram::removeHolograms);
    }

    private void removeAnimatedHolograms() {
        //Remove animated holograms.
        animatedHologram.forEach(Hologram::removeHolograms);

        //Remove animated holograms.
        animatedHologramRightClick.forEach(Hologram::removeHolograms);
    }

    /**
     * This will trigger when an NPC is left clicked or right clicked.
     *
     * @param player The player that interacted with the NPC.
     * @param npc    The Citizen (created by the Citizen plugin) that was clicked.
     */
    @SuppressWarnings("deprecation")
    public void onCitizenInteract(Player player, Player npc) {
        String npcName = npc.getDisplayName();
        CitizenType type = getCitizenType(npcName);
        Location loc = npc.getLocation().add(0, 2, 0);
        int rand = RandomChance.randomInt(1, 100);
        boolean skippedTutorial = false;

        //Trigger code in appropriate Java Class file.
        switch (type) {
            case AUCTIONEER:
                break;
            case BANKER:
                break;
            case BARTENDER:
                new BartenderMenu(plugin).open(player);
                break;
            case DIRTY_HOBO:
                break;
            case LOBBY:
                player.chat("/lobby");
                break;
            case MERCHANT:
                player.sendMessage(ChatColor.GREEN + "Merchant coming soon!");
                break;
            case MERCHANT_BOAT:
                new BoatMenu(plugin).open(player);
                break;
            case MERCHANT_FARMING:
                new MerchantFarmingMenu(plugin).open(player);
                break;
            case MERCHANT_FISHING:
                new MerchantFishingMenu(plugin).open(player);
                break;
            case MERCHANT_REALM:
                new MerchantMenu(plugin).open(player);
                break;
            case MERCHANT_MINING:
                new MerchantMiningMenu(plugin).open(player);
                break;
            case MERCHANT_WOOD_CUTTING:
                new MerchantWoodCuttingMenu(plugin).open(player);
                break;
            case PLAY_MINIGAMES:
                new GameSelectionMenu(plugin).open(player);
                break;
            case PROFESSION_COOKING:
                new CookingTrainerMenu(plugin, player, true).open(player);
                break;
            case PROFESSION_FARMING:
                new FarmingTrainerMenu(plugin, player, true).open(player);
                break;
            case PROFESSION_FISHING:
                new FishingTrainerMenu(plugin, player, true).open(player);
                break;
            case PROFESSION_MINING:
                new MiningTrainerMenu(plugin, player, true).open(player);
                break;
            case PROFESSION_SMELTING:
                new SmeltingTrainerMenu(plugin, player, true).open(player);
                break;
            case PROFESSION_WOOD_CUTTING:
                new WoodCuttingTrainerMenu(plugin, player, true).open(player);
                break;
            case SOCIAL_MEDIA:
                new LinksMenu(plugin).open(player);
                break;
            case DISCORD:
                player.sendMessage("");
                player.sendMessage("");
                player.sendMessage(SpigotCoreMessages.BAR_DISCORD.toString());
                player.sendMessage("");
                player.sendMessage(CenterChatText.centerChatMessage(SpigotCoreMessages.DISCORD_INFO_1.toString()));
                player.sendMessage(CenterChatText.centerChatMessage(SpigotCoreMessages.DISCORD_INFO_2.toString()));
                player.sendMessage("");
                player.sendMessage(CenterChatText.centerChatMessage(SpigotCoreMessages.DISCORD_INFO_3.toString()));
                player.sendMessage("");
                player.sendMessage(SpigotCoreMessages.BAR_BOTTOM.toString());
                break;
            case TUTORIAL:
                new TutorialMenu(plugin).open(player);
                break;
            case TUTORIAL_SKIP:
                // Show tutorial skip message.
                skippedTutorial = true;
            case TUTORIAL_EXIT:
                // Teleport the player to the main spawn.
                TutorialExitEvent tutorialExitEvent = new TutorialExitEvent(player);
                Bukkit.getPluginManager().callEvent(tutorialExitEvent);

                // Mark the tutorial as completed.
                plugin.getProfileManager().getProfile(player).setTutorialFinished(true);

                player.sendMessage("");
                if (!skippedTutorial) {
                    // Player finished the tutorial
                    player.sendMessage(ChatColor.GREEN + "You have completed the tutorial! To return to it, type "
                            + ChatColor.YELLOW + "/tutorial");
                    CommonSounds.ACTION_SUCCESS.playSound(player);
                } else {
                    // Player skipped the tutorial
                    player.sendMessage(ChatColor.RED + "You have skipped the tutorial. To return to it, type "
                            + ChatColor.YELLOW + "/tutorial");
                    CommonSounds.ACTION_FAILED.playSound(player);
                }
                break;
            case TUTORIAL_START:
                plugin.getAnimatedTutorial().startTutorial(player, "1");
                break;
            case TUTORIAL_PROFESSION_FARMING:
                plugin.getAnimatedTutorial().startTutorial(player, "2");
                break;
            case TUTORIAL_PROFESSION_FISHING:
                plugin.getAnimatedTutorial().startTutorial(player, "3");
                break;
            case TUTORIAL_PROFESSION_MINING:
                plugin.getAnimatedTutorial().startTutorial(player, "4");
                break;
            case TUTORIAL_PROFESSION_COOKING_SMELTING:
                plugin.getAnimatedTutorial().startTutorial(player, "5");
                break;
            case TUTORIAL_PLAYER_REALM:
                plugin.getAnimatedTutorial().startTutorial(player, "6");
                break;
            case TUTORIAL_CRAFTING:
                plugin.getAnimatedTutorial().startTutorial(player, "7");
                break;
            case TUTORIAL_PROFESSION_WOOD_CUTTING:
                plugin.getAnimatedTutorial().startTutorial(player, "8");
                break;
            case VOTE:
                break;
            case NONE:
            default:
                break;
        }

        //Send a random saying to the player.
        String npcMessage = getNPCMessage(npcName);
        if (npcMessage != null) {
            String fullMsg = ChatColor.GRAY + npcName + ChatColor.DARK_GRAY + ": " + ChatColor.RESET + npcMessage;
            player.sendMessage(fullMsg);
        }

        //Show particle effect.
        for (int i = 0; i <= 5; i++) {
            Bukkit.getWorlds().get(0).spigot().playEffect(loc, Effect.HAPPY_VILLAGER);
        }

        //Player sound.
        if (rand < 25) {
            player.playSound(loc, Sound.ENTITY_VILLAGER_TRADING, .8f, .8f);
        } else if (rand >= 25 && rand < 50) {
            player.playSound(loc, Sound.ENTITY_VILLAGER_AMBIENT, .8f, .8f);
        } else if (rand >= 50 && rand < 75) {
            player.playSound(loc, Sound.ENTITY_VILLAGER_NO, .8f, .8f);
        } else if (rand >= 75) {
            player.playSound(loc, Sound.ENTITY_VILLAGER_YES, .8f, .8f);
        }
    }

    private String getNPCMessage(String npcName) {
        String nameFix = npcName.replace(" ", "_");
        String prefix = nameFix + ".chat.";

        if (config.contains(prefix + "0")) {
            int lines = config.getConfigurationSection(prefix).getKeys(false).size();
            int rand = RandomChance.randomInt(1, lines) - 1;

            return ChatColor.translateAlternateColorCodes('&', config.getString(prefix + Integer.toString(rand)));
        } else {
            return null;
        }
    }

    /**
     * Creates a new configuration file on a players first visit to the server.
     */
    private void createCitizenConfig() {

        file = new File(filePath);
        config = YamlConfiguration.loadConfiguration(file);
        config.set("AnimatedTutorial", "AnimatedTutorial");

        //Types: ItemIdentifier, Merchant, Blacksmith, Alchemist, Quest, None
        config.set("AnimatedTutorial.type", "AnimatedTutorial");

        //Spawn location (used for Hologram
        config.set("AnimatedTutorial.spawn.x", 28.5);
        config.set("AnimatedTutorial.spawn.y", 79);
        config.set("AnimatedTutorial.spawn.z", -13.5);

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private CitizenType getCitizenType(String citizenName) {
        String npc = citizenName.replace(" ", "_");
        String type = config.getString(npc + ".type");

        if (type == null) {
            //Will leave null to report any errors if necessary.
            //System.out.println("[FSCore] NPC: " + npc + " does not exist in config.");
            return CitizenType.NONE;
        } else {
            return CitizenType.valueOf(type);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        if (resetTimer.containsPlayer(player)) return;
        resetTimer.addPlayer(player);

        if (!(event.getRightClicked() instanceof Player)) return;
        Player clickedPlayer = (Player) event.getRightClicked();

        //Check to see if entity is a Citizens NPC.
        if (!clickedPlayer.hasMetadata("NPC")) return;

        plugin.getCitizenManager().onCitizenInteract(player, clickedPlayer);
    }

    private class ResetTimer extends BukkitRunnable {

        private final Map<Player, Integer> countDowns = new ConcurrentHashMap<>();

        public ResetTimer() {
            ColorLogger.INFO.printLog("ResetTimer created!");
        }

        @Override
        public void run() {

            for (Player player : countDowns.keySet()) {

                int count = countDowns.get(player);

                if (count > 0) {
                    countDowns.remove(player);
                } else {
                    countDowns.replace(player, --count);
                }
            }
        }

        void addPlayer(Player player) {
            countDowns.put(player, 2);
        }

        boolean containsPlayer(Player player) {
            return countDowns.containsKey(player);
        }
    }
}


