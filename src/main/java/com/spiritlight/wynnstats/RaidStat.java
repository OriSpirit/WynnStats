package com.spiritlight.wynnstats;

public class RaidStat {
    private final int onolRaids;
    private final int tccRaids;
    private final int notgRaids;
    private final int totalRaids;

    public int getOnolRaids() {
        return onolRaids;
    }

    public int getTccRaids() {
        return tccRaids;
    }

    public int getNotgRaids() {
        return notgRaids;
    }

    public int getTotalRaids() {
        return totalRaids;
    }

    public RaidStat(int onolRaids, int tccRaids, int notgRaids, int totalRaids) {
        this.onolRaids = onolRaids;
        this.tccRaids = tccRaids;
        this.notgRaids = notgRaids;
        this.totalRaids = totalRaids;
    }
}
