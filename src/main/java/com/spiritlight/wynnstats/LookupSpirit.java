package com.spiritlight.wynnstats;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LookupSpirit {
    public static List<TextComponentString> searchPlayer(String player) {
        try {
            ArrayList<TextComponentString> s = new ArrayList<>();
            String data = HttpSpirit.get("https://api.wynncraft.com/v2/player/" + player + "/stats");
            List<Player> playerList = JSONSpirit.parse(data);
            if (playerList == null || playerList.isEmpty())
                return null;
            //System.out.println("Scan player Array " + j);
            for (Player pl : playerList) {
                Style style;
                TextComponentString str = new TextComponentString(displayName(pl));
                style = str.getStyle();
                TextComponentString msg = new TextComponentString(hoverOver(pl));
                style.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, msg));
                style.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://wynncraft.com/stats/player/" + pl.getPlayerName() + "?class=" + pl.getClassName()));
                s.add(str);
            }
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            AnnouncerSpirit.sendException(e);
            return null;
        }
    }

    @NotNull
    @Contract(pure = true)
    private static String mode(int mode, int deaths) {
        //  HaIrCrHu = 8241
        String s = "";
        int m = mode;
        if(m-8 >= 0) {
            m-=8;
            s+=(deaths == 0 ? "§c" : "§7") + "☠";
        }
        if(m-4 >= 0) {
            m-=4;
            s+="§3✿";
        }
        if(m-2 >= 0) {
            m-=2;
            s+="§6❂";
        }
        s += (m == 1 ? "§5⚔" : "");
        s+="§r";
        return s;
    }

    @NotNull
    @Contract(pure = true)
    private static String color(int level) {
        if (level > 130) {
            return "§b" + level + "§7";
        } else if(level > 110) {
            return "§c" + level + "§7";
        } else if (level > 90) {
            return "§e" + level + "§7";
        } else if (level > 70) {
            return "§6" + level + "§7";
        } else if (level > 50) {
            return "§a" + level + "§7";
        } else if (level > 30) {
            return "§r" + level + "§7";
        } else {
            return "§8" + level + "§7";
        }
    }

    private static String hoverOver(Player player) {
        final ProfessionStat professionStat = player.getProfessionStat();
        final RaidStat raid = player.getRaidStat();
        // Diff Structure: 0playtime, questCompleted, 2fa+xp, 4fi+xp, 6wo+xp, 8mi+xp, 10co+xp, 12 growth, 13mobs, 14chests
        // Object structure: 0world, name, 2playtime, questCompleted, [4]farming+xp, 6fishing+xp, 8woodcutting+xp, 10mining+xp
        // 12combat+xp, [14]iteratedProfessionsList, mode, username, [17]deaths, mobsKilled, chestsOpened
        return "[§" + (player.isPlayerOnline() ? "a" : "7") + "•] Class ID: " + player.getClassName() + " " + mode(player.getGamemode(), player.getDeaths()) +
                "\nLevels: ⬡ " + color(player.getProfessionStat().getCombat()) +
                "§7 Ⓒ" + color(professionStat.getWoodcutting()) +
                "§7 Ⓑ" + color(professionStat.getMining()) +
                "§7 Ⓙ" + color(professionStat.getFarming()) +
                "§7 Ⓚ" + color(professionStat.getFishing()) +
                "\n§e⬤§r Playtime: " + 4.7*player.getPlayTime()/60 + " hours" +
                "\n§rⒺ§r Quests Completed: " + color(player.getQuestCompleted()) +
                "\n§c⚔§r Mobs Killed: " + player.getMobsKilled() +
                "\n§6Ⓓ§r Growth Value: " + player.getGrowthValue() +
                "\n§r✎ Discoveries: " + player.getDiscoveries() +
                "\n§6§bῼ §rPlayer dungeon completions: " +
                color(player.getDungeonsCompleted()) +
                "\n§rPlayer total raids: " +
                color(raid.getTotalRaids()) +
                "\n§7The Canyon Colossus §rRaids: " +
                color(raid.getTccRaids()) +
                "\n§eOrphion's Nexus of Light §rRaids: " +
                color(raid.getOnolRaids()) +
                "\n§2Nest of the Grootslangs §rRaids: " +
                color(raid.getNotgRaids()) +
                "\n§6Click to open " + player.getPlayerName() + "'s " + player.getClassName() + " stat page!";
    }
    // Object structure: world, name, playtime, questCompleted,
    // [4]farming+xp, fishing+xp, woodcutting+xp, mining+xp, combat+xp, [14]iteratedProfessionsList,
    // mode, username, [17]deaths, mobsKilled, chestsOpened


    private static String displayName(Player player) {
        String classn = player.getClassName().replaceAll("([0-9])", "").substring(0, 2);
        classn = classn.substring(0, 1).toUpperCase() + classn.charAt(1);
        final ProfessionStat professionStat = player.getProfessionStat();
        return  "§r[" + player.getWorldAt() + "] [⬡"
                + color(player.getProfessionStat().getCombat())
                + "§7/Ⓒ" + color(professionStat.getWoodcutting())
                + "§7/Ⓑ" + color(professionStat.getMining())
                + "§7/Ⓙ" + color(professionStat.getFarming())
                + "§7/Ⓚ" + color(professionStat.getFishing())
                + "§7/" + classn + "] "
                + mode(player.getGamemode(), player.getDeaths()) + " " + player.getPlayerName();
    }
}
