package com.forgestorm.spigotcore.constants;

import org.bukkit.ChatColor;

public enum SpigotCoreMessages {

	ITEM_PICKUP_GEMS("         &a&l+%s Gems"),

    ECONOMY_PURCHASE_SUCCESS("&aYour purchase was successful!!"),
    ECONOMY_PURCHASE_FAILED("&cYou do not have enough money to make this purchase."),

    SCOREBOARD_TITLE("- &e&lFORGESTORM &r-"),
    SCOREBOARD_BLANK_LINE_1("&r"),
    SCOREBOARD_GEMS("&a&lGEMS&7&l:&r "),
    SCOREBOARD_LEVEL("&a&lLVL&7&l:&r "),
    SCOREBOARD_XP("&a&lXP&7&l:&r "),
    SCOREBOARD_BLANK_LINE_2("&r&r"),
    SCOREBOARD_SERVER("&b&lSERVER&7&l:&r "),

    PROFESSION_ACTION_FAILED("&cYour profession action was unsuccessful."),
    PROFESSION_NOT_LEARNED("&cYou have not learned this profession."),
    PROFESSION_LEVEL_NOT_HIGH_ENOUGH("&cYou don't have the required level to use this tool."),
    PROFESSION_WRONG_TOOL("&cYou're using the wrong tool for this."),

    BAR_REALM("&8&l&m--------------&r&l &l &l &f&lRealm Commands&l &l &l &8&l&m--------------"),
    REALM_PORTAL_DUPLICATE("&cYou already have a realm opened! Close it to open your realm at another location."),
    REALM_PORTAL_OPENED("&d&l* RealmCommands Portal OPENED *"),
    REALM_PORTAL_TITLE("&7Type &3/realm help &7for a list of commands."),
    REALM_PORTAL_PLACE_DENY_BLOCK("&cYou &ncannot&c open a realm portal here."),
    REALM_PORTAL_PLACE_TOO_CLOSE("&cYou &ncannot&c place a realm portal so close to another one."),

    BUNGEECORD_CONNECT_SERVER("&cConnecting you to server \"&e%s&c\"..."),
	
	BLOCK_PLACE_TNT_SUCCESS("&a&lTNT was set, RUN!"),
	BLOCK_PLACE_TNT_FAIL("&c&lYou can not set TNT here!"),

    HELP_PROFESSION_TRACKING_01("&cYou have not learned this profession yet!"),
    HELP_PROFESSION_TRACKING_02("&6You can find the profession trainer using your tracking menu."),
    HELP_PROFESSION_TRACKING_03("&71&8. &aCompass Menu &7-> &aHelp &7-> &aTracking Device &7-> &aFind NPC's"),
    HELP_PROFESSION_TRACKING_04("&72&8. &aNow select the profession trainer you wish to visit!"),
    HELP_PROFESSION_TRACKING_05("&73&8. &aYour compass will then point to that NPC."),
    HELP_PROFESSION_TRACKING_06("&74&8. &aNow follow your compass! The text above your HP bar lets you know " +
            "&ahow much further you will have to travel. Good luck!"),

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
    COMMAND_ADMIN_SET_PREMIUM_CURRENCY_SUCCESS("&aPlayer's premium currency has been changed to %s&a."),
    COMMAND_ADMIN_SET_EXPERIENCE_SUCCESS("&aPlayer's experience has been changed to %s&a."),
    COMMAND_ADMIN_SET_LEVEL_SUCCESS("&aPlayer's level has been changed to %s&a."),
    COMMAND_ADMIN_ADD_CURRENCY_SUCCESS("&aPlayer's currency has been increased. &e%c &c-> &e%s&a."),
    COMMAND_ADMIN_ADD_PREMIUM_CURRENCY_SUCCESS("&aPlayer's premium currency has been increased. &e%c &c-> &e%s&a."),
    COMMAND_ADMIN_ADD_EXPERIENCE_SUCCESS("&aPlayer's experience has been increased. &e%c &c-> &e%s&a."),
    COMMAND_ADMIN_ADD_LEVEL_SUCCESS("&aPlayer's level has been increased. &e%c &c-> &e%s&a."),
    COMMAND_ADMIN_REMOVE_CURRENCY_SUCCESS("&aPlayer's currency has been decreased. &e%c &c-> &e%s&a."),
    COMMAND_ADMIN_REMOVE_PREMIUM_CURRENCY_SUCCESS("&aPlayer's premium currency has been decreased. &e%c &c-> &e%s&a."),
    COMMAND_ADMIN_REMOVE_EXPERIENCE_SUCCESS("&aPlayer's experience has been decreased. &e%c &c-> &e%s&a."),
    COMMAND_ADMIN_REMOVE_LEVEL_SUCCESS("&aPlayer's level has been decreased. &e%c &c-> &e%s&a."),

    BAR_DISCORD("&8&l&m-----------------&r&l &l &l &9&lDiscord&l &l &l &8&l&m-----------------"),
    DISCORD_INFO_1("&7Want to voice chat with staff and our members?"),
    DISCORD_INFO_2("&7If so, join our Discord server! "),
    DISCORD_INFO_3("&bLink&8: &ehttps://discord.gg/NhtvMgR"),

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

    DISPLAY_TAB_HEADER("- &c&lFORGESTORM.COM &r-\n"),
    DISPLAY_TAB_FOOTER("\n&e&lBUY RANKS AND MORE @ STORE.FORGESTORM.COM"),

    GAME_BAR_ROLL("&8&l&m--------------------&r&8&l<[ &6&lRoll &8&l]>&8&l&m------------------"),
    ROLL("&7     &l%player%&8&l: &7Rolled a &n%s&r&7 out of &n%f&r&8."),
    ROLL_ERROR("&c  &l! &a&lPlease specify the maximum size of your dice roll."),
	ROLL_EXAMPLE("&7  &lExample&8&l: &r/roll 100"),
	ROLL_UNHEARD("&cNo one heard your message!"),

	NO_PERMISSION("&cYou do not have permission to do this.");
	
	private final String message;
	
	//Constructor
    SpigotCoreMessages(String message) {
        this.message = color(message);
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

    /**
     * Sends a string representation of the enumerator item.
     */
    public String toString() {
        return message;
    }
}
