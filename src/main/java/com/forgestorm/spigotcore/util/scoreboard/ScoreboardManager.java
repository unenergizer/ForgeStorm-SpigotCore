package com.forgestorm.spigotcore.util.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.forgestorm.spigotcore.SpigotCore;
import com.forgestorm.spigotcore.constants.Usergroup;
import com.forgestorm.spigotcore.profile.player.PlayerProfileData;

import net.md_5.bungee.api.ChatColor;

public class ScoreboardManager {
	
	private final SpigotCore PLUGIN;
	private Scoreboard scoreboard;
	private Objective objectivePlayerList, objectivePlayerHP;
	private Team free, paid1, paid2, paid3, paid4, mod, admin;

	public ScoreboardManager(SpigotCore plugin) {
		PLUGIN = plugin;
		registerScoreboard();
		registerObjectives();
		setupTeams();
	}
	
	private void registerScoreboard() {
		scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
	}
	
	private void registerObjectives() {
		objectivePlayerList = scoreboard.registerNewObjective("UsergroupManager", "dummy");
		objectivePlayerList.setDisplaySlot(DisplaySlot.PLAYER_LIST);
		
		objectivePlayerHP = scoreboard.registerNewObjective("UserHP", "dummy");
		objectivePlayerHP.setDisplaySlot(DisplaySlot.BELOW_NAME);
		objectivePlayerHP.setDisplayName(ChatColor.RED + "❤");
	}
	
	public void updatePlayerHP(PlayerProfileData profile, Player player) {
		Score score = objectivePlayerHP.getScore(Integer.toString((int) profile.getHealth()));
		score.setScore((int) profile.getHealth());
	}
	
	private void setupTeams() {
		free = scoreboard.registerNewTeam(Usergroup.USER_PREFIX_USERGROUP_0.getTeamName());
		paid1 = scoreboard.registerNewTeam(Usergroup.USER_PREFIX_USERGROUP_1.getTeamName());
		paid2 = scoreboard.registerNewTeam(Usergroup.USER_PREFIX_USERGROUP_2.getTeamName());
		paid3 = scoreboard.registerNewTeam(Usergroup.USER_PREFIX_USERGROUP_3.getTeamName());
		paid4 = scoreboard.registerNewTeam(Usergroup.USER_PREFIX_USERGROUP_4.getTeamName());
		mod = scoreboard.registerNewTeam(Usergroup.USER_PREFIX_MODERATOR.getTeamName());
		admin = scoreboard.registerNewTeam(Usergroup.USER_PREFIX_ADMINISTRATOR.getTeamName());
		
		free.setPrefix(Usergroup.USER_PREFIX_USERGROUP_0.getUsergroupPrefix());
		paid1.setPrefix(Usergroup.USER_PREFIX_USERGROUP_1.getUsergroupPrefix());
		paid2.setPrefix(Usergroup.USER_PREFIX_USERGROUP_2.getUsergroupPrefix());
		paid3.setPrefix(Usergroup.USER_PREFIX_USERGROUP_3.getUsergroupPrefix());
		paid4.setPrefix(Usergroup.USER_PREFIX_USERGROUP_4.getUsergroupPrefix());
		
		mod.setPrefix(Usergroup.USER_PREFIX_MODERATOR.getUsergroupPrefix());
		mod.setCanSeeFriendlyInvisibles(true);
		
		admin.setPrefix(Usergroup.USER_PREFIX_ADMINISTRATOR.getUsergroupPrefix());
		admin.setCanSeeFriendlyInvisibles(true);
	}
	
	private boolean addPlayer(Player player, Usergroup group) {
		player.setScoreboard(scoreboard);
		Team tryTeam =  scoreboard.getTeam(group.getTeamName());
	
		if (tryTeam == null) return false;
		
		tryTeam.addEntry(player.getName());
		return true;
	}
	
	public boolean removePlayer(Player player, Usergroup group) {
		
		Team tryTeam =  scoreboard.getTeam(group.getTeamName());
		
		if (tryTeam == null) return false;
		
		tryTeam.removeEntry(player.getName());
		return true;
	}
	
	public void assignPlayer(Player player) {
		PlayerProfileData profile = PLUGIN.getProfileManager().getProfile(player);
		Usergroup group = null;

		//Free
		if (profile.getUserGroup() == 0) group = Usergroup.USER_PREFIX_USERGROUP_0;
		
		//Paid
		if (profile.getUserGroup() == 1) group = Usergroup.USER_PREFIX_USERGROUP_1;
		if (profile.getUserGroup() == 2) group = Usergroup.USER_PREFIX_USERGROUP_2;
		if (profile.getUserGroup() == 3) group = Usergroup.USER_PREFIX_USERGROUP_3;
		if (profile.getUserGroup() == 4) group = Usergroup.USER_PREFIX_USERGROUP_4;
			
		//Staff
		if (profile.isModerator()) group = Usergroup.USER_PREFIX_MODERATOR;
		if (profile.isAdmin()) group = Usergroup.USER_PREFIX_ADMINISTRATOR;
		
		addPlayer(player, group);
		updatePlayerHP(profile, player);
	}

	/**
	 * Unregisters all the given objectives for the scoreboard.
	 * @param board The board that will have all objectives removed form it.
	 */
	private void unregisterObjectives(Scoreboard board) {
		//Check to make sure the board objectives are not null.
		if (board != null) {
			//Loop through all objectives and unregister them.
			for(Objective objectives : board.getObjectives()) {
				objectives.unregister();
			}
		}
	}
}
