package com.forgestorm.spigotcore.constants;

public enum Redis {
	
	//USER INFO
	PROFILE_ID("info:p:id:%s"),
	PROIFLE_UUID("info:p:uuid:%s"),
	PROFILE_USERNAME("info:p:username:%s"),
	PROFILE_PREFIX("info:p:prefox"),
	PROFILE_IP("info:p:ip:%s"),
	PROFILE_EXPERIENCE("info:p:experience:%s"),
	PROFILE_COMPLETED_TUTORIALS("info:p:completedtutorials:%s"),
	PROFILE_COLLECTED_RECIPES("info:p:collectedrecipes:%s"),
	PROFILE_USER_GROUP("groups:usergroup:%s"),
	PROFILE_PAST_USERNAMES("info:p:pastusernames:%s"),
	PROFILE_IS_MODERATOR("groups:ismod:%s"),
	PROFILE_IS_ADMIN("groups:isadmin:%s"),
	PROFILE_IS_BANNED("mod:banstatus:%s"),
	PROFILE_WARNING_POINTS("mod:warnings:%s"),
	PROFILE_CURRENCY_BANK_GEMS("economy:bankgems:%s"),
	PROFILE_CURRENCY_GEMS("economy:gems:%s"),
	PROFILE_CURRENCY_PREMIUM("economy:premium:%s"),
	PROFILE_CURRENCY_GEMS_LIFE_TIME_("economy:lifetimecurrency:%s"),
	PROFILE_CURRENCY_PREMIUM_LIFE_TIME("economy:lifetimepremiumcurrency:%s"),
	
	//USER RPG INFO
	PROFILE_LOGOUT_HP("info:p:logouthp:%s"),
	PROFILE_LOGOUT_ENERGY("info:p:logoutenergy:%s"),
	PROFILE_PLAYER_INVENTORY("info:p:playerinv:%s"),
	
	//USER SETTINGS
	SETTINGS_P_CHATFILTER("settings:p:chatfilter:%s"),
	SETTINGS_P_FRIEND_REQUEST("settings:p:friendrequests:%s"),
	SETTINGS_P_GUILD_REQUEST("settings:p:guildrequests:%s"),
	SETTINGS_P_PARTY_REQUEST("settings:p:partyrequests:%s"),
	SETTINGS_P_TRADE_REQUEST("settings:p:traderequests:%s"),
	SETTINGS_P_DEBUG_MESSAGES("settings:p:toggledebug:%s"),
	
	//DISCIPLINARY ACTION
	
	//USER STATS
	PROIFLE_ACHIEVEMENTS("stats:p:achievements:%s"),
	PROIFLE_LASTACTIVITY("stats:p:lastlogin:%s"),
	PROFILE_JOINDATE("stats:p:joindate:%s"),
	PROFILE_LOGINS("stats:p:logins:%s"),
	PROFILE_ONLINE_TIME("stats:p:onlinetime:%s"),
	PROFILE_ONLINE_LEVEL_TIME("stats:p:onlineleveltime:%s"),
	
	//PROFESSION
	PROFESSION_FARMING("profession:farming:%s"),
	PROFESSION_FISHING("profession:fishing:%s"),
	PROFESSION_LUMBERJACK("profession:lumberjack:%s"),
	PROFESSION_MINING("profession:mining:%s"),
	
	//SERVER STATS
	SERVER_UNIQUE_LOGINS("stats:s:uniquelogins");

	private String message;

	//Constructor
	Redis(String message) {
		this.message = message;
	}

	/**
	 * Sends a string representation of the enumerator item.
	 */
	public String toString() {
		return message;
	}
	
	public String toString(String uuid) {
		return message.replace("%s", uuid);
	}
}
