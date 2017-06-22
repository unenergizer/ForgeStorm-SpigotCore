package com.forgestorm.spigotcore.constants;

public enum ItemTiers {

    COMMON("&fCommon", "&f\u2605&7\u2604\u2604\u2604\u2604"),
    UNCOMMON("&aUncommon", "&a\u2605\u2605&7\u2604\u2604\u2604"),
    RARE("&9Rare", "&9\u2605\u2605\u2605&7\u2604\u2604"),
    EPIC("&5Epic", "&5\u2605\u2605\u2605\u2605&7\u2604"),
    LEGENDARY("&6Legendary", "&6\u2605\u2605\u2605\u2605\u2605");


	private final String name;
    private final String stars;

    ItemTiers(String name, String stars) {
        this.name = name;
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public String getStars() {
        return stars;
    }

}
