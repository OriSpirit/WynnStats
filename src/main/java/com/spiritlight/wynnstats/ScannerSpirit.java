package com.spiritlight.wynnstats;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CompletableFuture;

public class ScannerSpirit {
    static void parseOnline() {
        CompletableFuture.supplyAsync(() -> HttpSpirit.get("https://api.wynncraft.com/public_api.php?action=onlinePlayers")).thenAccept(ScannerSpirit::pass);
    }

    static void passGuild(String value) {
        JSONArray a = new JSONArray(value);
        for(int i=0; i<a.length(); i++) {
            MainMod.guildLists.add(a.getString(i));
        }
        AnnouncerSpirit.send("Successfully scanned guilds.");
    }
    static void pass(String value) {
        JSONObject o = new JSONObject(value);
        int i = 1;
        while(i < o.length()+50) { // Lenient level 50 cutoffs
            JSONArray base;
            try {
                //System.out.println("Attempting to cache world WC" + i);
                base = o.getJSONArray("WC" + i);
            } catch (JSONException e) {
                //System.out.println("Skipping world WC" + i + " (Invalid world)");
                i++;
                continue;
            }
            for (int j = 0; j < base.length(); j++) {
                if (base.getString(j) != null || !base.getString(j).equals("")) {
                    // Not really sure how it would work so let's trim just in case
                    String pl = base.getString(j);
                    MainMod.onlinePlayers.add(pl);
                }
            }
            i++;
        }
        try {
            //System.out.println("Attempting to cache world WC" + i);
            JSONArray base = o.getJSONArray("YT");
            for (int j = 0; j < base.length(); j++) {
                if (base.getString(j) != null || !base.getString(j).equals("")) {
                    // Not really sure how it would work so let's trim just in case
                    String pl = base.getString(j);
                    MainMod.onlinePlayers.add(pl);
                }
            }
        } catch (JSONException ignored) {
        }
    }
}
