package com.spiritlight.wynnstats;

public class Player {
    private final String playerName;
    private final String className;
    private final int gamemode;
    private final boolean playerOnline;
    private final int deaths;
    private final String worldAt;
    private final int playTime;
    private final int mobsKilled;
    private final int growthValue;
    private final int dungeonsCompleted;
    private final int questCompleted;
    private final int discoveries;
    private final ProfessionStat professionStat;
    private final RaidStat raidStat;

    public int getDeaths() {
        return deaths;
    }

    public int getGamemode() {
        return gamemode;
    }

    public Player(String playerName, String className, int gamemode, boolean playerOnline,
                  int deaths, String worldAt, int playTime, int mobsKilled, int growthValue, int dungeonsCompleted, int questCompleted, int discoveries,
                  ProfessionStat professionStat, RaidStat raidStat) {
        this.playerName = playerName;
        this.className = className;
        this.gamemode = gamemode;
        this.deaths = deaths;
        this.playerOnline = playerOnline;
        this.worldAt = worldAt;
        this.playTime = playTime;
        this.mobsKilled = mobsKilled;
        this.growthValue = growthValue;
        this.dungeonsCompleted = dungeonsCompleted;
        this.questCompleted = questCompleted;
        this.discoveries = discoveries;
        this.professionStat = professionStat;
        this.raidStat = raidStat;
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean isPlayerOnline() {
        return playerOnline;
    }

    public String getWorldAt() {
        return worldAt;
    }

    public int getPlayTime() {
        return playTime;
    }

    public int getMobsKilled() {
        return mobsKilled;
    }

    public int getGrowthValue() {
        return growthValue;
    }

    public int getQuestCompleted() {
        return questCompleted;
    }

    public int getDiscoveries() {
        return discoveries;
    }

    public ProfessionStat getProfessionStat() {
        return professionStat;
    }

    public RaidStat getRaidStat() {
        return raidStat;
    }

    public String getClassName() {
        return className;
    }

    public int getDungeonsCompleted() {
        return dungeonsCompleted;
    }

}
