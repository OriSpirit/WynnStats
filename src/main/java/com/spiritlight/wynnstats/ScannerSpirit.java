package com.spiritlight.wynnstats;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import java.text.DecimalFormat;
import java.util.*;

public class ScannerSpirit {
    // Execute this in async or free lag incoming
    @Nullable
    public static CompleteGuildData fetchGuild(@Nonnull String guildName) {
        List<GuildStat> data = new ArrayList<>();
        List<GuildStat> value = new ArrayList<>();
        String json = HttpSpirit.get("https://api.wynncraft.com/public_api.php?action=guildStats&command=" + guildName);
        JSONObject j = new JSONObject(json);
        try {
            j.getJSONObject("error");
            return null;
        } catch (JSONException ignored) {
        }
        GuildInfo info = new GuildInfo(j.getString("name"), j.getString("prefix"), j.getInt("level"), j.getInt("territories"));
        JSONArray arr = j.getJSONArray("members");
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        for (int i = 0; i < arr.length(); i++) {
            JSONObject o = arr.getJSONObject(i);
            String name = o.getString("name");
            String joinDate = o.getString("joinedFriendly");
            String rank = o.getString("rank");
            String xp = formatter.format(o.getLong("contributed"));
            data.add(new GuildStat(name, rank, joinDate, xp));
        }
        data.sort(new compareRank());
        return new CompleteGuildData(info, data);
    }
}

class compareRank implements Comparator<GuildStat> {
    final Map<String, Integer> rankLevel = new HashMap<String, Integer>() {{
        put("OWNER", 5);
        put("CHIEF", 4);
        put("STRATEGIST", 3);
        put("CAPTAIN", 2);
        put("RECRUITER", 1);
        put("RECRUIT", 0);
    }};
    @Override
    public int compare(GuildStat a, GuildStat b) {
        return -(rankLevel.get(a.getPosition()) - rankLevel.get(b.getPosition()));
    }
}
