package com.spiritlight.wynnstats;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ScannerSpirit {
    // Execute this in async or free lag incoming
    @Nullable
    public static List<GuildStat> fetchGuild(@Nonnull String guildName) {
        List<GuildStat> data = new ArrayList<>();
        String json = HttpSpirit.get("https://api.wynncraft.com/public_api.php?action=guildStats&command=" + guildName);
        JSONObject j = new JSONObject(json);
        try {
            j.getJSONObject("error");
            return null;
        } catch (JSONException ignored) {}
        JSONArray arr = j.getJSONArray("members");
        for(int i=0; i<arr.length(); i++) {
            JSONObject o = arr.getJSONObject(i);
            String name = o.getString("name");
            String joinDate = o.getString("joinedFriendly");
            String rank = o.getString("rank");
            long xp = o.getLong("contributed");
            data.add(new GuildStat(name, rank, joinDate, xp));
        }
        return data;
    }

}
