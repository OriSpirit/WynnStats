package com.spiritlight.wynnstats;

import org.json.JSONObject;

public class BasicGuildStat {
    private final String guildIn;
    private final String guildRank;
    private final boolean inGuild;

    public BasicGuildStat(Object guildIn, Object guildRank) {
        this.guildIn = (guildIn == JSONObject.NULL || guildIn == null ? null : (String)guildIn);
        this.guildRank = (guildRank == JSONObject.NULL || guildRank == null ? null : (String)guildRank);
        this.inGuild = (guildIn != null); // true if guild in is not null
    }

    public String getGuildIn() {
        return guildIn;
    }

    public String getGuildRank() {
        return guildRank;
    }

    public boolean isInGuild() {
        return inGuild;
    }
}
