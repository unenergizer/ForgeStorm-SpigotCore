package com.forgestorm.spigotcore.util.scoreboard;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.UserGroup;
import com.forgestorm.spigotcore.database.PlayerProfileData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager implements Listener {

    private final SpigotCore plugin;
    private final Map<UUID, UserGroup> userGroup;
    private Scoreboard scoreboard;
    private Objective objectivePlayerHP;

    public ScoreboardManager(SpigotCore plugin) {
        this.plugin = plugin;
        userGroup = new HashMap<>();
        registerScoreboard();
        registerObjectives();
        setupTeams();

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void onDisable() {
        EntityDamageEvent.getHandlerList().unregister(this);
        PlayerDeathEvent.getHandlerList().unregister(this);
        PlayerRespawnEvent.getHandlerList().unregister(this);
    }

    /**
     * This will register the scoreboard.
     */
    private void registerScoreboard() {
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    }

    /**
     * This will register the objectives for the scoreboard. So..
     * This will add the HP bar under the players name tag and
     * this will add setup teams for the players to join.
     */
    private void registerObjectives() {
        Objective objectivePlayerList = scoreboard.registerNewObjective("UsergroupManager", "dummy");
        objectivePlayerList.setDisplaySlot(DisplaySlot.PLAYER_LIST);

        objectivePlayerHP = scoreboard.registerNewObjective("UserHP", "dummy");
        objectivePlayerHP.setDisplaySlot(DisplaySlot.BELOW_NAME);
        objectivePlayerHP.setDisplayName(ChatColor.RED + "\u2764");
    }

    /**
     * Updates the HP under a players username.
     *
     * @param player The player HP we want to update.
     */
    public void updatePlayerHP(Player player) {
        objectivePlayerHP.getScore(player.getName()).setScore((int) player.getHealth());
    }

    /**
     * Updates the HP under a players username.
     *
     * @param player The player HP we want to update.
     * @param health The health to set in the scoreboard.
     */
    public void updatePlayerHP(Player player, int health) {
        objectivePlayerHP.getScore(player.getName()).setScore(health);
    }

    /**
     * This will setup all of the teams the player can join. This is used to show
     * tags next to their names.
     */
    private void setupTeams() {
        Team newPlayer = scoreboard.registerNewTeam(UserGroup.USER_PREFIX_USER_GROUP_NEW.getTeamName());
        Team free = scoreboard.registerNewTeam(UserGroup.USER_PREFIX_USER_GROUP_0.getTeamName());
        Team paid1 = scoreboard.registerNewTeam(UserGroup.USER_PREFIX_USER_GROUP_1.getTeamName());
        Team paid2 = scoreboard.registerNewTeam(UserGroup.USER_PREFIX_USER_GROUP_2.getTeamName());
        Team paid3 = scoreboard.registerNewTeam(UserGroup.USER_PREFIX_USER_GROUP_3.getTeamName());
        Team paid4 = scoreboard.registerNewTeam(UserGroup.USER_PREFIX_USER_GROUP_4.getTeamName());
        Team mod = scoreboard.registerNewTeam(UserGroup.USER_PREFIX_MODERATOR.getTeamName());
        Team admin = scoreboard.registerNewTeam(UserGroup.USER_PREFIX_ADMINISTRATOR.getTeamName());
        Team npc = scoreboard.registerNewTeam(UserGroup.USER_PREFIX_NPC.getTeamName());

        newPlayer.setPrefix(UserGroup.USER_PREFIX_USER_GROUP_NEW.getUserGroupPrefix());
        free.setPrefix(UserGroup.USER_PREFIX_USER_GROUP_0.getUserGroupPrefix());
        paid1.setPrefix(UserGroup.USER_PREFIX_USER_GROUP_1.getUserGroupPrefix());
        paid2.setPrefix(UserGroup.USER_PREFIX_USER_GROUP_2.getUserGroupPrefix());
        paid3.setPrefix(UserGroup.USER_PREFIX_USER_GROUP_3.getUserGroupPrefix());
        paid4.setPrefix(UserGroup.USER_PREFIX_USER_GROUP_4.getUserGroupPrefix());

        mod.setPrefix(UserGroup.USER_PREFIX_MODERATOR.getUserGroupPrefix());
        mod.setCanSeeFriendlyInvisibles(true);

        admin.setPrefix(UserGroup.USER_PREFIX_ADMINISTRATOR.getUserGroupPrefix());
        admin.setCanSeeFriendlyInvisibles(true);

        npc.setPrefix(UserGroup.USER_PREFIX_NPC.getUserGroupPrefix());
    }

    /**
     * This will add a player to this scoreboard.
     *
     * @param player The player to add.
     * @param group  The User group they are in.
     * @return True if they were added, false otherwise.
     */
    private boolean addPlayer(Player player, UserGroup group) {
        userGroup.put(player.getUniqueId(), group);
        player.setScoreboard(scoreboard);
        Team tryTeam = scoreboard.getTeam(group.getTeamName());

        if (tryTeam == null) return false;

        tryTeam.addEntry(player.getName());
        return true;
    }

    /**
     * This will remove the player from this scoreboard.
     *
     * @param player The player to remove.
     * @return True if removed, false otherwise.
     */
    public boolean removePlayer(Player player) {

        Team tryTeam = scoreboard.getTeam(userGroup.get(player.getUniqueId()).getTeamName());

        if (tryTeam == null) return false;

        tryTeam.removeEntry(player.getName());
        return true;
    }

    /**
     * This will assign the player their Username Tax and add the HP bar under their name.
     *
     * @param player The player to add.
     */
    public void assignPlayer(Player player) {
        PlayerProfileData profile = plugin.getProfileManager().getProfile(player);
        UserGroup group = null;

        //Free
        if (profile.getUserGroup() == 0) {
            // If they are a new player, show the new player tag.
            if (profile.shouldShowNewPlayerTag()) {
                group = UserGroup.USER_PREFIX_USER_GROUP_NEW;
            } else {
                group = UserGroup.USER_PREFIX_USER_GROUP_0;
            }
        }

        //Paid
        if (profile.getUserGroup() == 1) group = UserGroup.USER_PREFIX_USER_GROUP_1;
        if (profile.getUserGroup() == 2) group = UserGroup.USER_PREFIX_USER_GROUP_2;
        if (profile.getUserGroup() == 3) group = UserGroup.USER_PREFIX_USER_GROUP_3;
        if (profile.getUserGroup() == 4) group = UserGroup.USER_PREFIX_USER_GROUP_4;

        //Staff
        if (profile.isModerator()) group = UserGroup.USER_PREFIX_MODERATOR;
        if (profile.isAdmin()) group = UserGroup.USER_PREFIX_ADMINISTRATOR;

        addPlayer(player, group);
        updatePlayerHP(player);
    }

    /**
     * This will setup a NPC player to add their TAG prefix.
     *
     * @param npc The NPC player to add.
     */
    public void setupNPC(Player npc) {
        addPlayer(npc, UserGroup.USER_PREFIX_NPC);
        updatePlayerHP(npc);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        // Update the players health under their name.
        updatePlayerHP(event.getEntity());
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        updatePlayerHP(event.getPlayer(), 20);
    }


    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        updatePlayerHP((Player) event.getEntity());
    }
}
