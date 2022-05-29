package com.spiritlight.wynnstats;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ConfigSpirit {
    public static void getConfig() throws IOException {
        File config = new File("config/WynnStats.json");
        if (config.exists()) {
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = (JsonObject)parser.parse(new FileReader("config/WynnStats.json"));
            MainMod.guildMaps = new HashMap<>((Map<? extends String, ? extends String>) jsonObject.get("guilds"));
            // a = String.valueOf(jsonObject.get("g")).replace("\"", ""); // Funny bug!
        } else {
            writeConfig();
        }
    }

    public static void writeConfig() throws IOException {
        final Gson gson = new Gson();
        Type gsonType = new TypeToken(){}.getType();
        JsonWriter writer = new JsonWriter(new FileWriter("config/WynnStats.json"));
        writer.beginObject();
        writer.name("guilds").value(gson.toJson(MainMod.guildMaps, gsonType));
        writer.endObject();
        writer.close();
    }

    public static void updateConfig() {
        try {
            writeConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}