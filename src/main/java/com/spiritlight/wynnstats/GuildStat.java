package com.spiritlight.wynnstats;

public class GuildStat {
    private final String guildName;
    private final String position;

    public GuildStat(String guildName, String position) {
        this.guildName = guildName;
        this.position = position;
    }

    public String getGuildName() {
        return guildName;
    }

    public String getPosition() {
        return position;
    }
}
