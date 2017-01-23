package com.forgestorm.spigotcore.constants;

import org.bukkit.ChatColor;

public enum Messages {
	
	//RPG CODE	
	DAMAGE_OUTPUT_OPPONENT("&c        &l%s DMG &r&c-> &r %name% &c[&7%f&cHP]"),
	DAMAGE_OUTPUT_PLAYER("&c        &l-%s HP &r&c-> &r &a[&7%f&a&lHP&r&a]"),
	DAMAGE_LIFESTEAL_DEFENDER("&2&l                   *OPPONENT LIFESTEAL* - &c%s"),
	DAMAGE_LIFESTEAL_DAMAGER("&c&l                        *LIFESTEAL* &2+%s"),
	DAMAGE_KNOCKBACK_DEFENDER(""),
	DAMAGE_KNOCKBACK_DAMAGER(""),
	DAMAGE_CRITICAL_DEFENDER("&2&l                        *CRITICAL HIT*"),
	DAMAGE_CRITICAL_DAMAGER("&c&l                   *CRITICAL HIT* (&r%name%&c&l)"),
	DAMAGE_BLIND_DEFENDER(""),
	DAMAGE_BLIND_DAMAGER(""),
	DAMAGE_VSPLAYER_DEFENDER(""),
	DAMAGE_VSPLAYER_DAMAGER(""),
	DAMAGE_VSMONSTER_DEFENDER(""),
	DAMAGE_VSMONSTER_DAMAGER(""),
	DAMAGE_PURE_DEFENDER(""),
	DAMAGE_PURE_DAMAGER(""),
	DAMAGE_ARMORPEN_DEFENDER(""),
	DAMAGE_ARMORPEN_DAMAGER(""),
	DAMAGE_ICE_DAMAGE_DEFENDER(""),
	DAMAGE_ICE_DAMAGE_DAMAGER(""),
	DAMAGE_FIRE_DAMAGE_DEFENDER(""),
	DAMAGE_FIRE_DAMAGE_DAMAGER(""),
	DAMAGE_POISON_DAMAGE_DEFENDER(""),
	DAMAGE_POISON_DAMAGE_DAMAGER(""),
	DAMAGE_BLOCK_DEFENDER(""),
	DAMAGE_BLOCK_DAMAGER(""),
	DAMAGE_DODGE_DEFENDER(""),
	DAMAGE_DODGE_DAMAGER(""),
	DAMAGE_THORNS_DEFENDER(""),
	DAMAGE_THORNS_DAMAGER(""),
	DAMAGE_REFLECTION_DEFENDER(""),
	DAMAGE_REFLECTION_DAMAGER(""),
	DAMAGE_ICE_RESIST_DEFENDER(""),
	DAMAGE_ICE_RESIST_DAMAGER(""),
	DAMAGE_FIRE_RESIST_DEFENDER(""),
	DAMAGE_FIRE_RESIST_DAMAGER(""),
	DAMAGE_POISON_RESIST_DEFENDER(""),
	DAMAGE_POISON_RESIST_DAMAGER(""),
		
	ITEM_PICKUP_GEMS("         &a&l+%s Gems"),
	
	KICK_REBOOTING("&aThis &lForgeStorm shard &r&ais rebooting.\n\n&7&nwww.ForgeStorm.com\n\n\n\n\n\n"),
	
	MOUNT_SUMMONING_NAME("&lSUMMONING &n%f&r... %s&ls"),
	MOUNT_SUMMONING("&lSUMMONING &r... %s&ls"),
	MOUNT_MOVED("&c&lSUMMONING CANCELED... DO NOT MOVE."),
	MOUNT_LIQUID("&c&lCANNOT SUMMON MOUNT WHILE IN LIQUID."),
	MOUNT_HAS_MOUNT("&c&lYOU ALREADY HAVE A MOUNT."),

