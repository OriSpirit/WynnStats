package com.spiritlight.wynnstats;

public class GuildStat {
    private final String playerName;
    private final String position;
    private final String joinDate;
    private final String contributedXP;

    public GuildStat(String playerName, String position, String joinDate, String contributedXP) {
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

    public String getContributedXP() {
        return contributedXP;
    }
}
