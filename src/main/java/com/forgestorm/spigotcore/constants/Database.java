package com.forgestorm.spigotcore.constants;

public enum Database {

    //USER INFO
    PROFILE_ID("info_p_id"),
    PROIFLE_UUID("info_p_uuid"),
    PROFILE_USERNAME("info_p_username"),
    PROFILE_PREFIX("info_p_prefix"),
    PROFILE_IP("info_p_ip"),
    PROFILE_EXPERIENCE("info_p_experience"),
    PROFILE_COMPLETED_TUTORIALS("info_p_completed_tutorials"),
    PROFILE_COLLECTED_RECIPES("info_p_collected_recipes"),
    PROFILE_USER_GROUP("groups_usergroup"),
    PROFILE_PAST_USERNAMES("info_p_past_username"),
    PROFILE_IS_MODERATOR("groups_is_mod"),
    PROFILE_IS_ADMIN("groups_is_admin"),
    PROFILE_IS_BANNED("mod_ban_status"),
    PROFILE_WARNING_POINTS("mod_warnings"),
    PROFILE_CURRENCY_BANK_GEMS("economy_bank_gems"),
    PROFILE_CURRENCY_GEMS("economy_gems"),
    PROFILE_CURRENCY_PREMIUM("economy_premium"),
    PROFILE_CURRENCY_GEMS_LIFE_TIME_("economy_lifetime_currency"),
    PROFILE_CURRENCY_PREMIUM_LIFE_TIME("economy_lifetime_premium_currency"),
    PROFILE_PLAYER_INVENTORY("info_p_player_inventory"),
    PROFILE_TUTORIAL_FINISHED("info_p_tutorial_finished"),

    //USER SETTINGS
    SETTINGS_P_CHAT_FILTER("settings_p_chat_filter"),
    SETTINGS_P_FRIEND_REQUEST("settings_p_friend_requests"),
    SETTINGS_P_GUILD_REQUEST("settings_p_guild_requests"),
    SETTINGS_P_PARTY_REQUEST("settings_p_party_requests"),
    SETTINGS_P_TRADE_REQUEST("settings_p_trade_requests"),
    SETTINGS_P_DEBUG_MESSAGES("settings_p_toggle_debug"),

    //DISCIPLINARY ACTION

    //USER STATS
    PROFILE_ACHIEVEMENTS("stats_p_achievements"),
    PROFILE_LAST_ACTIVITY("stats_p_last_login"),
    PROFILE_JOIN_DATE("stats_p_join_date"),
    PROFILE_LOGIN_COUNT("stats_p_login_count"),
    PROFILE_ONLINE_TIME("stats_p_online_time"),
    PROFILE_ONLINE_LEVEL_TIME("stats_p_online_level_time"),

    //PROFESSION
    PROFESSION_FARMING_ACTIVE("profession_farming_active"),
    PROFESSION_FISHING_ACTIVE("profession_fishing_active"),
    PROFESSION_LUMBERJACK_ACTIVE("profession_lumberjack_active"),
    PROFESSION_MINING_ACTIVE("profession_mining_active"),
    PROFESSION_COOKING_ACTIVE("profession_cooking_active"),
    PROFESSION_SMELTING_ACTIVE("profession_smelting_active"),
    PROFESSION_FARMING("profession_farming"),
    PROFESSION_FISHING("profession_fishing"),
    PROFESSION_LUMBERJACK("profession_lumberjack"),
    PROFESSION_MINING("profession_mining"),
    PROFESSION_COOKING("profession_cooking"),
    PROFESSION_SMELTING("profession_smelting"),

	//REALM DATA
    REALM_TIER("realm_tier"),
    REALM_PORTAL_INSIDE_LOCATION("realm_portal_inside_location"),
    REALM_TITLE("realm_title"),
    REALM_HAS_REALM("realm_has_realm"),

    //SERVER STATS
    SERVER_UNIQUE_LOGIN_COUNT("stats_s_unique_login_count");

	private final String message;

	//Constructor
    Database(String message) {
        this.message = message;
    }

	/**
	 * Sends a string representation of the enumerator item.
	 */
	public String toString() {
        return "sc_" + message;
    }
}
