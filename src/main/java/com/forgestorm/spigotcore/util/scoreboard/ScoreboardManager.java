package com.forgestorm.spigotcore.util.scoreboard;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.UserGroup;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager {
	
	private final SpigotCore PLUGIN;
	private Scoreboard scoreboard;
	private Objective objectivePlayerHP;
	private final Map<UUID, UserGroup> userGroup;

	public ScoreboardManager(SpigotCore plugin) {
		PLUGIN = plugin;
		userGroup = new HashMap<>();
		registerScoreboard();
		registerObjectives();
		setupTeams();
	}
	
	private void registerScoreboard() {
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	}
	
	private void registerObjectives() {
		Objective objectivePlayerList = scoreboard.registerNewObjective("UsergroupManager", "dummy");
		objectivePlayerList.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		
		objectivePlayerHP = scoreboard.registerNewObjective("UserHP", "dummy");
		objectivePlayerHP.setDisplaySlot(DisplaySlot.BELOW_NAME);
		objectivePlayerHP.setDisplayName(ChatColor.RED + "\u2764");
	}
	
	public void updatePlayerHP(PlayerProfileData profile, Player player) {
		objectivePlayerHP.getScore(player.getName()).setScore((int) profile.getHealth());
	}
	
	private void setupTeams() {
		Team free = scoreboard.registerNewTeam(UserGroup.USER_PREFIX_USER_GROUP_0.getTeamName());
		Team paid1 = scoreboard.registerNewTeam(UserGroup.USER_PREFIX_USER_GROUP_1.getTeamName());
		Team paid2 = scoreboard.registerNewTeam(UserGroup.USER_PREFIX_USER_GROUP_2.getTeamName());
		Team paid3 = scoreboard.registerNewTeam(UserGroup.USER_PREFIX_USER_GROUP_3.getTeamName());
		Team paid4 = scoreboard.registerNewTeam(UserGroup.USER_PREFIX_USER_GROUP_4.getTeamName());
		Team mod = scoreboard.registerNewTeam(UserGroup.USER_PREFIX_MODERATOR.getTeamName());
		Team admin = scoreboard.registerNewTeam(UserGroup.USER_PREFIX_ADMINISTRATOR.getTeamName());
		
		free.setPrefix(UserGroup.USER_PREFIX_USER_GROUP_0.getUserGroupPrefix());
		paid1.setPrefix(UserGroup.USER_PREFIX_USER_GROUP_1.getUserGroupPrefix());
		paid2.setPrefix(UserGroup.USER_PREFIX_USER_GROUP_2.getUserGroupPrefix());
		paid3.setPrefix(UserGroup.USER_PREFIX_USER_GROUP_3.getUserGroupPrefix());
		paid4.setPrefix(UserGroup.USER_PREFIX_USER_GROUP_4.getUserGroupPrefix());
		
		mod.setPrefix(UserGroup.USER_PREFIX_MODERATOR.getUserGroupPrefix());
		mod.setCanSeeFriendlyInvisibles(true);
		
		admin.setPrefix(UserGroup.USER_PREFIX_ADMINISTRATOR.getUserGroupPrefix());
		admin.setCanSeeFriendlyInvisibles(true);
	}
	
	private boolean addPlayer(Player player, UserGroup group) {
		userGroup.put(player.getUniqueId(), group);
		player.setScoreboard(scoreboard);
		Team tryTeam =  scoreboard.getTeam(group.getTeamName());
	
		if (tryTeam == null) return false;
		
		tryTeam.addEntry(player.getName());
		return true;
	}
	
	public boolean removePlayer(Player player) {
		
		Team tryTeam =  scoreboard.getTeam(userGroup.get(player.getUniqueId()).getTeamName());
		
		if (tryTeam == null) return false;
		
		tryTeam.removeEntry(player.getName());
		return true;
	}
	
	public void assignPlayer(Player player) {
		PlayerProfileData profile = PLUGIN.getProfileManager().getProfile(player);
		UserGroup group = null;

		//Free
		if (profile.getUserGroup() == 0) group = UserGroup.USER_PREFIX_USER_GROUP_0;
		
		//Paid
		if (profile.getUserGroup() == 1) group = UserGroup.USER_PREFIX_USER_GROUP_1;
		if (profile.getUserGroup() == 2) group = UserGroup.USER_PREFIX_USER_GROUP_2;
		if (profile.getUserGroup() == 3) group = UserGroup.USER_PREFIX_USER_GROUP_3;
		if (profile.getUserGroup() == 4) group = UserGroup.USER_PREFIX_USER_GROUP_4;
			
		//Staff
		if (profile.isModerator()) group = UserGroup.USER_PREFIX_MODERATOR;
		if (profile.isAdmin()) group = UserGroup.USER_PREFIX_ADMINISTRATOR;
		
		addPlayer(player, group);
		updatePlayerHP(profile, player);
	}
}
