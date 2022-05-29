package com.spiritlight.wynnstats;

public class GuildStat {
    private final String playerName;
    private final String position;
    private final String joinDate;
    private final long contributedXP;

    public GuildStat(String playerName, String position, String joinDate, long contributedXP) {
        this.playerName = playerName;
        this.position = position;
        this.joinDate = joinDate;
        this.contributedXP = contributedXP;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getPosition() {
        return position;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public long getContributedXP() {
        return contributedXP;
    }
}
