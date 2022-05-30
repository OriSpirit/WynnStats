package com.spiritlight.wynnstats;

public class GuildInfo {
    private final String guildName;
    private final String prefix;
    private final int level;
    private final int territories;

    public GuildInfo(String guildName, String prefix, int level, int territories) {
        this.guildName = guildName;
        this.prefix = prefix;
        this.level = level;
        this.territories = territories;
    }

    public String getGuildName() {
        return guildName;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getLevel() {
        return level;
    }

    public int getTerritories() {
        return territories;
    }
}