	PLAYER_WELCOME_1("&e&lForgeStorm: &r&lRPG MINIGAME SERVER %s"),
	PLAYER_WELCOME_2("&7&nhttp://www.ForgeStorm.com/"),
	PLAYER_WELCOME_3("&c/help &e/mainmenu &a/settings &b/playtime &d/lobby"),

	REALM_PORTAL_DUPLICATE("&cYou already have a realm opened! Close it to open your realm at another location."),
	REALM_PORTAL_OPENED("&d&l* Realm Portal OPENED *"),
	REALM_PORTAL_TITLE("&7Type /realm <TITLE> to set the description of your realm. It will be displayed to all visitors."),
	REALM_PORTAL_PLACE_DENY_BLOCK("&cYou &ncannot&c open a realm portal here."),
	REALM_PORTAL_PLACE_TOO_CLOSE("&cYou &ncannot&c place a realm portal so close to another one."),
	
	SPAWNER_TP_SUCCESS("&a&lTELEPORTING: &r&aMoving you to spawner ID %s."),
	SPAWNER_TP_FAIL("&c&lTELEPORTING CANCELED: &r&cThat spawner ID does not exist! Use 0-%s."),
	
	TOGGLE_DEBUG_ENABLED("&aDebug Messages - &lENABLED"),
	TOGGLE_DEBUG_DISABLED("&cDebug Messages - &lDISABLED"),
	
	//ORIGINAL CODE
	DEFAULT_PLUGIN_NAME("FS-SpigotCore"),
	
	BUNGEECORD_CONNECT_SERVER("&cConnecting you to server \"&e%s&c\"..."),
	
	BLOCK_PLACE_TNT_SUCCESS("&a&lTNT was set, RUN!"),
	BLOCK_PLACE_TNT_FAIL("&c&lYou can not set TNT here!"),
	
	COMMAND_ADMIN_PLAYER_NOT_ONLINE("&cThat player is not online."),
	COMMAND_ADMIN_DEMOTE_ADMIN_SUCCESS("&aYou demoted the player from admin."),
	COMMAND_ADMIN_DEMOTE_MODERATOR_SUCCESS("&aYou demoted the player from moderator."),
	COMMAND_ADMIN_DEMOTE_USERGROUP_SUCCESS("&aYou demoted the player's usergroup."),
	COMMAND_ADMIN_DEMOTE_USERGROUP_FAILURE("&cUnable to demote the player's usergroup."),	
	COMMAND_ADMIN_PROMOTE_ADMIN_SUCCESS("&aYou promoted the player to admin."),
	COMMAND_ADMIN_PROMOTE_MODERATOR_SUCCESS("&aYou promoted the player to moderator."),
	COMMAND_ADMIN_PROMOTE_USERGROUP_SUCCESS("&aYou promoted the player's usergroup."),
	COMMAND_ADMIN_SET_USERGROUP_SUCCESS("&aYou have set the player's usergroup to &e%s&a."),
	COMMAND_ADMIN_SET_CURRENCY_SUCCESS("&aPlayer's currency has been changed to %s&a."),
	COMMAND_ADMIN_SET_PREMIUMCURRENCY_SUCCESS("&aPlayer's premium currency has been changed to %s&a."),
	COMMAND_ADMIN_SET_EXPERIENCE_SUCCESS("&aPlayer's experience has been changed to %s&a."),	
	COMMAND_ADMIN_SET_LEVEL_SUCCESS("&aPlayer's level has been changed to %s&a."),	
	COMMAND_ADMIN_ADD_CURRENCY_SUCCESS("&aPlayer's currency has been increased. &e%c &c-> &e%s&a."),
	COMMAND_ADMIN_ADD_PREMIUMCURRENCY_SUCCESS("&aPlayer's premium currency has been increased. &e%c &c-> &e%s&a."),	
	COMMAND_ADMIN_ADD_EXPERIENCE_SUCCESS("&aPlayer's experience has been increased. &e%c &c-> &e%s&a."),	
	COMMAND_ADMIN_ADD_LEVEL_SUCCESS("&aPlayer's level has been increased. &e%c &c-> &e%s&a."),	
	COMMAND_ADMIN_REMOVE_CURRENCY_SUCCESS("&aPlayer's currency has been decreased. &e%c &c-> &e%s&a."),
	COMMAND_ADMIN_REMOVE_PREMIUMCURRENCY_SUCCESS("&aPlayer's premium currency has been decreased. &e%c &c-> &e%s&a."),
	COMMAND_ADMIN_REMOVE_EXPERIENCE_SUCCESS("&aPlayer's experience has been decreased. &e%c &c-> &e%s&a."),
	COMMAND_ADMIN_REMOVE_LEVEL_SUCCESS("&aPlayer's level has been decreased. &e%c &c-> &e%s&a."),
	
