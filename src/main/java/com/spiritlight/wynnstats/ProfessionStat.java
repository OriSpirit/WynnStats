package com.spiritlight.wynnstats;

public class ProfessionStat {
    private final int combat;
    private final int woodcutting;
    private final int mining;
    private final int farming;
    private final int fishing;


    public int getWoodcutting() {
        return woodcutting;
    }

    public int getMining() {
        return mining;
    }

    public int getFarming() {
        return farming;
    }

    public int getFishing() {
        return fishing;
    }

    public int getCombat() {
        return combat;
    }

    public ProfessionStat(int combat, int woodcutting, int mining, int farming, int fishing) {
        this.combat = combat;
        this.woodcutting = woodcutting;
        this.mining = mining;
        this.farming = farming;
        this.fishing = fishing;
    }
}
