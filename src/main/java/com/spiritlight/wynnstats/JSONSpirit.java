package com.spiritlight.wynnstats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONSpirit {
    // Returns a list of Player object representing this player's subclasses
    public static List<Player> parse(String s) throws JSONException {
        final List<Player> data = new ArrayList<>();
        //String[] professions = new String[] {"alchemism", "armouring", "combat", "cooking", "farming", "fishing", "jeweling", "mining", "scribing", "tailoring", "weaponsmithing", "woodcutting", "woodworking"};

        JSONObject root = new JSONObject(s);
        // Check for existence of data before proceeding to parse
        int responseCode = root.getInt("code");
        if(responseCode == 429) {
            System.out.println("Hit ratelimit.");
            AnnouncerSpirit.sendDebug("JSONSpirit >> Hit ratelimit.");
            return null;
        }
        if(responseCode != 200) {
            System.out.println("An error has occurred whilst scanning this player (HTTP " + responseCode + ")");
            System.out.println("Skipping...");
            return null;
        }
        // Start querying the received JSONObjects
        // Structure: {"uuid":[{"className":[{ProfessionArray},{gamemode}]},{"className2":[{ProfessionArray2},{gamemode}]]} ...
        JSONArray pdata = root.getJSONArray("data");
        JSONObject index = pdata.getJSONObject(0);
        JSONObject meta = index.getJSONObject("meta");
        String name = index.getString("username");
        Object world = meta.getJSONObject("location").get("server"); // String object
        world = (world == JSONObject.NULL ? "Offline" : world);
        JSONArray classes = index.getJSONArray("classes");
        for(int i=0; i<classes.length(); i++) {
            JSONObject accessClass = classes.getJSONObject(i);
            ProfessionStat prof = parseProf(accessClass.getJSONObject("professions"));
            RaidStat raid = parseRaid(accessClass.getJSONObject("raids"));
            BasicGuildStat guildStat = parseGuild(index.getJSONObject("guild"));
            String className = accessClass.getString("name");
            int gamemode = automode(accessClass.getJSONObject("gamemode"));
            int playtime = accessClass.getInt("playtime");
            int deaths = accessClass.getInt("deaths");
            int questCompleted = accessClass.getJSONObject("quests").getInt("completed");
            int mobs = accessClass.getInt("mobsKilled");
            int discoveries = accessClass.getInt("discoveries");
            int growth = calcGrowth(accessClass.getJSONObject("professions"));
            int dungeonsCompleted = accessClass.getJSONObject("dungeons").getInt("completed");
            data.add(new Player(name, className, gamemode, deaths,
                    (String) world, playtime, mobs, growth, dungeonsCompleted, questCompleted, discoveries,
                    prof, raid, guildStat));
        }
        return data; // Looks like {"name":[{"className"... I hope
    }

    private static BasicGuildStat parseGuild(JSONObject j) {
        return new BasicGuildStat(j.get("name"), j.get("rank"));
    }

    private static ProfessionStat parseProf(JSONObject j) {
        return new ProfessionStat(j.getJSONObject("combat").getInt("level"),
                j.getJSONObject("woodcutting").getInt("level"),
                j.getJSONObject("mining").getInt("level"),
                j.getJSONObject("farming").getInt("level"),
                j.getJSONObject("fishing").getInt("level"));
    }

    private static RaidStat parseRaid(JSONObject j) {
        JSONArray array = j.getJSONArray("list");
        int total = j.getInt("completed");
        int nol = 0, tcc = 0, notg = 0;
        for(int i=0; i<array.length(); i++) {
            JSONObject o = array.getJSONObject(i);
            switch(o.getString("name")) {
                case "The Canyon Colossus":
                    tcc = o.getInt("completed");
                    break;
                case "Nest of the Grootslangs":
                    notg = o.getInt("completed");
                    break;
                case "Orphion's Nexus of Light":
                    nol = o.getInt("completed");
                    break;
                default:
                    break;
            }
        }
        return new RaidStat(nol, tcc, notg, total);
    }

    private static int automode(JSONObject j) {
        return (j.getBoolean("hardcore") ? 8 : 0) + (j.getBoolean("ironman") ? 2 : 0) + (j.getBoolean("craftsman") ? 4 : 0)  + (j.getBoolean("hunted") ? 1 : 0);
    }

    private static int calcGrowth(JSONObject professionBase) {
        int growth = 0;
        String[] professions = new String[]{"farming", "fishing", "woodcutting", "mining", "alchemism", "armouring", "combat", "cooking", "jeweling", "scribing", "tailoring", "weaponsmithing", "woodworking"};
        for (String profession : professions) {
            Object o = professionBase.getJSONObject(profession).get("xp");
            double xp = (o == null || o == JSONObject.NULL ? 0 : professionBase.getJSONObject(profession).getDouble("xp"));
            // v3 growth calculation; v1 and v2 lands at 6500 max uncapped combat, v3 land at 10000 capped combat
            if(profession.equals("combat") && xp > 4642)
                xp = 4642;
            //#v1Growth growth += 43.5 * Math.sqrt((((double)professionBase.getJSONObject(profession).getInt("level") + xp/100))/2);
            //#v2Growth growth += Math.pow((double)professionBase.getJSONObject(profession).getInt("level") + xp/100, 2)*500/17424;
            growth += Math.pow((double)professionBase.getJSONObject(profession).getInt("level") + xp/100, 2)*750/17424;
        }
        return growth;
    }
}
