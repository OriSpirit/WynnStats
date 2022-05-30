package com.spiritlight.wynnstats;

import java.util.List;

public class CompleteGuildData {
    private final GuildInfo info;
    private final List<GuildStat> stats;

    public CompleteGuildData(GuildInfo info, List<GuildStat> stats) {
        this.info = info;
        this.stats = stats;
    }

    public GuildInfo getInfo() {
        return info;
    }

    public List<GuildStat> getStats() {
        return stats;
    }
}