	ENTITY_LEVEL("&7[&c%s&7] "),
	
	BAR_TEAMSPEAK("&8&l&m----------------&r&l &l &l &9&lTeamSpeak&l &l &l &8&l&m----------------"),
	TEAMSPEAK_INFO_1("&7Want to chat with staff and our members?"),
	TEAMSPEAK_INFO_2("&7If so, join our TeamSpeak server! "),
	TEAMSPEAK_INFO_3("&bIP&8: &ets.forgestorm.com"),

	BAR_SOCIAL_MEDIA("&8&l&m---------------&r&l &l &l &9&lSocial Media&l &l &l &8&l&m---------------"),
	FS_SOCIAL_WEB("&c• http://www.forgestorm.com/"),
	FS_SOCIAL_FACEBOOK("&e• https://facebook.com/ForgeStormOfficial/"),
	FS_SOCIAL_TWITTER("&a• https://twitter.com/TheForgeStorm"),
	FS_SOCIAL_YOUTUBE("&b• https://youtube.com/channel/UCOupaY4xuutRjeHzlHH7seA"),
		
	BAR_BOTTOM("&8&l&m---------------------------------------------"),

	HAYLEY_TWITCH("&c• https://twitch.tv/thegreathayley"),
	HAYLEY_INSTAGRAM("&e• https://instagram.com/thegreathayley/"),
	HAYLEY_TWITTER("&a• https://twitter.com/thegreathayley"),
	HAYLEY_YOUTUBE("&b• https://youtube.com/user/TheGreatHayley"),
	
	BAR_TUTORIAL("&8&l&m-----------------&r&l &l &l &9&lTutorial&l &l &l &8&l&m-----------------"),
	
	DISPLAY_TAB_HEADER("\n&8&m-------------------------------\n&r%s&7, thanks for playing on\n \n&r&6&l�  ForgeStorm  �&r\n&8&m-------------------------------&r\n "),
	DISPLAY_TAB_FOOTER("\n&8&m-------------------------------\n&bNews&7, &aForum&7, &eTeamSpeak&7, &dShop &6@\n&r\n&r&9http://www.ForgeStorm.com\n&8&m-------------------------------"),
	
	GAME_BAR_ROLL("&8&l&m--------------------&r&8&l<[ &6&lRoll &8&l]>&8&l&m------------------"),
	ROLL("&7     &l%player%&8&l: &7Rolled a &n%s&r&7 out of &n%f&r&8."),
	ROLL_ERROR("&c  &l! &a&lPlease specify the maximum size of your dice roll."),
	ROLL_EXAMPLE("&7  &lExample&8&l: &r/roll 100"),
	ROLL_UNHEARD("&cNo one heard your message!"),

	NO_PERMISSION("&cYou do not have permission to do this.");
	
	private final String message;
	
	//Constructor
	Messages(String message) {
		this.message = color(message);
	}
	
	/**
	 * Sends a string representation of the enumerator item.
	 */
	public String toString() {
		return message;
	}
	
	/**
	 * Converts special characters in text into Minecraft client color codes.
	 * <p>
	 * This will give the messages color.
	 * @param msg The message that needs to have its color codes converted.
	 * @return Returns a colored message!
	 */
	private static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
